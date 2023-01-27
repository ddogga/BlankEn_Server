package blank.english.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String password;

    private String email;

    @Column(name = "join_date")
    private LocalDateTime joinDate;

    @OneToMany(mappedBy = "member")
    private List<QuizSet> quizSets = new ArrayList<>();

    public Member(Long id, String password, String email, LocalDateTime joinDate) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.joinDate = joinDate;
    }

    public Member() {
    }
}
