package com.cms.app.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CmsUtils {
    private CmsUtils(){

    }

    public static ResponseEntity<String> getResponseEntity(String responseMessage, HttpStatus httpStatus){
        return new ResponseEntity<String>("{\"message\":\""+responseMessage+"\"}", httpStatus);
    }
}
