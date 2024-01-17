package com.cms.app.ServiceImpl;

import com.cms.app.JWT.CustomerUserDetailsService;
import com.cms.app.JWT.JwtFilter;
import com.cms.app.JWT.JwtUtil;
import com.cms.app.POJO.Category;
import com.cms.app.Service.CategoryService;
import com.cms.app.doa.CategoryDao;
import com.cms.app.utils.CmsUtils;
import com.cms.app.utils.EmailUtils;
import com.google.common.base.Strings;
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
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryDao categoryDao;

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
    public ResponseEntity<String> addNewCategory(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()) {
                if (validateCatgoryMap(requestMap, false)) {
                    categoryDao.save(getCategoryFromMap(requestMap, false));
                    return CmsUtils.getResponseEntity("Added", HttpStatus.OK);
                }
            } else {
                return CmsUtils.getResponseEntity("Unauthorize access", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CmsUtils.getResponseEntity("Unable to add", HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<List<Category>> getAllCategory(String filterValue) {
        {
            try {
                if (!Strings.isNullOrEmpty(filterValue) && filterValue.equalsIgnoreCase("true")) {
                    log.info("Inside getAll");
                    log.info("{}",categoryDao.getAllCategory());
                    System.out.println(categoryDao.getAllCategory());
                    return new ResponseEntity<>(categoryDao.getAllCategory(), HttpStatus.OK);

                } else {
                    log.info("Inside findAll");
                    return new ResponseEntity<>(categoryDao.findAll(), HttpStatus.OK);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<String> updateCategory(Map<String, String> requestMap) {
        try {
            String id = requestMap.get("id");
            if (jwtFilter.isAdmin()) {
                if (validateCatgoryMap(requestMap, true)) {
                    Optional<Category> optional = categoryDao.findById(Integer.valueOf(id));
                    if (!optional.isEmpty()) {
                        categoryDao.save(getCategoryFromMap(requestMap, true));
                        return CmsUtils.getResponseEntity("Updated Successfully",HttpStatus.OK);
                    } else
                        return CmsUtils.getResponseEntity("Category id doesnt exist", HttpStatus.OK);
                }

                return CmsUtils.getResponseEntity("INVALID DATA", HttpStatus.BAD_REQUEST);
            }
            return CmsUtils.getResponseEntity("Not an admin", HttpStatus.UNAUTHORIZED);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CmsUtils.getResponseEntity("Error Updating", HttpStatus.BAD_REQUEST);
    }


    private boolean validateCatgoryMap(Map<String, String> requestMap, boolean validateId) {
        if (requestMap.containsKey("name")) {
            if (requestMap.containsKey("id") && validateId) {
                return true;
            } else return !validateId;
        }
        return false;
    }

    private Category getCategoryFromMap(Map<String, String> requestMap, boolean isAdd) {
        Category category = new Category();
        if (isAdd) {
            category.setId(Integer.parseInt(requestMap.get("id")));

        }
        category.setName(requestMap.get("name"));
        return category;
    }
}


