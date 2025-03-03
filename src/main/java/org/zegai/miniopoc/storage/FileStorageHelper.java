package org.zegai.miniopoc.storage;

import org.zegai.miniopoc.ApiApp;
import org.zegai.miniopoc.entity.FileDescriptor;

import java.util.Set;

public class FileStorageHelper {

    private FileStorageHelper() {
    }

    public static String getFirstPreviewFileUrl(Set<? extends FileDescriptor> fileDescriptors, FileStorageFolder folder) {
        if(fileDescriptors.isEmpty()) {
            return null;
        }

        return getPreviewFileUrl(fileDescriptors.iterator().next(), folder);
    }

    public static String getPreviewFileUrl(FileDescriptor fileDescriptor, FileStorageFolder folder) {
        FileStorageService fileStorageService = ApiApp.getApplicationContext().getBean(FileStorageService.class);
        return fileStorageService.getPreviewFileUrl(fileDescriptor, folder);
    }
}
