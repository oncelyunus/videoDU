package com.voc.videoDU.service;

import com.voc.videoDU.dto.UploadDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UploadService {

    private Map<String, UploadDTO> uploadFileInfo = new ConcurrentHashMap<>();

    @Value("${upload.location:#{systemProperties['java.io.tmpdir']}}")
    private String uploadLocation;

    public void uploadInit(String fileName, UploadDTO uploadDTO) {
        File parentFile = new File(uploadLocation + File.separator + fileName);
        if(!parentFile.exists()) {
            boolean mkdir = parentFile.mkdir();
            System.out.println("Parent file created " + mkdir + " - "  + parentFile.getAbsolutePath());

            uploadFileInfo.put(fileName, uploadDTO);
            System.out.println("burada");
        }
    }


}
