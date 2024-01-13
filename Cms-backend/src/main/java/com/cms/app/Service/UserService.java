package com.cms.app.Service;

import com.cms.app.wrapper.UserWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

public interface UserService {

    ResponseEntity<String> signUp(Map<String,String> requestMap);
    ResponseEntity<String> login(Map<String,String> requestMap);
    ResponseEntity<List<UserWrapper>> getAllUsers();
    public ResponseEntity<String> update(@RequestBody Map<String,String> requestmap);
    ResponseEntity<String> checkToken();
    public ResponseEntity<String> changePassword(@RequestBody Map<String,String> requestmap);
    public ResponseEntity<String> forgetPassword(@RequestBody Map<String,String> requestmap);

}

