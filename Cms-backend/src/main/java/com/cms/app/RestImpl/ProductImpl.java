package com.cms.app.RestImpl;

import com.cms.app.REST.ProductRest;
import com.cms.app.Service.ProductService;
import com.cms.app.constants.CmsConstant;
import com.cms.app.utils.ProductWrapper;
import org.springframework.web.bind.annotation.RestController;
import com.cms.app.utils.CmsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@RestController
public class ProductImpl implements ProductRest {

    @Autowired
    ProductService productService;
    @Override
    public ResponseEntity<String> addNewProduct(Map<String, String> requestMap) {
        try{
            return productService.addNewProduct(requestMap);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<String>(CmsConstant.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ProductWrapper>> getAllProduct() {
        try{
            return productService.getAllProduct();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateProduct(Map<String, String> requestMap) {
        try{
            return productService.updateProduct(requestMap);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>("Something Went Wrong",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteProduct(Integer id) {
        try{
            return productService.deleteProduct(id);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>("Something Went Wrong",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateStatus(Map<String, String> requestMap) {
        try{
            return productService.updateStatus(requestMap);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>("Something Went Wrong",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ProductWrapper>> getByCategory(Integer id) {
        try{
            return productService.getByCategory(id);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<ProductWrapper> getById(Integer id) {
        try{
            return productService.getById(id);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ProductWrapper(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
