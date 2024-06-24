package com.firstversion.musicmanager.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {
    @Autowired
    Cloudinary cloudinary;

    public Map uploadFile(MultipartFile file, String folderName) throws IOException {
        return cloudinary.uploader().upload(file.getBytes(),
                ObjectUtils.asMap(
                        "folder", folderName
                ));
    }

    public Map uploadImage(MultipartFile file) throws IOException {
        return cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
    }

    public Map uploadVideo(MultipartFile file) throws IOException {
        return cloudinary.uploader().upload(file.getBytes(),
                ObjectUtils.asMap(
                        "resource_type", "video"
                ));
    }
    private String extractPublicId(String fileUrl) {
        String[] parts = fileUrl.split("/");
        String publicIdWithFormat = parts[parts.length - 1];
        return publicIdWithFormat.split("\\.")[0];
    }
    public void deleteFile(String fileUrl) {
        try {
            String publicId = extractPublicId(fileUrl);
            Map result = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to delete file from Cloudinary: " + fileUrl);
        }
    }
}
