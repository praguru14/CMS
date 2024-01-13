package com.cms.app.doa;

import com.cms.app.POJO.Category;
import com.cms.app.POJO.User;
import com.cms.app.wrapper.UserWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CategoryDao extends JpaRepository<Category,Integer> {

    List<Category> getAllCategory();
}
