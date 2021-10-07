package org.wecango.wecango.Base.Image.Service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.wecango.wecango.Base.Image.Dto.UploadFileResDto;
import org.wecango.wecango.Preference.CustomPreference;

import java.io.IOException;
import java.net.URL;

import static java.util.UUID.randomUUID;

@Service
public class S3ImageUploadService {
    private AmazonS3 s3Client;
    private Regions clientRegion = Regions.AP_NORTHEAST_2;
    private String bucket = "wecango";

    public S3ImageUploadService(CustomPreference customPreference) {
        AWSCredentials credentials = new BasicAWSCredentials(customPreference.AWSAccessKey(), customPreference.AWSSecretKey());
        this.s3Client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(clientRegion)
                .build();

    }

    public UploadFileResDto upload(MultipartFile file) throws IOException {

        String fileName = file.getOriginalFilename();
        String key = randomUUID().toString();
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());
        s3Client.putObject(new PutObjectRequest(bucket, key, file.getInputStream(), objectMetadata)
                .withCannedAcl(CannedAccessControlList.PublicRead));

        URL url = s3Client.getUrl(this.bucket, key);

        UploadFileResDto uploadFileResDto = new UploadFileResDto();
        uploadFileResDto.setFileName(fileName);
        uploadFileResDto.setImageUrl(url.toString());

        return uploadFileResDto;
    }


}
