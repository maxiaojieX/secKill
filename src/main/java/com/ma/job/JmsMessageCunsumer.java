package com.ma.job;

import com.alibaba.fastjson.JSON;
import com.ma.entity.Phone;
import com.ma.mapper.PhoneMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * Created by Administrator on 2017/12/6 0006.
 */
@Component
public class JmsMessageCunsumer {

    @Autowired
    private PhoneMapper phoneMapper;
    @Autowired
    private JedisPool jedisPool;

    @JmsListener(destination = "secKill-Quere")
    public void messageCunsumer(String message) {

        System.out.println("》》》》》》这是消息消费端的输出"+message);

        String phoneId = message.split("\"")[3];
        String key = "phone:"+phoneId+"list";
        System.out.println(key);

        try(Jedis jedis = jedisPool.getResource()) {
            Long len = jedis.llen(key);
            System.out.println(">>>>>>>>>>>获取剩余数量:"+len);
            String phoneKey = "phone:"+phoneId;
            System.out.println(phoneKey);
            String json = jedis.get(phoneKey);
            System.out.println(">>JSON:>>"+json);
            Phone phone = JSON.parseObject(json,Phone.class);
            phone.setOther(len.toString());
            phoneMapper.updateByPrimaryKeySelective(phone);
            System.out.println("数据库已更新");
        }


    }

}
