package com.cms.app.doa;



import com.cms.app.POJO.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface UserDao extends JpaRepository<User, Integer> {

    //Create query to check whether email already exist

    User findByEmailId(@Param("email") String email);

}
