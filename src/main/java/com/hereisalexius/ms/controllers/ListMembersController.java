package com.hereisalexius.ms.controllers;

import com.hereisalexius.ms.model.Member;
import com.hereisalexius.ms.services.ListMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ListMembersController {

    @Autowired
    private ListMemberService listMemberService;

    @GetMapping(
            value = "/members/list",
            produces = {"application/json", "application/xml"})
    @ResponseBody
    public ResponseEntity<List<Member>> list() {
        return ResponseEntity.ok(listMemberService.list());
    }

}
