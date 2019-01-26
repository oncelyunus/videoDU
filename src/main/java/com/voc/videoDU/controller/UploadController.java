package com.voc.videoDU.controller;

import com.voc.videoDU.dto.UploadDTO;
import com.voc.videoDU.service.UploadService;
import com.voc.videoDU.upload.ChunkNumber;
import com.voc.videoDU.upload.InfoStorage;
import com.voc.videoDU.upload.UploadInfo;
import com.voc.videoDU.upload.UploadStatus;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
public class UploadController {

    @Value("${upload.location:#{systemProperties['java.io.tmpdir']}}")
    private String uploadLocation;

    @Autowired
    private UploadService uploadService;

    @PostMapping("/upload/init")
    public void getDeneme(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String fileName = RandomStringUtils.randomAlphanumeric(10).toLowerCase();
        Long fileSize = Long.parseLong(request.getHeader("Y-Content-Length"));
        String orginalFileName = request.getHeader("Y-Content-Name");
        Integer chunksQuantity = Integer.parseInt(request.getHeader("Y-Chunks-Quantity"));
        fileName += fileName + "_" + chunksQuantity;

        String basename = FilenameUtils.getBaseName(orginalFileName);
        String extension = FilenameUtils.getExtension(orginalFileName);

        UploadDTO uploadDTO = new UploadDTO();
        uploadDTO.setChunkQuantity(chunksQuantity);
        uploadDTO.setExtension(extension);
        uploadDTO.setName(basename);
        uploadDTO.setStart(System.currentTimeMillis());
        uploadService.uploadInit(fileName, uploadDTO);
        response.getWriter().print(fileName);

    }

    @GetMapping(value = "/upload/deneme")
    public String deneme() {
        return "Deneme Successfully";
    }

    @PostMapping(value = "/upload/video")
    public void upload(HttpServletRequest request,
                       HttpServletResponse response,
                       @RequestParam MultipartFile file,
                       @RequestParam Integer chunkNumber,
                       @RequestParam Integer chunkSize,
                       @RequestParam Long totalSize,
                       @RequestParam String identifier,
                       @RequestParam String fileName) throws IOException {


        String tempFilePath = uploadLocation + File.separator + fileName + ".temp";
        InfoStorage storage = InfoStorage.getInstance();
        UploadInfo info = storage.get(chunkSize, totalSize,
                identifier, fileName, tempFilePath);

        if (!info.valid())         {
            storage.remove(info);
            throw new IllegalArgumentException("Invalid request params.");
        }

        RandomAccessFile raf = new RandomAccessFile(info.getFilePath(), "rw");

        //Seek to position
        raf.seek((chunkNumber ) * (long)info.getChunkSize());

        InputStream is = file.getInputStream();
        long readed = 0;
        long content_length = file.getSize();
        byte[] bytes = new byte[1024 * 100];
        while(readed < content_length) {
            int r = is.read(bytes);
            if (r < 0)  {
                break;
            }
            raf.write(bytes, 0, r);
            readed += r;
        }
        raf.close();

        info.getUploadedChunks().add(new ChunkNumber(chunkNumber));

        if (info.checkIfUploadFinished()) { //Check if all chunks uploaded, and change filename
            InfoStorage.getInstance().remove(info);
            response.getWriter().print("{\"status\":\"Finished\"}");
        } else {
            response.getWriter().print("{\"status\":\"Upload\"}");
        }
    }

    private File createOrRetrieve(final String target) throws IOException {

        final Path path = Paths.get(target);

        if(Files.notExists(path)){
            System.out.println("Target file \"" + target + "\" will be created.");
            return Files.createFile(Files.createDirectories(path)).toFile();
        }
        System.out.println("Target file \"" + target + "\" will be retrieved.");
        return path.toFile();
    }

}
