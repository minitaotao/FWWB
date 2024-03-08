package com.example.fwwbjava1_8.Mysql_class.Controller;

import com.example.fwwbjava1_8.Mysql_class.Service.AdminService;
import com.example.fwwbjava1_8.Mysql_class.model.Router;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class RoutesController {
    public RoutesController() {
        List<Router.Children> list = new ArrayList<>();
        list.add(
                new Router.Children(
                        "/admin/dashboard",
                        "dashborad",
                        new Router.Meta2(
                                "menus.hsdashboard",
                                true,
                                null
                        )));
        list.add(
                new Router.Children(
                        "/admin/orderquery",
                        "orderquery",
                        new Router.Meta2(
                                "menus.hsorderquery",
                                true,
                                null
                        )));
        list.add(
                new Router.Children(
                        "/admin/item/index",
                        "itemadmin",
                        new Router.Meta2(
                                "menus.hsitemadmin",
                                true,
                                null
                        )));
        list.add(
                new Router.Children(
                        "/admin/item/edit",
                        "itemedit",
                        new Router.Meta2(
                                "menus.hsitemedit",
                                true,
                                null,
                                false
                        )));
        list.add(
                new Router.Children(
                        "/admin/rules/index",
                        "rules",
                        new Router.Meta2(
                                "menus.hsrules",
                                true,
                                null)));
        list.add(
                new Router.Children(
                        "/admin/rules/add",
                        "ruleadd",
                        new Router.Meta2(
                                "menus.hsruleadd",
                                true,
                                null,
                        false)));
        adminRouter = new Router(
                "/admin",
                "/admin/dashboard",

                new Router.Meta1(
                        "menus.hsadmin",
                        "lollipop",
                        true ,
                        9),
                list
        );
        list = new ArrayList<>();
        list.add(
                new Router.Children(
                "/myshop/info",
                "info",
                new Router.Meta2(
                        "menus.hsinfo",
                        true,
                        null,
                        true
                )
        ));
        list.add(
                new Router.Children(
                        "/myshop/order",
                        "myorder",
                        new Router.Meta2(
                                "menus.hsmyorder",
                                true,
                                null,
                                true
                        )
                )
        );
        list.add(
                new Router.Children(
                        "/myshop/orderdetail",
                        "myorderdetail",
                        new Router.Meta2(
                                "menus.hsmyorderdetail",
                                true,
                                null,
                                false
                        )
                )
        );
        myshopRouter = new Router(
                "/myshop",
                "/myshop/myinfo",
                new Router.Meta1(
                        "menus.hsmyshop",
                            "fa-user",
                        true,
                        4
                ),
                list
        );
    }
    @Autowired
    private AdminService adminService;
    private final Router adminRouter;
    private final Router myshopRouter;

    @GetMapping("/api/getAsyncRoutes")
    public ResponseEntity<List<Router>> AsyncRoutes(@CookieValue String token){
        List<Router> list = new ArrayList<>();
        if (adminService.isAdmin(token)){
            list.add(adminRouter);
        }else{
            list.add(myshopRouter);
        }
        return ResponseEntity.ok().body(list);
    }
}