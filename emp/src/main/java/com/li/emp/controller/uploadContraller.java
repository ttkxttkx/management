package com.li.emp.controller;


import com.li.emp.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
public class uploadContraller {
//整个类是用来文件上传 即浏览器端上传文件到服务器端 并保存到本地磁盘--服务端的磁盘
    @PostMapping("/upload")
    public Result upload(String name,String age,MultipartFile file) throws IOException {
        System.out.println(file.getOriginalFilename());
        log.info("上传文件:{}{}{}",name,age,file);
        String filename= UUID.randomUUID().toString()+file.getOriginalFilename();//随机生成文件名
        file.transferTo(new File("D:\\IDEA\\tmp\\"+  filename));
        return Result.success();
    }
}
