package com.melson.files.Bean;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Message {

    public  String userIP;
    public  String message;
    public  String time;

}
