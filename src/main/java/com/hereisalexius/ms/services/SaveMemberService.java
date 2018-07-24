package com.hereisalexius.ms.services;

import com.hereisalexius.ms.dao.ImagesDao;
import com.hereisalexius.ms.dto.MemberInputInfo;
import com.hereisalexius.ms.model.Member;
import com.hereisalexius.ms.repos.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.UUID;

@Service
public class SaveMemberService {

    @Autowired
    private MemberRepository repository;

    @Autowired
    private ImagesDao imagesDao;

    @Autowired
    private ReadMemberService readMemberService;

    public Member save(MemberInputInfo info) {

        String imgUrl = info.getImageUrl();
        String imageID = null;
        try {
            imageID = imagesDao.save(imgUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Member member = new Member(
                info.getFirstName(),
                info.getLastName(),
                info.getBirthDate(),
                info.getPostalCode(),
                imageID
        );

        repository.save(member);
        return member;
    }


    @Autowired
    private MongoOperations mongoOperations;

    public Member update(String memberId, Map<String, Object> data) {
        //prevent id change
        data.remove("id");
        Update update = new Update();
        data.forEach((s, o) -> {
            update.set(s, o);
        });
        //update params
        mongoOperations.updateFirst(new Query(Criteria.where("_id").is(memberId)), update, Member.class);

        //update image before out
        return refreshImage(memberId, data);
    }

    //check document for new image url
    public Member refreshImage(String memberId, Map<String, Object> data) {
        //load member entity
        Member m = readMemberService.read(memberId);
        //check for imageUrl
        data.computeIfPresent("imageUrl", (s, o) -> {
            try {
                //save to fs
                String imageID = imagesDao.save((String) o);
                //delete old
                imagesDao.delete(m.getImageFileId());
                //bind id of new
                m.setImageFileId(imageID);
                //update member info
                repository.save(m);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                return m;
            }

        });
        return m;
    }


}
