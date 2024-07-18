package com.project.picpay.controller;

import com.project.picpay.domain.user.User;
import com.project.picpay.dto.UserPostDTO;
import com.project.picpay.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> save(@RequestBody UserPostDTO userPostDTO){
        return new ResponseEntity<>(userService.save(userPostDTO), HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<User>> listAll(){
        return new ResponseEntity<>(userService.listAll(), HttpStatus.OK);
    }
}
