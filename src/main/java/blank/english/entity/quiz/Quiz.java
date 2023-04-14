package blank.english.entity.quiz;

import blank.english.dto.QuizDto;
import blank.english.dto.QuizResDto;
import blank.english.entity.quiz.QuizSet;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Quiz {

    @Id
    @GeneratedValue
    @Column(name = "quiz_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quizset_id")
    private QuizSet quizSet;

    private String quizImgName;

    private String firstSentence;

    private String blank;

    private String lastSentence;

    private String expression;

    private int num;

    @Builder
    public Quiz(String quizImgName, String firstSentence, String blank, String lastSentence, String expression, int num) {
        this.quizImgName = quizImgName;
        this.firstSentence = firstSentence;
        this.blank = blank;
        this.lastSentence = lastSentence;
        this.expression = expression;
        this.num = num;
    }

    public void setQuizSet(QuizSet quizSet){
        this.quizSet = quizSet;
    }


    public QuizResDto toDto() {
        System.out.println("id = " + id);
        return  QuizResDto.builder()
                .quizImg(this.quizImgName)
                .firstSentence(this.firstSentence)
                .blank(this.blank)
                .lastSentence(this.lastSentence)
                .expression(this.expression)
                .num(this.num)
                .build();
    }
}
