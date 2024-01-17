package com.cms.app.ServiceImpl;

import com.cms.app.JWT.CustomerUserDetailsService;
import com.cms.app.JWT.JwtFilter;
import com.cms.app.JWT.JwtUtil;
import com.cms.app.POJO.Category;
import com.cms.app.POJO.Product;
import com.cms.app.Service.ProductService;
import com.cms.app.constants.CmsConstant;
import com.cms.app.doa.CategoryDao;
import com.cms.app.doa.ProductDao;
import com.cms.app.utils.CmsUtils;
import com.cms.app.utils.EmailUtils;
import com.cms.app.utils.ProductWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductDao productDao;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtFilter jwtFilter;
    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    EmailUtils emailUtils;

    @Autowired
    CustomerUserDetailsService customerUserDetailsService;

    @Override
    public ResponseEntity<String> addNewProduct(Map<String, String> requestMap) {
        try {

            if (jwtFilter.isAdmin()) {
                if (validateProductFromMap(requestMap, false)) {
                    productDao.save(getProductFromMap(requestMap, false));
                    return CmsUtils.getResponseEntity("Product Added Successfully", HttpStatus.OK);
                }
                return CmsUtils.getResponseEntity(CmsConstant.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<String>(CmsConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ProductWrapper>> getAllProduct() {
        try {
            return new ResponseEntity<>(productDao.getAllProduct(), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateProduct(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()) {
                if (validateProductFromMap(requestMap, true)) {
                    Optional<Product> optional = productDao.findById(Integer.parseInt(requestMap.get("id")));
                    if (!optional.isEmpty()) {
                        Product product = getProductFromMap(requestMap, true);
                        product.setStatus(optional.get().getStatus());
                        productDao.save(product);
                        return CmsUtils.getResponseEntity("Updated Successfully", HttpStatus.OK);
                    } else {
                        return CmsUtils.getResponseEntity("Product id doesnt exist", HttpStatus.BAD_REQUEST);
                    }
                } else {
                    CmsUtils.getResponseEntity(CmsConstant.INVALID_DATA, HttpStatus.BAD_REQUEST);
                }
            } else {
                return CmsUtils.getResponseEntity("Not admin", HttpStatus.OK);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>("Something Went Wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteProduct(Integer id) {
        try {
            if (jwtFilter.isAdmin()) {
                Optional optional = productDao.findById(id);
                if (!optional.isEmpty()) {
                        productDao.deleteById(id);
                        return CmsUtils.getResponseEntity("Product Deleted",HttpStatus.OK);
                }
                return CmsUtils.getResponseEntity(CmsConstant.PRODUCT_ID_DOESNT_EXIST,HttpStatus.BAD_REQUEST);
            }
               else{
                    return CmsUtils.getResponseEntity(CmsConstant.UNAUTHORIZED_ACCESS, HttpStatus.INTERNAL_SERVER_ERROR);
                }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>("Something Went Wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateStatus(Map<String, String> requestMap) {
        try{
            if(jwtFilter.isAdmin()){
               Optional optional= productDao.findById(Integer.parseInt(requestMap.get("id")));
               if(!optional.isEmpty()){
                   productDao.updateStatus(requestMap.get("status"),Integer.parseInt(requestMap.get("id")));
                   return CmsUtils.getResponseEntity(CmsConstant.UPDATED_SUCCESSFULLY,HttpStatus.OK);
               }
                return CmsUtils.getResponseEntity(CmsConstant.PRODUCT_ID_DOESNT_EXIST,HttpStatus.OK);
            }

            else{
                return CmsUtils.getResponseEntity(CmsConstant.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>("Something Went Wrong",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ProductWrapper>> getByCategory(Integer id) {
        try{
            return new ResponseEntity<>(productDao.getProductByCategory(id),HttpStatus.OK);
//            if(jwtFilter.isAdmin()){
//
//            }
//            else{
//                return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
//            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<ProductWrapper> getById(Integer id) {
        try{
            return new ResponseEntity<>(productDao.getProductById(id),HttpStatus.OK);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ProductWrapper(),HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private Product getProductFromMap(Map<String, String> requestMap, boolean isAdd) {
        Product product = new Product();
        Category category = new Category();
        category.setId(Integer.parseInt(requestMap.get("categoryId")));
        if (isAdd) {
            product.setId(Integer.parseInt(requestMap.get("id")));
        } else {
            product.setStatus("true");
        }
        product.setCategory(category);
        product.setName(requestMap.get("name"));
        product.setDescription(requestMap.get("description"));
        product.setPrice(Integer.valueOf(requestMap.get("price")));
        return product;
    }

    private boolean validateProductFromMap(Map<String, String> requestMap, boolean productValidation) {

        if (requestMap.containsKey("name")) {
            if (requestMap.containsKey("id") && productValidation) {
                return true;
            } else if (!productValidation) return true;
        }
        return false;
    }
}
