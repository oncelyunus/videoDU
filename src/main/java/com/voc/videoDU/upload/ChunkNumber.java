package com.voc.videoDU.upload;

public class ChunkNumber {

    private int number;

    public ChunkNumber(int number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ChunkNumber &&
                ((ChunkNumber)obj).getNumber() == this.getNumber();
    }

    @Override
    public int hashCode() {
        return number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
