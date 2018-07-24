package com.hereisalexius.ms.controllers;

import com.hereisalexius.ms.services.DeleteMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DeleteMemberController {

    @Autowired
    private DeleteMemberService deleteMemberService;

    @DeleteMapping("/members/{id}")
    @ResponseBody
    public ResponseEntity delete(@PathVariable("id") String id) {
        deleteMemberService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
