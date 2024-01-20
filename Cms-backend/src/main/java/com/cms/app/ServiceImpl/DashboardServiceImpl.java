package com.cms.app.ServiceImpl;

import com.cms.app.REST.DashboardRest;
import com.cms.app.Service.DashboardService;
import com.cms.app.doa.BillDao;
import com.cms.app.doa.CategoryDao;
import com.cms.app.doa.ProductDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class DashboardServiceImpl implements DashboardService {


    @Autowired
    CategoryDao categoryDao;

    @Autowired
    ProductDao productDao;

    @Autowired
    BillDao billDao;
    @Override
    public ResponseEntity<Map<String, Object>> getDetails() {
        Map<String,Object> map = new HashMap<>();
        map.put("Category",categoryDao.count());
        map.put("Product",productDao.count());
        map.put("Bill",billDao.count());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
