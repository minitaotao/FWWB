package com.example.fwwbjava1_8.Mysql_class.Mapper;


import com.example.fwwbjava1_8.Mysql_class.model.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
@Component
public interface AdminMapper {
    @Select("select * from admin where phone_num=#{phone_num} and password=#{password}")
    Admin AdminLogin(@Param("phone_num") String account, @Param("password") String password);
    @Select("select * from admin where id=#{id}")
    Admin GetAdminById(int id);
}
