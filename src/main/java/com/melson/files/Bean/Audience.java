package com.melson.files.Bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 配置实体类
 */
@Data
@ConfigurationProperties(prefix = "audience")
@Component
public class Audience {

    //客户端id
    private String clientId;
    //经过base64加密后密钥
    private String base64Secret;
    //签发主题
    private String name;
    //过期时间
    private int expiresSecond;
}
