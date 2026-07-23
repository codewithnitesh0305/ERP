package com.springboot.Service.Cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.springboot.Utility.Utilities;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.cloudinary.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FileManager {

    private final Cloudinary cloudinaryBean;
    private static Cloudinary cloudinary;

    @PostConstruct
    public void init() {
        cloudinary = cloudinaryBean;
    }

    public static Map<String, Object> uploadFileInCloud(MultipartFile file) throws IOException {
        Map uploadedImages = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type", "image"));
        return Map.of("imageUrl", uploadedImages.get("secure_url").toString(), "public_id", uploadedImages.get("public_id").toString());
    }
    public static String uploadFile(MultipartFile file) throws IOException {
        if(file == null) return null;
        JSONObject jsonObject = new JSONObject();
        Map uploadedImages = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type", "image"));
        jsonObject.put("imageUrl", Utilities.stringValue(uploadedImages.get("secure_url")));
        jsonObject.put("publicIp", Utilities.stringValue(uploadedImages.get("public_id")));
        return jsonObject.toString();
    }

    public static boolean deleteFile(String file) {
        try {
            JSONObject jsonObject = new JSONObject(file);
            String publicId = Utilities.stringValue(jsonObject.get("publicIp"));
            Map result = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            return "ok".equals(result.get("result"));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}