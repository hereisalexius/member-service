package com.hereisalexius.ms.controllers;

import com.hereisalexius.ms.dto.MemberInputInfo;
import com.hereisalexius.ms.model.Member;
import com.hereisalexius.ms.services.SaveMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class CreateMemberController {

    @Autowired
    private SaveMemberService saveService;

    @PostMapping(
            value = "/members/create",
            produces = {"application/json", "application/xml"})
    @ResponseBody
    public ResponseEntity<Member> create(@RequestBody @Valid MemberInputInfo memberInputInfo) {
        return ResponseEntity.ok(saveService.save(memberInputInfo));
    }


}
