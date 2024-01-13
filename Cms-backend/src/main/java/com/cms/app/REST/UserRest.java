package com.cms.app.REST;

import com.cms.app.wrapper.UserWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/user")
public interface UserRest {

    @PostMapping(path = "/signup")
    public ResponseEntity<String> signUp(@RequestBody(required = true) Map<String,String> requestMap);

    @PostMapping(path = "/login")
    public ResponseEntity<String> login(@RequestBody Map<String,String> requestMap);

    @GetMapping(path = "/get")
    public ResponseEntity<List<UserWrapper>> getAllUsers();

    @PostMapping(path = "/update")
    public ResponseEntity<String> update(@RequestBody Map<String,String> requestmap);

    @GetMapping(path="/checkToken")
    ResponseEntity<String> checkToken();

    @PostMapping(path = "/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody Map<String,String> requestmap);

    @PostMapping(path = "/forgetPassword")
    public ResponseEntity<String> forgetPassword(@RequestBody Map<String,String> requestmap);
}