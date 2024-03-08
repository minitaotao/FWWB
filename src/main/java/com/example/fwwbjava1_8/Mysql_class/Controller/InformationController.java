package com.example.fwwbjava1_8.Mysql_class.Controller;

import com.example.fwwbjava1_8.Mysql_class.Service.AuthService;
import com.example.fwwbjava1_8.Mysql_class.model.ResultData;
import com.example.fwwbjava1_8.Mysql_class.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@SessionAttributes
public class InformationController {

    @Autowired
    private AuthService authService;

    @GetMapping(value = "/api/user/information")
    public ResultData GetInformation(@CookieValue("token") String token){
        int id;
        User user = authService.SearchUserByToken(token);
        int userId = user.getId();
        if(authService.IsTokenExpired(userId))
            return ResultData.fail(500,"token过期");
        else {
            try {
                id = authService.SearchIdByToken(token);
            } catch (Exception e) {
                return ResultData.fail(403,"用户不存在");
            }
            return ResultData.success(authService.SearchUserById(id));
        }
    }
}
