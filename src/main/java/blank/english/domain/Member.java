package blank.english.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(unique=true, name = "user_name")
    private String userName;

    private String password;

    private String email;


    @OneToMany(mappedBy = "member")
    private List<QuizSet> quizSets = new ArrayList<>();

    public Member(String userName,String password, String email) {
        this.userName = userName;
        this.password = password;
        this.email = email;
    }

    public Member() {
    }
}
