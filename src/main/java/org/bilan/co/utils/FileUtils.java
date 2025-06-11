package org.bilan.co.utils;

import org.springframework.web.multipart.MultipartFile;

public class FileUtils {

    public static String getExtension(MultipartFile file){
        String originalFilename = file.getOriginalFilename();

        if (originalFilename != null && originalFilename.contains(".")) {
            return originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        return ""; // Return empty string if no extension is found
    }
}
