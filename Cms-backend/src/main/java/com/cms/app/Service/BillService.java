package com.cms.app.Service;

import com.cms.app.POJO.Bill;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

public interface BillService {
    ResponseEntity<String> generateReport(Map<String,Object> requestMap);
    ResponseEntity<List<Bill>> getAllBills();
    ResponseEntity<byte[]> getPdf(Map<String ,Object> requestMap);
    ResponseEntity<String> deleteBill(Integer id);
}
