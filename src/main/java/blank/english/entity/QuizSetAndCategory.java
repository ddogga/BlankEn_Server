package blank.english.entity;

import blank.english.entity.quiz.QuizSet;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class QuizSetAndCategory {

    @Id
    @GeneratedValue
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quizset_id")
    private QuizSet quizSet;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;


    private String category_name;


}
