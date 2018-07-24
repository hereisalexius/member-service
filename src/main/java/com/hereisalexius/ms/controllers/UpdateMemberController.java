package com.hereisalexius.ms.controllers;

import com.hereisalexius.ms.dto.MemberUpdateInfo;
import com.hereisalexius.ms.model.Member;
import com.hereisalexius.ms.services.SaveMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UpdateMemberController {

    @Autowired
    private SaveMemberService saveMemberService;

    @PatchMapping("/members/{id}")
    @ResponseBody
    public ResponseEntity<Member> update(@RequestBody @Valid MemberUpdateInfo info, @PathVariable("id") String id) {
        return ResponseEntity.ok(saveMemberService.update(id, info.asMap()));
    }
}