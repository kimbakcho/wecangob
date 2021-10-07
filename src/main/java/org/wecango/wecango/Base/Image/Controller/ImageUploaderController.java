package org.wecango.wecango.Base.Image.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.wecango.wecango.Base.Image.Dto.UploadFileResDto;
import org.wecango.wecango.Base.Image.Service.S3ImageUploadService;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/imageUpload")
public class ImageUploaderController {

    final S3ImageUploadService s3ImageUploadService;

    @PostMapping
    public UploadFileResDto upload(@RequestParam MultipartFile file ) throws IOException {
        return s3ImageUploadService.upload(file);
    }

}
