package com.springboot.Service.Cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.springboot.Utility.Utilities;
import lombok.AllArgsConstructor;
import org.cloudinary.json.JSONObject;
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

    public String uploadFile(MultipartFile file) throws IOException {
        JSONObject jsonObject = new JSONObject();
        Map uploadedImages = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type","image"));
        jsonObject.put("imageUrl", Utilities.stringValue(uploadedImages.get("secure_url")));
        jsonObject.put("publicIp", Utilities.stringValue(uploadedImages.get("public_id")));
        return jsonObject.toString();
    }
}
