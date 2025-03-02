package org.zegai.miniopoc.storage.s3;

import org.zegai.miniopoc.entity.FileDescriptor;
import org.zegai.miniopoc.exception.UnprocessableRequestException;
import org.zegai.miniopoc.storage.FileStorageFolder;
import org.zegai.miniopoc.storage.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.InputStream;
import java.time.Duration;

@RequiredArgsConstructor
//@Service
public class FileStorageServiceS3Impl implements FileStorageService {

    private static final int PREVIEW_PRIVATE_FILE_DURATION_IN_HOURS = 6;

    @Value("${aws.s3.bucket}")
    private String bucket;

    @Value("${aws.cloud-front.base-path}")
    private String cloudFrontBasePath;

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;

    @Override
    public void saveFile(FileDescriptor fileDescriptor, FileStorageFolder folder, InputStream inputStream) {
        String fileName = getFileName(fileDescriptor, folder);

        saveFile(inputStream, fileName, fileDescriptor.getSize());
    }

    @Override
    public void saveFile(InputStream inputStream, String fileName, Long fileSize) {
        PutObjectRequest objectRequest = PutObjectRequest.builder()
            .bucket(bucket)
            .key(fileName)
            .build();

        try(inputStream) {
            s3Client.putObject(objectRequest, RequestBody.fromInputStream(inputStream, fileSize));
        } catch (Exception e) {
            throw new UnprocessableRequestException("error.storage.object", e);
        }
    }

    @Override
    public void removeFile(FileDescriptor fileDescriptor, FileStorageFolder folder) {
        String fileName = getFileName(fileDescriptor, folder);
        DeleteObjectRequest objectRequest = DeleteObjectRequest.builder()
            .bucket(bucket)
            .key(fileName)
            .build();

        try {
            s3Client.deleteObject(objectRequest);
        } catch (Exception e) {
            throw new UnprocessableRequestException("error.storage.object", e);
        }
    }

    @Override
    public String getPreviewFileUrl(FileDescriptor fileDescriptor, FileStorageFolder folder) {
        if(folder.isPublic()) {
            return cloudFrontBasePath + "/" + getFileName(fileDescriptor, folder);
        }
        return generatePreSignedUrl(fileDescriptor, folder, PREVIEW_PRIVATE_FILE_DURATION_IN_HOURS);
    }

    @Override
    public String generatePreSignedUrl(FileDescriptor fileDescriptor, FileStorageFolder folder, int durationInHours) {
        String fileName = getFileName(fileDescriptor, folder);

        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
            .bucket(bucket)
            .key(fileName)
            .build();

        GetObjectPresignRequest getObjectPresignRequest = GetObjectPresignRequest.builder()
            .signatureDuration(Duration.ofHours(durationInHours))
            .getObjectRequest(getObjectRequest)
            .build();

        PresignedGetObjectRequest presignedGetObjectRequest = s3Presigner.presignGetObject(getObjectPresignRequest);
        return presignedGetObjectRequest.url().toString();
    }
}
