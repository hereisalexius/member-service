package com.hereisalexius.ms.dao;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;

public interface ImagesDao {

    public String save(String url) throws Exception;
    public void delete(String id);
    public ResponseEntity<InputStreamResource> loadAsStream(String id);

}
