package blank.english.entity.quiz;

import blank.english.entity.Category;
import blank.english.entity.Member;
import blank.english.entity.ShareState;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuizSet {

    @Id
    @GeneratedValue
    @Column(name = "quizset_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "quizSet", cascade = CascadeType.ALL)
    private List<Quiz> quizList = new ArrayList<>();

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_id")
    private Category category;


//    @OneToOne(fetch = FetchType.LAZY)
//    private QuizSetTitleImg quizSetTitleImg;

    @Column(name = "title_img")
    private String titleImg;

    private String title;

    private String contents;

    @Column(name = "write_date")
    private LocalDateTime writeDate;

    @Column(name = "good_nums")
    private int goodNums;

    private int views;
    private ShareState state;



    @Builder
    public QuizSet(Member member, List<Quiz> quizList, String titleImg, String title, String contents, LocalDateTime writeDate, ShareState state, Category category) {
        this.member = member;
        this.titleImg = titleImg;
        this.title = title;
        this.contents = contents;
        this.writeDate = writeDate;
        this.state = state;

        for (Quiz quiz : quizList) {
            addQuiz(quiz);
        }
        setCategory(category);
    }


    //연관관계 편의 메서드 QuizSet - Quiz
    public void addQuiz(Quiz quiz) {
        quizList.add(quiz);
        quiz.setQuizSet(this);
    }

    // Category - QuizSet
    public void setCategory(Category category) {
        this.category = category;
        category.getQuizSets().add(this);
    }

//    public void setCategory(Category category) {this.category = category;}
}
