package com.cms.app.JWT;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;

//    @Bean
//    public  CustomerUserDetailsService userDetailsService(){
//        return new CustomerUserDetailsService();
//    }
    Claims claims = null;

    private String userName = null;



    //Token Validation
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        //bypass userlogin , signup and forget password
        if(httpServletRequest.getServletPath().matches("/user/login | /user/forgetPassword | /user/signup | /actuator ")){
            filterChain.doFilter(httpServletRequest,httpServletResponse); //bypass
        }
        else{
            String authorizationHeader = httpServletRequest.getHeader("Authorization");
            String token = null;


            //check for bearer
            if(authorizationHeader!=null && authorizationHeader.startsWith("Bearer ")){
                token = authorizationHeader.substring(7);
                userName = jwtUtil.extractUserName(token); //extract Username
                claims = jwtUtil.extractAllClaims(token); //extract Claims
            }
            if(userName !=null && SecurityContextHolder.getContext().getAuthentication()==null){
                UserDetails userDetails = customerUserDetailsService.loadUserByUsername(userName);
                if(jwtUtil.validateToken(token,userDetails)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(httpServletRequest)
                    );

                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
            filterChain.doFilter(httpServletRequest,httpServletResponse);
        }


    }
    public String admin(){
     return "admin";
    }
    public boolean isAdmin(){

        return "admin".equalsIgnoreCase((String )claims.get("role"));
    }


    public boolean isUser(){
        return "user".equalsIgnoreCase((String )claims.get("role"));
    }

    public String getCurrentUser(){
        return userName;
    }
}
