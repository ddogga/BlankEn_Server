package blank.english.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class QuizSet {

    @Id
    @GeneratedValue
    @Column(name = "quizset_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String title;

    @Column(name = "write_date")
    private LocalDateTime writeDate;

    @Column(name = "good_nums")
    private int goodNums;

    private int views;

    private ShareState state;



}
