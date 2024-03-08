package com.example.fwwbjava1_8.Mysql_class.Controller;


import com.example.fwwbjava1_8.Mysql_class.Service.*;
import com.example.fwwbjava1_8.Mysql_class.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@SessionAttributes
public class ExposerController {

    @Autowired
    private ExposerService exposerService;
    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private AuthService authService;
    @Autowired
    private CheckService checkService;
    @Autowired
    private AdminService adminService;


    @RequestMapping(value = "/api/shop/item/getUrl")
    public ResultData exposer(@CookieValue String token, @RequestParam int id) {
        //goodsId表示是什么商品，然后根据该商品的数据库依次获得尚未被秒杀的每个商品的唯一ID，然后根据商品的唯一ID来生成唯一的秒杀URL
        //seckillGoodsId为某个商品的唯一id
        //其中这一步可以省，之间将itemId表示的传递给exportSeckillUrl也可以完成
        //异常判断省掉，返回上述的model对象。即包含md5的对象
        User user = authService.SearchUserByToken(token);
        if (user==null){
            return ResultData.fail(403,"用户不存在");
        }
        if(authService.IsTokenExpired(user.getId()))
            return ResultData.fail(500,"token过期");
        else if(!authService.isComplete(user))
            return ResultData.fail(520,"信息不完整");
        else{
            if(adminService.isAdmin(token))
                return ResultData.fail(500,"管理员不能购买");
        else if(checkService.isPermitted(token,id)==0)
                return ResultData.fail(500,"失信人员不能购买");
        else if(checkService.isPermitted(token,id)==-1)
                return ResultData.fail(500,"年龄不符合要求");
        else if(checkService.isPermitted(token,id)==-2)
                return ResultData.fail(500,"收入不符合要求");
        else if(checkService.isPermitted(token,id)==-3)
                return ResultData.fail(500,"超出限购数量");
        else
            return ResultData.success(exposerService.exportSeckillUrl(id));
        }
    }


    @PostMapping(value = "/api/shop/item/execution")
    public ResultData execution(@RequestParam String md5, @CookieValue String token) {
        //executionSeckillId的操作是强事务,操作为减库存+增加购买明细，最终返回是否秒杀成功，秒杀成功的商品消息等。
        int userId = authService.SearchIdByToken(token);
        int itemId = exposerService.GetItemId(md5);
        if(authService.IsTokenExpired(userId))
            return ResultData.fail(500,"token过期");
        else {
            if (itemId == 0) {
                return ResultData.fail(403,"商品不存在");
            } else {
                return purchaseService.AddOrderANDBill(itemId, userId, 1);
            }
        }
    }
}

