package blank.english.entity;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Entity
@Getter
public class Quiz {

    @Id
    @GeneratedValue
    @Column(name = "quiz_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quizset_id")
    private QuizSet quizSet;

    private String originalQuizImgName;

    private String firstSentence;

    private String blank;

    private String lastSentence;

    private String expression;

    private int num;


    public void setQuizSet(QuizSet quizSet){
        this.quizSet = quizSet;
    }

}
