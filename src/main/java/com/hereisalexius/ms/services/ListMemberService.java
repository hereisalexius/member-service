package com.hereisalexius.ms.services;

import com.hereisalexius.ms.model.Member;
import com.hereisalexius.ms.repos.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListMemberService {

    @Autowired
    private MemberRepository repo;

    public List<Member> list(){
        return repo.findAll();
    }
}
