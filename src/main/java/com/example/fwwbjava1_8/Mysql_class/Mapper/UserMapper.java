package com.example.fwwbjava1_8.Mysql_class.Mapper;

import com.example.fwwbjava1_8.Mysql_class.model.CompleteUserInfo;
import com.example.fwwbjava1_8.Mysql_class.model.User;
import com.example.fwwbjava1_8.Mysql_class.model.RegisterForm;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
@Component
public interface UserMapper {
    @Select("select * from user where phone_num=#{phone_num} and password=#{password}")
    User login(@Param("phone_num") String account, @Param("password") String password);

    @Select("select * from user where id=#{id}")
    User getUser(@Param("id") Integer id);

    @Update("update user set balance =#{balance} where id=#{id}")
    int UpdateUserBalance(User user);

    @Select("select * from user where id_card=#{id_card}")
    User IsRegistered(RegisterForm form);

    @Insert("insert into user(id_card,phone_num,password,name) values(#{id_card},#{phone_num},#{password},#{name})")
    int Register(RegisterForm form);

    @Update("update user set age=#{age},income=#{income},working_status=#{working_status} where id=#{id}")
    int CompleteInfo(CompleteUserInfo completeUserInfo);

    @Update("update user set balance = #{income} + balance  where id=204")
    void UpdateBankBalance(int income);

    @Select("select balance from user where id=204")
    long GetBankBalance();
}
