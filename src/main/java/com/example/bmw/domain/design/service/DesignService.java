package com.example.bmw.domain.design.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.bmw.domain.design.entity.Design;
import com.example.bmw.domain.design.repository.DesignRepository;
import com.example.bmw.domain.post.entity.Post;
import com.example.bmw.domain.user.entity.User;
import com.example.bmw.domain.user.repository.UserRepository;
import com.example.bmw.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DesignService {
    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;
    private final DesignRepository designRepository;
    private final UserRepository userRepository;

    public void isExist(String designName){
        if (!"".equals(designName) && designName != null) {
            boolean isExistObject = amazonS3.doesObjectExist(bucket, designName);

            if (isExistObject) {
                amazonS3.deleteObject(bucket, designName);
            }
        }
    }

    @Transactional
    public Design save(MultipartFile multipartFile, String designName) throws IOException {
        isExist(designName);
        User user = userRepository.findByEmail(SecurityUtil.getLoginUserEmail()).orElseThrow(() -> new IllegalArgumentException("not found"));
        String fileName = UUID.randomUUID() + "_" + multipartFile.getOriginalFilename();

        ObjectMetadata objMeta = new ObjectMetadata();
        objMeta.setContentLength(multipartFile.getInputStream().available());

        amazonS3.putObject(bucket, fileName, multipartFile.getInputStream(), objMeta);

        Design design = new Design(fileName, amazonS3.getUrl(bucket, fileName).toString(), user);
        return designRepository.save(design);
    }

    @Transactional
    public void delete(String designName){
        amazonS3.deleteObject(bucket, designName);
        Design design = designRepository.findByDesignName(designName).orElseThrow(() -> new IllegalArgumentException("not found"));
        designRepository.delete(design);
    }

}
