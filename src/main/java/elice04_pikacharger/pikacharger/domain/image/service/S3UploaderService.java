package elice04_pikacharger.pikacharger.domain.image.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class S3UploaderService {
    private final AmazonS3 amazonS3;
    private final String bucket;

    public S3UploaderService(AmazonS3 amazonS3, @Value("${cloud.aws.s3.bucket}") String bucket) {
        this.amazonS3 = amazonS3;
        this.bucket = bucket;
    }

    public String uploadSingleFile(MultipartFile multipartFile, String dirName) throws IOException {
        // 파일 이름에서 공백을 제거한 새로운 파일 이름 생성
        String originalFileName = multipartFile.getOriginalFilename();

        // UUID를 파일명에 추가
        String uuid = UUID.randomUUID().toString();
        String uniqueFileName = uuid + "_" + originalFileName.replaceAll("\\s", "_");

        String fileName = dirName + "/" + uniqueFileName;
        log.info("fileName: " + fileName);
        File uploadFile = convert(multipartFile);

        String uploadImageUrl = putS3(uploadFile, fileName);
        removeNewFile(uploadFile);
        return uploadImageUrl;
    }

    public List<String> uploadMultipleFiles(List<MultipartFile> multipartFiles, String dirName) throws IOException {
        if (multipartFiles.size() == 1){
            return Collections.singletonList(uploadSingleFile(multipartFiles.get(0), dirName));
        }else {
            List<String> uploadedImageUrls = new ArrayList<>();
            for (MultipartFile file : multipartFiles) {
                String imageUrl = uploadSingleFile(file, dirName);
                uploadedImageUrls.add(imageUrl);
            }
            return uploadedImageUrls;
        }
    }

    private File convert(MultipartFile file) throws IOException {
        String originalFileName = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        String uniqueFileName = uuid + "_" + originalFileName.replaceAll("\\s", "_");

        File convertFile = new File(uniqueFileName);
        if (convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            } catch (IOException e) {
                log.error("파일 변환 중 오류 발생: {}", e.getMessage());
                throw e;
            }
            return convertFile;
        }
        throw new IllegalArgumentException(String.format("파일 변환에 실패했습니다. %s", originalFileName));
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3.putObject(new PutObjectRequest(bucket, fileName, uploadFile)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3.getUrl(bucket, fileName).toString();
    }

    // TODO 파일이 삭제 되었는지, 삭제되지 못했는지만 로그로 남기고 메서드는 삭제.
    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("파일이 삭제되었습니다.");
        } else {
            log.info("파일이 삭제되지 못했습니다.");
        }
    }

    public void deleteFile(String fileName) {
        try {
            // URL 디코딩을 통해 원래의 파일 이름을 가져옴
            String decodedFileName = URLDecoder.decode(fileName, "UTF-8");
            log.info("Deleting file from S3: " + decodedFileName);
            amazonS3.deleteObject(this.bucket, decodedFileName.substring(59));
        } catch (UnsupportedEncodingException e) {
            log.error("Error while decoding the file name: {}", e.getMessage());
        }
    }

    public List<String> updateFiles(List<MultipartFile> newFiles, List<String> oldFileNames, String dirName) throws IOException {
        List<String> updatedImageUrls = new ArrayList<>();
        for (int i = 0; i < newFiles.size(); i++) {
            String oldFileName = oldFileNames.get(i);
            // 기존 파일 삭제
            log.info("S3 oldFileName: " + oldFileName);
            deleteFile(oldFileName);
            // 새 파일 업로드
            MultipartFile newFile = newFiles.get(i);
            String updatedImageUrl = uploadSingleFile(newFile, dirName);
            updatedImageUrls.add(updatedImageUrl);
        }
        return updatedImageUrls;
    }
}
