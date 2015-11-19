package com.theironyard.services;

import com.theironyard.entities.AnonFile;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by MattBrown on 11/18/15.
 */
public interface AnonFileRepository extends CrudRepository<AnonFile, Integer> {
}
