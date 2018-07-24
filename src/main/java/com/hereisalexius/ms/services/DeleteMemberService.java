package com.hereisalexius.ms.services;

import com.hereisalexius.ms.dao.ImagesDao;
import com.hereisalexius.ms.model.Member;
import com.hereisalexius.ms.repos.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class DeleteMemberService {

    @Autowired
    private MemberRepository repo;

    @Autowired
    private ImagesDao imagesDao;

    public void delete(String id) {
        Member member = repo.findById(id).get();
        repo.delete(member);
        imagesDao.delete(member.getImageFileId());
    }
}
