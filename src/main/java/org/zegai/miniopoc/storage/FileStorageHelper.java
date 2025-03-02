package org.zegai.miniopoc.storage;

import org.zegai.miniopoc.entity.FileDescriptor;

import java.util.Set;

public class FileStorageHelper {

    private FileStorageService fileStorageService;
    
    private FileStorageHelper() {
    }

    public static String getFirstPreviewFileUrl(Set<? extends FileDescriptor> fileDescriptors, FileStorageFolder folder) {
        if(fileDescriptors.isEmpty()) {
            return null;
        }

        return getPreviewFileUrl(fileDescriptors.iterator().next(), folder);
    }

    public String getPreviewFileUrl(FileDescriptor fileDescriptor, FileStorageFolder folder) {
        return fileStorageService.getPreviewFileUrl(fileDescriptor, folder);
    }
}
