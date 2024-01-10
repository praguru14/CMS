package com.cms.app.ServiceImpl;




import com.cms.app.JWT.CustomerUserDetailsService;
import com.cms.app.JWT.JwtFilter;
import com.cms.app.JWT.JwtUtil;
import com.cms.app.POJO.User;
import com.cms.app.Service.UserService;
import com.cms.app.constants.CmsConstant;
import com.cms.app.doa.UserDao;
import com.cms.app.utils.CmsUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtFilter jwtFilter;
    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    CustomerUserDetailsService customerUserDetailsService;
    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {

        log.info("Inside Signup {}", requestMap);
        try{

            if(validateSignUpMap(requestMap)){
                User user = userDao.findByEmailId(requestMap.get("email"));
                if(Objects.isNull(user)){
                    userDao.save(getUserFromMap(requestMap));
                    return CmsUtils.getResponseEntity("SignUp is done",HttpStatus.OK);
                }
                else{
                    return CmsUtils.getResponseEntity("Email exists already",HttpStatus.BAD_REQUEST);
                }

            }
            else{
                return CmsUtils.getResponseEntity(CmsConstant.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return CmsUtils.getResponseEntity(CmsConstant.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        log.info("Inside Login");
        try{

            //get pass and email
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestMap.get("email"),requestMap.get("password")));
            if(auth.isAuthenticated()){
                //extract role and status
                if(customerUserDetailsService.getUserDetail().getStatus().equalsIgnoreCase("true")){
                    return new ResponseEntity<>("{\"token\":\""+jwtUtil.generateToken(customerUserDetailsService.getUserDetail().getEmail(),
                            customerUserDetailsService.getUserDetail().getRole() +"\"}"),HttpStatus.OK);
                }
                else{
                    return new ResponseEntity<String >("{\"message\":\""+"Wait for admin approval"+"\"}",HttpStatus.BAD_REQUEST);
                }
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
            log.error("{}",ex);
        }
        return CmsUtils.getResponseEntity(CmsConstant.BAD_CREDENTIALS,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateSignUpMap(Map<String, String> requestMap) {
        return requestMap.containsKey("name") && requestMap.containsKey("contactNumber") && requestMap.containsKey("email") && requestMap.containsKey("password");
    }

    //DTO
    private User getUserFromMap(Map<String,String> requestMap){
        User user = new User();
        user.setName(requestMap.get("name"));
        user.setContactNumber(requestMap.get("contactNumber"));
        user.setEmail(requestMap.get("email"));
        user.setPassword(requestMap.get("password"));
        user.setStatus("false");
        user.setRole("user");
        return user;
    }


}
