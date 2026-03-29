package com.springboot.Service.Cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@AllArgsConstructor
@Service
public class FileManager {

    private final Cloudinary cloudinary;

    public Map<String,Object> uploadFileInCloud(MultipartFile file) throws IOException {
        Map uploadedImages = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type","image"));
        return Map.of("imageUrl",uploadedImages.get("secure_url").toString(),"public_id",uploadedImages.get("public_id").toString());
    }
}
