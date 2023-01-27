package blank.english.domain;

import javax.persistence.*;

@Entity
public class Quiz {

    @Id
    @GeneratedValue
    @Column(name = "quiz_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quizset_id")
    private QuizSet quizSet;

    private String first_entence;

    private String blank;

    private String last_sentence;

    private String expression;

    private int order;

}
