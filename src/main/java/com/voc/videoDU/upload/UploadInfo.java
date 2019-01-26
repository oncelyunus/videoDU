package com.voc.videoDU.upload;

import org.springframework.util.StringUtils;

import java.io.File;
import java.util.HashSet;

public class UploadInfo {

    private int chunkSize;
    private long totalSize;
    private String identifier;
    private String filename;
    private String filePath;
    private HashSet<ChunkNumber> uploadedChunks = new HashSet<ChunkNumber>();

    public boolean valid() {
        return !(chunkSize < 0 || totalSize < 0
                || StringUtils.isEmpty(identifier)
                || StringUtils.isEmpty(filename));
    }

    public boolean checkIfUploadFinished() {
        int count = (int) Math.ceil(((double) totalSize) / ((double) chunkSize));
        for(int i = 1; i < count; i ++) {
            if (!uploadedChunks.contains(new ChunkNumber(i))) {
                return false;
            }
        }

        //Upload bitti, change filename.
        File file = new File(filePath);
        String new_path = file.getAbsolutePath().substring(0, file.getAbsolutePath().length() - ".temp".length());
        file.renameTo(new File(new_path));
        return true;
    }

    public int getChunkSize() {
        return chunkSize;
    }

    public void setChunkSize(int chunkSize) {
        this.chunkSize = chunkSize;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public HashSet<ChunkNumber> getUploadedChunks() {
        return uploadedChunks;
    }

    public void setUploadedChunks(HashSet<ChunkNumber> uploadedChunks) {
        this.uploadedChunks = uploadedChunks;
    }
}
