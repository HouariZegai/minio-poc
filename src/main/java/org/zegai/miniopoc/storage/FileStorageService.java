package org.zegai.miniopoc.storage;

import org.zegai.miniopoc.entity.FileDescriptor;
import org.zegai.miniopoc.exception.UnprocessableRequestException;

import java.io.InputStream;

public interface FileStorageService {

    void saveFile(FileDescriptor fileDescriptor, FileStorageFolder folder, InputStream inputStream);

    void saveFile(InputStream inputStream, String fileName, Long fileSize);

    void removeFile(FileDescriptor fileDescriptor, FileStorageFolder folder) throws UnprocessableRequestException;

    String getPreviewFileUrl(FileDescriptor fileDescriptor, FileStorageFolder folder);

    String generatePreSignedUrl(FileDescriptor fileDescriptor, FileStorageFolder folder, int expireInHours);

    default String getFileName(FileDescriptor fileDescriptor, FileStorageFolder folder) {
        return folder.name() + "/" + fileDescriptor.getId().toString() + "." + fileDescriptor.getExtension();
    }
}
