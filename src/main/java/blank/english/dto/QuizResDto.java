package blank.english.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
public class QuizResDto {

    private String quizImg;
    private String firstSentence;
    private String blank;
    private String lastSentence;
    private String expression;
    private int num;
}
