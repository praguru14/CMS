package com.cms.app.RestImpl;

import com.cms.app.REST.BillRest;
import com.cms.app.Service.BillService;
import com.cms.app.constants.CmsConstant;
import com.cms.app.utils.CmsUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

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
}
