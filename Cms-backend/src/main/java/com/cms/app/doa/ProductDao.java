package com.cms.app.doa;

import com.cms.app.POJO.Product;
import com.cms.app.utils.ProductWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface ProductDao extends JpaRepository<Product,Integer> {

List<ProductWrapper> getAllProduct();

    @Modifying
    @Transactional
    Integer updateStatus(@Param("status") String status,@Param("id") int id);

    List<ProductWrapper> getProductByCategory(@Param("id") Integer id);

    ProductWrapper getProductById(@Param("id") Integer id);
}
