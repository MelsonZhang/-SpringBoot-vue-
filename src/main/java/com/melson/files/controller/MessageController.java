package com.melson.files.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.melson.files.Bean.Message;
import com.melson.files.Utils.RedisCacheUtil;
import com.melson.files.service.CacheService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class MessageController {
    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private CacheService cacheService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    WebSocket webSocket=new WebSocket();
    @GetMapping("/getMessages")
    public String getMessages() {
        int listLength = Math.toIntExact(redisTemplate.opsForList().size("MyMessages"));
        List<String> messagesList = redisTemplate.opsForList().range("MyMessages", 0, listLength - 1);
//        System.out.println("messages的长度是"+messagesList.size());
        return JSON.toJSONString(messagesList);
    }

    @GetMapping("/clearMessages")
    public void clearMessages() {
        redisTemplate.delete("MyMessages");

    }

    @GetMapping("/RedisTest")
    public String redisTest() {
        int listLength = Math.toIntExact(redisTemplate.opsForList().size("MyMessages"));
        List<String> messagesList = redisTemplate.opsForList().range("MyMessages", 0, listLength - 1);
        return JSON.toJSONString(messagesList);
    }

    @PostMapping("/SendMessage")
    public String sendMessage(HttpServletRequest request) {
        String message = request.getParameter("message");
        String time=request.getParameter("time");
        String user=request.getParameter("user");
        Message message1=Message.builder().userIP(user).time(time).message(message).build();
        System.out.println("收到消息"+message1.toString());
        try {
            JSONObject jsonObject = (JSONObject) JSON.toJSON(message1);
            System.out.println(jsonObject.toJSONString());
            redisTemplate.opsForList().leftPush("MyMessages", jsonObject.toJSONString());
            webSocket.sendAllMessage("");
            return "ok";
        } catch (Exception e) {
            return "error";
        }
    }
}
