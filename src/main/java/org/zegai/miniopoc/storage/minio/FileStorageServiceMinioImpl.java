package org.zegai.miniopoc.storage.minio;

import org.zegai.miniopoc.entity.FileDescriptor;
import org.zegai.miniopoc.exception.UnprocessableRequestException;
import org.zegai.miniopoc.storage.FileStorageFolder;
import org.zegai.miniopoc.storage.FileStorageService;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class FileStorageServiceMinioImpl implements FileStorageService {

    private final MinioClient minioClient;
    private final MinioConfigProperties minioConfigProperties;

    @Override
    public void saveFile(FileDescriptor fileDescriptor, FileStorageFolder folder, InputStream inputStream) {
        saveFile(inputStream, getFileName(fileDescriptor, folder), fileDescriptor.getSize());
    }

    @Override
    public void saveFile(InputStream inputStream, String fileName, Long fileSize) {
        try (inputStream) {
            PutObjectArgs args = PutObjectArgs.builder()
                .bucket(minioConfigProperties.getBucket())
                .object(fileName)
                .stream(inputStream, inputStream.available(), -1)
                .build();
            minioClient.putObject(args);
        } catch (Exception e) {
            throw new UnprocessableRequestException("error.storage.object", e);
        }
    }

    @Override
    public void removeFile(FileDescriptor fileDescriptor, FileStorageFolder folder) {
        try {
            RemoveObjectArgs args = RemoveObjectArgs.builder()
                .bucket(minioConfigProperties.getBucket())
                .object(getFileName(fileDescriptor, folder))
                .build();
            minioClient.removeObject(args);
        } catch (Exception e) {
            throw new UnprocessableRequestException("error.storage.object", e);
        }
    }

    @Override
    public String getPreviewFileUrl(FileDescriptor fileDescriptor, FileStorageFolder folder) {
        var previewDurationInHours = folder.isPublic() ? minioConfigProperties.getPublicPreviewExpiresInHours() :
            minioConfigProperties.getPrivatePreviewExpiresInHours();

        return generatePreSignedUrl(fileDescriptor, folder, previewDurationInHours);
    }

    @Override
    public String generatePreSignedUrl(FileDescriptor fileDescriptor, FileStorageFolder folder, int expireInHours) {
        GetPresignedObjectUrlArgs args = GetPresignedObjectUrlArgs.builder()
            .method(Method.GET)
            .bucket(minioConfigProperties.getBucket())
            .object(getFileName(fileDescriptor, folder))
            .expiry(expireInHours, TimeUnit.HOURS)
            .build();
        try {
            return minioClient.getPresignedObjectUrl(args);
        } catch (Exception e) {
            throw new UnprocessableRequestException("error.storage.object", e);
        }
    }
}
