package com.cms.app.Service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;


public interface CategoryService {
    ResponseEntity<String> addNewCategory(@RequestBody Map<String,String> requestMap);
}
