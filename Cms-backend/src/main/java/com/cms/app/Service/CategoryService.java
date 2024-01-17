package com.cms.app.Service;

import com.cms.app.POJO.Category;
import com.cms.app.utils.ProductWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;


public interface CategoryService {
    ResponseEntity<String> addNewCategory( Map<String,String> requestMap);
    ResponseEntity<List<Category>> getAllCategory(String filterValue);
    ResponseEntity<String> updateCategory( Map<String,String> requestMap);

}
