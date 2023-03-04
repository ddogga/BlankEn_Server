package blank.english.service.quiz;


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
@Transactional
public class FileUploadService {

    @Value("${com.example.upload.path}") // application.yml에서 설정한 경로 변수
    private String uploadPath;

    public void uploadFile(MultipartFile uploadFile) {
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
            }catch (IOException e){
                e.printStackTrace();
            }
        }


    }



    private boolean isImage(MultipartFile uploadFile) {
        return uploadFile.getContentType().startsWith("image");
    }

    private String getFileName(MultipartFile uploadFile) {
        String originalName = uploadFile.getOriginalFilename();
        String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);
        return fileName;
    }

    private String makeFolder() {
        // 년/월/일 문자열
        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        // 년>월>일 구조의 디렉토리 만들기
        String folderPath = str.replace("/", File.separator);
        // make folder ----
        File uploadPathFolder = new File(uploadPath,folderPath);

        if(uploadPathFolder.exists() == false){
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
