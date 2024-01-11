package com.cms.app.ServiceImpl;




import com.cms.app.JWT.CustomerUserDetailsService;
import com.cms.app.JWT.JwtFilter;
import com.cms.app.JWT.JwtUtil;
import com.cms.app.POJO.User;
import com.cms.app.Service.UserService;
import com.cms.app.constants.CmsConstant;
import com.cms.app.doa.UserDao;
import com.cms.app.utils.CmsUtils;
import com.cms.app.wrapper.UserWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
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
                if(customerUserDetailsService.getUserDetail().getStatus().equalsIgnoreCase("true")) {
                    String userEmail = customerUserDetailsService.getUserDetail().getEmail();
                    String userRole = customerUserDetailsService.getUserDetail().getRole();

                    // Generate token
                    String token = jwtUtil.generateToken(userEmail, userRole);

                    // Format the JSON string manually
                    String jsonResponse = "{\"token\":\"" + token + "\"}";

                    return new ResponseEntity<>(jsonResponse, HttpStatus.OK);

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

    @Override
    public ResponseEntity<List<UserWrapper>> getAllUsers() {
        try {
        if(jwtFilter.isAdmin()){
            return new ResponseEntity<>(userDao.getAllUser(),HttpStatus.ACCEPTED);
        }
        else{
            return new ResponseEntity<>(new ArrayList<>(),HttpStatus.UNAUTHORIZED);
        }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<List<UserWrapper>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestmap) {
        try {
            if(jwtFilter.isAdmin()){
              Optional<User> optionalUser= userDao.findById(Integer.parseInt(requestmap.get("id"))); // convert ID string to int
                if(optionalUser.isPresent()){
                userDao.updateStatus(requestmap.get("status"), Integer.valueOf(requestmap.get("id")));
                return CmsUtils.getResponseEntity("User status updated",HttpStatus.ACCEPTED);

                }
                else {
                    return new ResponseEntity<String>(CmsConstant.USERID_NOT_FOUND,HttpStatus.OK);
                }
            }
            else{
                return new ResponseEntity<String>(CmsConstant.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<String>(CmsConstant.UNAUTHORIZED_ACCESS,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<UserWrapper>> getAllUsers() {
        try {
        if(jwtFilter.isAdmin()){
            return new ResponseEntity<>(userDao.getAllUser(),HttpStatus.ACCEPTED);
        }
        else{
            return new ResponseEntity<>(new ArrayList<>(),HttpStatus.UNAUTHORIZED);
        }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<List<UserWrapper>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
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