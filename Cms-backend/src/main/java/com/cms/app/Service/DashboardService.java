package com.cms.app.Service;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface DashboardService {
    ResponseEntity<Map<String,Object>> getDetails();
}
