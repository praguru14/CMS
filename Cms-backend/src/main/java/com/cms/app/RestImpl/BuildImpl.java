package com.cms.app.RestImpl;

import com.cms.app.POJO.Bill;
import com.cms.app.REST.BillRest;
import com.cms.app.Service.BillService;
import com.cms.app.constants.CmsConstant;
import com.cms.app.utils.CmsUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class BuildImpl implements BillRest {

    private BillService billService;

    public BuildImpl(BillService billService) {
        this.billService = billService;
    }

    @Override
    public ResponseEntity<String> generateReport(Map<String, Object> requestMap) {
        try{
            return billService.generateReport(requestMap);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<String>(CmsConstant.TOKEN_ERROR,HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<List<Bill>> getAllBills() {
        try{
            return billService.getAllBills();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<byte[]> getPdf(Map<String, Object> requestMap) {
        try{
            return billService.getPdf(requestMap);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public ResponseEntity<String> deleteBill(Integer id) {
        try{
            return billService.deleteBill(id);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>("Error",HttpStatus.FORBIDDEN);
    }
}
