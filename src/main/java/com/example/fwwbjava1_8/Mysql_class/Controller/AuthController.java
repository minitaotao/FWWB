package com.example.fwwbjava1_8.Mysql_class.Controller;

import com.example.fwwbjava1_8.Mysql_class.model.*;
import com.example.fwwbjava1_8.Mysql_class.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;


@RestController
@SessionAttributes
public class AuthController {

    @Autowired
    public AuthService authService;

    @PostMapping(value = "/api/loginuser")
    public Object login(LoginForm form, @CookieValue(required = false) String token) {

        User t_name = authService.login(form.phone_num, form.password);
        if (t_name == null) {
            //resp.status = -1;
            //resp.msg = "auth fail";
            return ResultData.fail(403,"手机号或者密码错误");
        }
        String uuid = UUID.randomUUID().toString();
        AuthService.AddToken(uuid, t_name.getId());
        if (token == null) {
            return ResponseEntity.ok().header("Set-Cookie", "token" + "=" + uuid).body(t_name.getName());
        }

        return ResponseEntity.ok().header("Set-Cookie", "token" + "=" + token + ";expires=" + new Date()).header("Set-Cookie", "token" + "=" + uuid).body(t_name.getName());


    }

    @PostMapping(value = "/api/register")
    public String register(RegisterForm form) {

        //RegisterResp resp = new RegisterResp();

        if (authService.isRegistered(form)) {
            //resp.status=-2;
            //resp.msg="用户已被注册";
            return "用户已被注册";
        } else {
            authService.register(form);
            //resp.status=0;
            //resp.msg="ok";
            // return resp;
            return "";
        }
    }

    @PostMapping(value = "/api/user/completeInfo")
    public ResultData complete(@CookieValue("token") String token, CompleteUserInfo completeUserInfo) {
        int id = authService.SearchIdByToken(token);
        User user = authService.SearchUserByToken(token);
        if (user != null) {
            int userId = user.getId();
            if (authService.IsTokenExpired(userId)) {
                return ResultData.fail(500, "token过期");
            }
        }
        completeUserInfo.setId(id);
        authService.completeUserInfo(completeUserInfo);
        return ResultData.success("操作成功");
    }
}
