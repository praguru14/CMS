package com.cms.app.POJO;

import javax.persistence.*;

import jdk.jfr.Name;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;


@NamedQuery(name = "User.findByEmailId", query = "select u from User u where u.email = :email")
@NamedQuery(name="User.getAllUser",query = "select new com.cms.app.wrapper.UserWrapper(u.id,u.name,u.contactNumber,u.email,u.status) from User u where u.role = 'user'")
@NamedQuery(name= "User.updateStatus",query = "update User u set u.status=:status where u.id=:id")
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "user")
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "contactNumber")
    private String contactNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "status")
    private String status;

    @Column(name = "role")
    private String role;

}