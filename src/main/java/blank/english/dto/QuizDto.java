package blank.english.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class QuizDto {

    private MultipartFile quizImg;
    private String firstSentence;
    private String blank;
    private String lastSentence;
    private String expression;
    private int num;
}
