package com.hereisalexius.ms.repos;

import com.hereisalexius.ms.model.Member;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MemberRepository extends MongoRepository<Member,String> {
}
