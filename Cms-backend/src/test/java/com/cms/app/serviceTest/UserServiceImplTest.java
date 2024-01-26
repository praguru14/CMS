package com.cms.app.serviceTest;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.*;

import com.cms.app.JWT.CustomerUserDetailsService;
import com.cms.app.JWT.JwtFilter;
import com.cms.app.JWT.JwtUtil;
import com.cms.app.POJO.User;
import com.cms.app.ServiceImpl.UserServiceImpl;
import com.cms.app.constants.CmsConstant;
import com.cms.app.doa.UserDao;
import com.cms.app.utils.EmailUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @Mock
    private UserDao userDao;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtFilter jwtFilter;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private EmailUtils emailUtils;

    @Mock
    private CustomerUserDetailsService customerUserDetailsService;

    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private Authentication authentication;

    private Map<String, String> validSignUpRequest;
    private Map<String, String> invalidSignUpRequest;
    private Map<String, String> loginRequest;

    private User createTestUser(String email, String role, String status) {
        User user = new User();
        user.setEmail(email);
        user.setRole(role);
        user.setStatus(status);
        return user;
    }

    @Before
    public void setUp() {
        validSignUpRequest = new HashMap<>();
        validSignUpRequest.put("name", "John Doe");
        validSignUpRequest.put("contactNumber", "1234567890");
        validSignUpRequest.put("email", "john.doe@example.com");
        validSignUpRequest.put("password", "password123");

        invalidSignUpRequest = new HashMap<>();
        invalidSignUpRequest.put("contactNumber", "1234567890");
        invalidSignUpRequest.put("email", "john.doe@example.com");
        invalidSignUpRequest.put("password", "password123");

        loginRequest = new HashMap<>();
        loginRequest.put("email", "john.doe@example.com");
        loginRequest.put("password", "password123");
    }

    @Test
    public void signUp_ValidRequest_Success() {
        when(userDao.findByEmailId(anyString())).thenReturn(null);

        ResponseEntity<String> response = userService.signUp(validSignUpRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("{\"message\":\"SignUp is done\"}", response.getBody());
    }

    @Test
    public void signUp_EmailExists_Conflict() {
        when(userDao.findByEmailId(anyString())).thenReturn(new User());

        ResponseEntity<String> response = userService.signUp(validSignUpRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("{\"message\":\"Email exists already\"}", response.getBody());
    }

    @Test
    public void signUp_InvalidRequest_BadRequest() {
        ResponseEntity<String> response = userService.signUp(invalidSignUpRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("{\"message\":\"Invalid Data!\"}", response.getBody());
    }
//@Test
//    public void loginSucess(){
//        Map<String ,String> rm = new HashMap<>();
//
//        rm.put("email","pg@gmail.com");
//        rm.put("password","passsss");
//    ResponseEntity<String> response = userService.login(rm);
//        assertEquals(HttpStatus.OK,response.getStatusCode());
}
//    @Test
//   public void testLoginSuccess() {
//        Map<String, String> requestMap = new HashMap<>();
//        requestMap.put("email", "john@example.com");
//        requestMap.put("password", "password");
//
//        // Mock the behavior of CustomerUserDetailsService
//        UserDetails userDetails = Mockito.mock(UserDetails.class);
//        Mockito.when(userDetails.getUsername()).thenReturn("john@example.com");
//        Mockito.when(userDetails.getPassword()).thenReturn("encodedPassword");  // Replace with the actual encoded password
//
//        Mockito.when(authenticationManager.authenticate(any()))
//                .thenReturn(authentication);
//        Mockito.when(authentication.isAuthenticated()).thenReturn(true);
//        Mockito.when(customerUserDetailsService.getUserDetail()).thenReturn((userDetails));  // Mock the user details
//
//        Mockito.when(userDao.findByEmail("john@example.com"))
//                .thenReturn(createTestUser("john@example.com", "user", "true"));
//
//        ResponseEntity<String> response = userService.login(requestMap);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertTrue(response.getBody().contains("token"));
//    }

//
//    @Test
//    public void login_UserNotApproved_BadRequest() {
//        Authentication authentication = mock(Authentication.class);
//        when(authenticationManager.authenticate(any())).thenReturn(authentication);
//        when(authentication.isAuthenticated()).thenReturn(true);
//        when(customerUserDetailsService.getUserDetail()).thenReturn(new UserDetail("john.doe@example.com", "user", "false"));
//
//        ResponseEntity<String> response = userService.login(loginRequest);
//
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//
//    }




