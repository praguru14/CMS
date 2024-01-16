package com.cms.app.Service;

import com.cms.app.POJO.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;


public interface CategoryService {
    ResponseEntity<String> addNewCategory(@RequestBody Map<String,String> requestMap);
    ResponseEntity<List<Category>> getAllCategory(@RequestParam(required = false) String filtervalue);
    ResponseEntity<String> updateCategory(@RequestBody Map<String,String> requestMap);
}
