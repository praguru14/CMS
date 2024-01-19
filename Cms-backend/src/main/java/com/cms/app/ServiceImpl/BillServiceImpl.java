package com.cms.app.ServiceImpl;

import com.cms.app.JWT.CustomerUserDetailsService;
import com.cms.app.JWT.JwtFilter;
import com.cms.app.JWT.JwtUtil;
import com.cms.app.POJO.Bill;
import com.cms.app.Service.BillService;
import com.cms.app.constants.CmsConstant;
import com.cms.app.doa.BillDao;
import com.cms.app.utils.CmsUtils;
import com.cms.app.utils.EmailUtils;
import com.google.gson.JsonArray;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.util.Map;
import java.util.stream.Stream;

@Service
@Slf4j
public class BillServiceImpl implements BillService {

    @Autowired
     BillDao billDao;

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
    public ResponseEntity<String> generateReport(Map<String, Object> requestMap) {
        try{
            log.info("Generate Report");
            String fileName ;
            if(validateRequestMap(requestMap)){
                if(requestMap.containsKey("isGenerate")&& !(Boolean)requestMap.get("isGenerate")){
                    fileName = (String) requestMap.get("uuid");
                }
                else {
                    fileName = CmsUtils.getUUID();
                    requestMap.put("uuid",fileName);
                    insertBill(requestMap);
                }

                String data = "Name: " + requestMap.get("name") +"\n"+
                        "Contact Number: "+ requestMap.get("contactNumber") +"\n"+
                        "Email: " + requestMap.get("email") +"\n"+
                        "Payment Method: "+ requestMap.get("paymentMethod");

                Document document = new Document();
                PdfWriter.getInstance(document,new FileOutputStream(CmsConstant.STORE_LOCATION +"\\"+ fileName+".pdf"));
                document.open();
                setRectanglePdf(document);

                Paragraph paragraph = new Paragraph("Prafull's Eatery",getFont("Header"));
                paragraph.setAlignment(Element.ALIGN_CENTER);
                document.add(paragraph);

                Paragraph paragraph1 = new Paragraph(data+ "\n \n",getFont("Data"));
                document.add(paragraph1);

                PdfPTable table = new PdfPTable(5);
                table.setWidthPercentage(100);
                addTableHeader(table);

                JSONArray jsonArray = CmsUtils.getJsonArrayFromString((String) requestMap.get("productDetails"));
                for(int i = 0;i<jsonArray.length();i++){
                    addRows(table,CmsUtils.getMapFromJson(jsonArray.getString(i)));
                }
                document.add(table);

                Paragraph footer = new Paragraph("Total: "+ requestMap.get("totalAmount")+"\n"
                +"Thank you for Visiting",getFont("Data"));

                document.add(footer);
                document.close();
                return CmsUtils.getResponseEntity("{\"uuid\":\""+fileName+"\"}",HttpStatus.OK);

            }
            return CmsUtils.getResponseEntity("Required Data not found", HttpStatus.BAD_REQUEST);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(CmsConstant.TOKEN_ERROR, HttpStatus.FORBIDDEN);
    }

    private void addRows(PdfPTable table, Map<String, Object> mapFromJson) {
        log.info("Inside Add Rows");
        table.addCell((String) mapFromJson.get("name"));
        table.addCell((String) mapFromJson.get("category"));
        table.addCell((String) mapFromJson.get("quantity"));
        table.addCell(Double.toString((Double) mapFromJson.get("price")));
        table.addCell(Double.toString((Double) mapFromJson.get("total")));

    }

    private void addTableHeader(PdfPTable table) {

        log.info("Inside Add table header");

        Stream.of("Name,","Category","Quantity","Price","Sub total").forEach(columnTitle->{
            PdfPCell header = new PdfPCell();
            header.setBackgroundColor(BaseColor.LIGHT_GRAY);
            header.setBorderWidth(2);
            header.setPhrase(new Phrase(columnTitle));
            header.setBackgroundColor(BaseColor.YELLOW);
            header.setHorizontalAlignment(Element.ALIGN_CENTER);
            header.setVerticalAlignment(Element.ALIGN_CENTER);
            table.addCell(header);
        });
    }

    private Font getFont(String type) {
        log.info("inside GetFont");
        switch (type){
            case "Header":
                Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE,18,BaseColor.BLACK);
                headerFont.setStyle(Font.BOLD);
                return headerFont;

            case "Data":
                Font dataFont = FontFactory.getFont(FontFactory.TIMES_ROMAN,11,BaseColor.BLACK);
                dataFont.setStyle(Font.BOLD);
                return dataFont;

            default:
                return new Font();
        }
    }

    private void setRectanglePdf(Document document) throws DocumentException {
    log.info("Inside setRectanglePdf");
        Rectangle rectangle = new Rectangle(600,850,18,15);
        rectangle.enableBorderSide(1);
        rectangle.enableBorderSide(2);
        rectangle.enableBorderSide(4);
        rectangle.enableBorderSide(8);
        rectangle.setBorderColor(BaseColor.BLACK);
        rectangle.setBorderWidth(1);
        document.add(rectangle);

    }

    private void insertBill(Map<String, Object> requestMap) {
        try {
            Bill bill = new Bill();
            bill.setUuid((String) requestMap.get("uuid")) ;
            bill.setName((String) requestMap.get("name"));
            bill.setEmail((String) requestMap.get("email"));
            bill.setContactNumber((String) requestMap.get("contactNumber"));
            bill.setPaymentMethod((String) requestMap.get("paymentMethod"));
            bill.setTotal(Integer.valueOf((String) requestMap.get("totalAmount")));
            bill.setProductDetails((String) requestMap.get("productDetails"));
            bill.setCreatedBy(jwtFilter.getCurrentUser());
            billDao.save(bill);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private boolean validateRequestMap(Map<String, Object> requestMap) {
        return requestMap.containsKey("name") && requestMap.containsKey("contactNumber") &&
                requestMap.containsKey("email") && requestMap.containsKey("paymentMethod") && requestMap.containsKey("productDetails")
                && requestMap.containsKey("totalAmount");

    }
}
