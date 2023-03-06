package blank.english.service.quiz;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
@Slf4j
@Transactional
public class FileUploadService {

    @Value("${blank.english.upload.path}") // application.yml에서 설정한 경로 변수
    private String uploadPath;

    public String uploadFile(MultipartFile uploadFile) {
        //이미지 파일만 업로드 가능하도록 설정
        if (isImage(uploadFile)) {

            //실제 파일 이름
            String fileName = getFileName(uploadFile);

            //폴더 생성
            String folderPath = makeFolder();

            //저장할 파일 이름
            String saveName = makeSaveName(folderPath,fileName);
            Path savePath = Paths.get(saveName);

            try {
                uploadFile.transferTo(savePath);// 실제 이미지 저장
                return saveName;
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        log.warn("this file is empty or not image type");
        return "N"; //이미지 없음

    }



    private boolean isImage(MultipartFile uploadFile) {
        if (uploadFile == null) {return false;}
        return uploadFile.getContentType().startsWith("image");
    }

    private String getFileName(MultipartFile uploadFile) {
        String originalName = uploadFile.getOriginalFilename();
        String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);
        log.info("fileName" + fileName);
        return fileName;
    }

    private String makeFolder() {
        // 년/월/일 문자열
        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        // 년>월>일 구조의 디렉토리 만들기. 운영체제에 따라 경로 구분자가 다르므로 자바에서 제공하는 File.separator를 이용
        String folderPath = str.replace("/", File.separator);
        // make folder ----
        File uploadPathFolder = new File(uploadPath,folderPath);

        if(uploadPathFolder.exists() == false){ //해당 폴더가 존재하지 않을 경우 생성
            uploadPathFolder.mkdirs();
        }

        return folderPath;
    }

    private String makeSaveName(String folderPath, String fileName) {
        String uuid = UUID.randomUUID().toString();

        String saveName = uploadPath + File.separator + folderPath + File.separator + uuid + fileName;
        return saveName;
    }
}
