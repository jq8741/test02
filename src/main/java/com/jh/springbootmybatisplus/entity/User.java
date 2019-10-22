package com.jh.springbootmybatisplus.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import sun.plugin2.message.Message;

import javax.validation.constraints.*;

@Data
public class User {

    private int id;

    @NotBlank(message = "用户名不能为空")
    @Length(min= 1,max = 50,message = "用户名只能是1-50个字符")
    private String userName;

    @Max(value = 150,message = "年龄不能超过150岁")
    private int age;

    @Email(message = "邮箱格式不正确")
    private String email;

    @NotBlank(message = "密码不能为空")
    @Length(min = 1,max = 50,message = "密码只能是1-50个字符")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
