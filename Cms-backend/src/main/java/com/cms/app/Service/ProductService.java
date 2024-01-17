package com.cms.app.Service;

import com.cms.app.utils.ProductWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

public interface ProductService {
    ResponseEntity<String> addNewProduct(Map<String,String> requestMap);
    ResponseEntity<List<ProductWrapper>> getAllProduct();
    ResponseEntity<String> updateProduct(Map<String,String> requestMap);
    ResponseEntity<String> deleteProduct(Integer id);
    ResponseEntity<String> updateStatus(Map<String,String> requestMap);
    ResponseEntity<List<ProductWrapper>> getByCategory(Integer id);
    ResponseEntity<ProductWrapper> getById(Integer id);
}
