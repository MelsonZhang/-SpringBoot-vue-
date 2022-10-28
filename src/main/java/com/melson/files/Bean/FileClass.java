package com.melson.files.Bean;

import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Builder
public class FileClass {
   public    String fileName;
   public String fileTime;

   public int fileSize;

}
