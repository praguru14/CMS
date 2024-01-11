package com.cms.app.doa;



import com.cms.app.POJO.User;
import com.cms.app.wrapper.UserWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserDao extends JpaRepository<User, Integer> {

    //Create query to check whether email already exist

    User findByEmailId(@Param("email") String email);

    List<UserWrapper> getAllUser();
}
