package com.cms.app.RestImpl;

import com.cms.app.REST.CategoryRest;
import com.cms.app.Service.CategoryService;
import com.cms.app.utils.CmsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class CategoryImpl implements CategoryRest {

    @Autowired
    CategoryService categoryService;
    @Override
    public ResponseEntity<String> addNewCategory(Map<String, String> requestMap) {
       try{
        return categoryService.addNewCategory(requestMap);
       }
       catch (Exception ex){
        ex.printStackTrace();
       }
       return CmsUtils.getResponseEntity("Unable to add", HttpStatus.BAD_REQUEST);
    }
}
