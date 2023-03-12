package blank.english.dto;

import blank.english.entity.quiz.Quiz;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class QuizSetSaveForm {



    private MultipartFile titleImg;
    private String title;
    private String contents;
    private String email;
    private List<QuizDto> quizList;
    private String category;
    private boolean shared;



}
