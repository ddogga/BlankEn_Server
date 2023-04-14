package blank.english.dto;

import blank.english.entity.quiz.Quiz;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Builder
public class QuizSetResDto {

    private Long id;
    private String writer;
    private List<QuizResDto> quizzes;
    private String category;
    private String titleImg;
    private String title;
    private String contents;
    private int quizCnt;
    private int goodNums;
    private int views;

}
