package com.meitu.ablum.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
public class PictureController {

    @CrossOrigin(maxAge = 3600)
    @RequestMapping("/uploadFile")
    @ResponseBody
    public String uploadFile(HttpServletRequest request) {
        String imageUrl = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main"
                + File.separator + "resources" + File.separator + "static" + File.separator + "image" + File.separator;

/*        String originalFilename = formData.getOriginalFilename();
        String extName = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFile = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 5) + extName;
        File dest = new File(imageUrl + newFile);*/

        try {
            return "上传成功";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "上传失败";
    }

}
