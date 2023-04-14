package blank.english.entity;

import blank.english.entity.quiz.Quiz;
import blank.english.entity.quiz.QuizSet;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
public class Category {

    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    private String description; //카테고리 설명

    /**
     * 하나의 카테고리에 해당되는 퀴즈세트가 여러개 있음.
     */
    @OneToMany(mappedBy = "category")
    private List<QuizSet> quizSets;




}
