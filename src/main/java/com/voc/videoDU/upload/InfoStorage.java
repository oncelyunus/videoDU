package com.voc.videoDU.upload;

import java.util.HashMap;

public class InfoStorage {

    private HashMap<String, UploadInfo> mMap = new HashMap<String, UploadInfo>();

    private InfoStorage() {
    }

    private static InfoStorage sInstance;

    public static synchronized InfoStorage getInstance() {
        if (sInstance == null) {
            sInstance = new InfoStorage();
        }
        return sInstance;
    }

    public synchronized UploadInfo get(int chunkSize,
                                       long totalSize,
                                       String identifier,
                                       String filename,
                                       String filePath) {

        UploadInfo info = mMap.get(identifier);

        if (info == null) {
            info = new UploadInfo();

            info.setChunkSize(chunkSize);
            info.setTotalSize(totalSize);
            info.setIdentifier(identifier);
            info.setFilename(filename);
            info.setFilePath(filePath);

            mMap.put(identifier, info);
        }
        return info;
    }

    public void remove(UploadInfo info) {
        mMap.remove(info.getIdentifier());
    }

}
