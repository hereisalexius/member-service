package com.hereisalexius.ms.services;

import com.hereisalexius.ms.dao.ImagesDao;
import com.hereisalexius.ms.model.Member;
import com.hereisalexius.ms.repos.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ReadMemberService {

    @Autowired
    private MemberRepository repository;

    @Autowired
    private ImagesDao imagesDao;

    public Member read(String id) {
        return repository.findById(id).get();
    }

    public ResponseEntity<InputStreamResource> readMemberImage(String id) {
        String imageID = read(id).getImageFileId();
        return imagesDao.loadAsStream(imageID);
    }
}
