package com.cms.app.constants;

import com.cms.app.utils.CmsUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CmsConstant {

    public static final String SOMETHING_WENT_WRONG = "Something went wrong";
    public static final String INVALID_DATA = "Invalid Data!";

    public static final String BAD_CREDENTIALS = "Bad Credentials";
    public static final String UNAUTHORIZED_ACCESS = "Unauthorized access";

    public static final String USERID_NOT_FOUND = "UserId doesnt exist";
    public static final String TOKEN_ERROR = "Token Error";
    public static final String PRODUCT_ID_DOESNT_EXIST = "Product ID doesn't exist" ;
    public static final String UPDATED_SUCCESSFULLY ="Updated Successfully" ;

    public static final String STORE_LOCATION = "C:\\Users\\pragu\\OneDrive\\Documents\\CMS";


//    public ResponseEntity<String> tryCatch(){
//        try{
//            tryCode();
//        }
//        catch (Exception ex){
//            ex.printStackTrace();
//        }
//        return CmsUtils.getResponseEntity("Unable to add", HttpStatus.BAD_REQUEST);
//    }
//
//    private void tryCode() {
//        System.out.println("Hello");
//    }

}

