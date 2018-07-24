package com.hereisalexius.ms.controllers;

import com.hereisalexius.ms.model.Member;
import com.hereisalexius.ms.services.ReadMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReadMemberController {

    @Autowired
    private ReadMemberService readMemberService;

    @GetMapping(
            value = "/members/{id}",
            produces = {"application/json", "application/xml"})
    @ResponseBody
    public ResponseEntity<Member> readMember(@PathVariable("id") String id) {
        return ResponseEntity.ok(readMemberService.read(id));
    }

    @GetMapping("/members/{id}/image")
    @ResponseBody
    public ResponseEntity<InputStreamResource> readMemberImage(@PathVariable("id") String id) {
        return readMemberService.readMemberImage(id);
    }
}
