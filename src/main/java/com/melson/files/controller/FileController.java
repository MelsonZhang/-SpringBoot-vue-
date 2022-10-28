package com.melson.files.controller;

import com.alibaba.fastjson.JSON;
import com.melson.files.Bean.FileClass;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


@RestController
public class FileController {
    private final static String mDownloadDir="/root/Melson/FileList";
    private final static  String mFilePath="/root/Melson/FileList/";

    @PostMapping("/login")
    public String Login(HttpServletRequest request) {
        String password = request.getParameter("password");
        System.out.println(password);
        //根据用户id生成token
        if (!password.equals("hhhhhhh")) {
            return "error";
        } else {

            request.getSession().setAttribute("loginName", "admin");
            return "ok";


        }


    }

    @PostMapping("/upload")
    public String upload(@RequestPart("file") MultipartFile multipartFile) throws IOException {

        String fileName = multipartFile.getOriginalFilename();
        System.out.println(fileName);
        File file = new File(mFilePath + fileName);
        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = new FileOutputStream(file);
            IOUtils.copy(multipartFile.getInputStream(), fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
//                关闭
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();

            }
        }

        return "文件上传成功";
    }


    @PostMapping("/getFile")
    public String getFiles() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss ");
        File dir = new File(mDownloadDir);
        String filePath=mFilePath;
        List<FileClass> file1List=new ArrayList<FileClass>();
        String[] children = dir.list();
        if (children == null) {
            System.out.println("目录不存在或它不是一个目录");
        } else {
            for (int i = 0; i < children.length; i++) {
                try {
                    FileTime t = Files.readAttributes(Paths.get(filePath+children[i]), BasicFileAttributes.class).creationTime();
                    String createTime = dateFormat.format(t.toMillis());
                    FileInputStream fis=new FileInputStream(filePath+children[i]);
                    FileClass file1=FileClass.builder().fileName(children[i]).fileTime(createTime).fileSize(fis.available()).build();
                    file1List.add(file1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
        return JSON.toJSONString(file1List);
    }

    @RequestMapping("/download")
    public String fileDownLoad(HttpServletResponse response, @RequestParam("fileName") String fileName){
        File file = new File(mFilePath+ fileName);//文件路径+文件名
        if(!file.exists()){
            return "下载文件不存在";
        }
        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment;filename=" +fileName );

        try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));) {
            byte[] buff = new byte[1024];
            OutputStream os  = response.getOutputStream();
            int i = 0;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
        } catch (IOException e) {

            return "下载失败";
        }
        return "下载成功";
    }

    @PostMapping("/getFilesByMoble")
    public String getFiles1() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss ");
        File dir = new File(mDownloadDir);
        String filePath=mFilePath;
        List<FileClass> file1List=new ArrayList<FileClass>();
        String[] children = dir.list();
        if (children == null) {
            System.out.println("目录不存在或它不是一个目录");
        } else {
            for (int i = 0; i < children.length; i++) {
                try {
                    FileTime t = Files.readAttributes(Paths.get(filePath+children[i]), BasicFileAttributes.class).creationTime();
                    String createTime = dateFormat.format(t.toMillis());
                    FileInputStream fis=new FileInputStream(filePath+children[i]);
                    FileClass file1=FileClass.builder().fileName(children[i]).fileTime(createTime).fileSize(fis.available()).build();
                    file1List.add(file1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
        return JSON.toJSONString(file1List);
    }

    @RequestMapping("/downloadByMobel")
    public String fileDownLoad1(HttpServletResponse response, @RequestParam("fileName") String fileName){
        File file = new File(mFilePath+ fileName);//文件路径+文件名
        if(!file.exists()){
            return "下载文件不存在";
        }
        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment;filename=" +fileName );

        try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));) {
            byte[] buff = new byte[1024];
            OutputStream os  = response.getOutputStream();
            int i = 0;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
        } catch (IOException e) {

            return "下载失败";
        }
        return "下载成功";
    }

}


