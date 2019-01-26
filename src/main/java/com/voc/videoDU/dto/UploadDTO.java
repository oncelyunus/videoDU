package com.voc.videoDU.dto;

import java.io.Serializable;

public class UploadDTO implements Serializable {
    private String name;
    private String extension;
    private Long start = System.currentTimeMillis();
    private Long end;
    private int chunkQuantity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Long getStart() {
        return start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public Long getEnd() {
        return end;
    }

    public void setEnd(Long end) {
        this.end = end;
    }

    public int getChunkQuantity() {
        return chunkQuantity;
    }

    public void setChunkQuantity(int chunkQuantity) {
        this.chunkQuantity = chunkQuantity;
    }
}
