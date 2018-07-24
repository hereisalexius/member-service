package com.hereisalexius.ms.dao;

import com.mongodb.client.gridfs.model.GridFSFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.UUID;

@Service
public class ImagesDaoImpl implements ImagesDao {
    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Override
    public String save(String url) throws Exception {
        String ext = makeExtFromURL(url);
        String contentType = "image/" + ext;
        String fileName = UUID.randomUUID().toString() + "." + ext;
        InputStream inputStream = new URL(url).openStream();
        return gridFsTemplate.store(inputStream, fileName, contentType).toString();
    }

    @Override
    public void delete(String id) {
        gridFsTemplate.delete(new Query(Criteria.where("_id").is(id)));
    }

    @Override
    public ResponseEntity<InputStreamResource> loadAsStream(String id) {
        GridFSFile file = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id)));
        GridFsResource resource = gridFsTemplate.getResource(file.getFilename());
        try {
            ResponseEntity<InputStreamResource> response = ResponseEntity.ok()
                    .contentLength(resource.contentLength())
                    .contentType(MediaType.parseMediaType(resource.getContentType()))
                    .body(new InputStreamResource(resource.getInputStream()));
            return response;
        } catch (IOException ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String makeExtFromURL(String url) {
        String[] splitedByDots = url.split("\\.");
        return splitedByDots[splitedByDots.length - 1];
    }
}
