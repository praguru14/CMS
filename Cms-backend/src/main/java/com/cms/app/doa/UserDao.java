package com.cms.app.doa;



import com.cms.app.POJO.User;
import com.cms.app.wrapper.UserWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import java.util.List;

import javax.transaction.Transactional;
import java.util.List;

public interface UserDao extends JpaRepository<User, Integer> {

    //Create query to check whether email already exist

    User findByEmailId(@Param("email") String email);

    List<UserWrapper> getAllUser();
    //List<UserWrapper> getAllUser();


    @Transactional
    @Modifying
    Integer updateStatus(@Param("status") String status,@Param("id") Integer id);


    List<String> getAllAdmins();
}
