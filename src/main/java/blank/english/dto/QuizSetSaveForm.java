package blank.english.dto;

import blank.english.entity.Quiz;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class QuizSetSaveForm {

    private String titleImg;
    private String title;
    private String contents;
    private String writer;
    private String email;
    private List<Quiz> quizList;



}
