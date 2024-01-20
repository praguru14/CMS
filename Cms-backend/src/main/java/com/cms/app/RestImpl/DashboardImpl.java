package com.cms.app.RestImpl;

import com.cms.app.REST.DashboardRest;
import com.cms.app.Service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DashboardImpl implements DashboardRest {
    @Autowired
    DashboardService dashboardService;
    @Override
    public ResponseEntity<Map<String, Object>> getDetails() {
        return dashboardService.getDetails();
    }
}
