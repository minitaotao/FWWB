package com.example.fwwbjava1_8.Mysql_class.Service;

import com.example.fwwbjava1_8.Mysql_class.model.Exposer;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;


import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Repository
public class ExposerService {

    static Map<String,Integer> map = new ConcurrentHashMap();

    private String getMD5(int itemId)
    {
        long seed1 = System.nanoTime();
        Random r = new Random(seed1);
        double k = r.nextDouble()*100;

        String salt="Earth_miao"+k+"miao_Roar!";
        //结合秒杀的商品id与混淆字符串生成通过md5加密
        String base=itemId+"/"+salt;
        return DigestUtils.md5DigestAsHex(base.getBytes());
    }

    public Exposer exportSeckillUrl(int itemId) {
        //首页根据该seckillGoodsId判断商品是否还存在。
        //如果不存在则表示已经被秒杀
        String md5 = getMD5(itemId);
        map.put(md5,itemId);
        return new Exposer(md5);
    }

    public int GetItemId(String md5) {
        return map.get(md5);
    }
}



