package com.theironyard.controllers;

import com.theironyard.entities.AnonFile;
import com.theironyard.services.AnonFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MattBrown on 11/18/15.
 */
@RestController
public class AnonUploadController {

    @Autowired
    AnonFileRepository files;

    @RequestMapping("/files")
    public Iterable<AnonFile> getFiles() {
        return files.findAll();
    }

    @RequestMapping("/upload")
    public void upload(MultipartFile file,
                       HttpServletResponse response,
                       boolean isPerm,
                       String newComment
    ) throws Exception {
        File f = File.createTempFile("file", file.getOriginalFilename(), new File("public"));
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(file.getBytes());

        AnonFile anonFile = new AnonFile();
        anonFile.originalName = file.getOriginalFilename();
        anonFile.name = f.getName();
        anonFile.isPerm = isPerm;
        anonFile.newComment = newComment;
        files.save(anonFile);

        List<AnonFile> fileAnonList = (List<AnonFile>)files.findAll();
        ArrayList<AnonFile> anonFindList = new ArrayList<>();
        for (AnonFile searchedBy : fileAnonList ){
            if (!searchedBy.isPerm){
                anonFindList.add(searchedBy);
            }
        }
        if (anonFindList.size() > 5){
            AnonFile removeList = anonFindList.get(0);
            files.delete(removeList);
        }
        response.sendRedirect("/");
    }
}
