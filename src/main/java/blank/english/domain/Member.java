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

    //userName이 진짜 아이디
    @Column(unique=true, name = "user_name")
    private String userName;

    private String password;

    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private String profile_img;


    @OneToMany(mappedBy = "member")
    private List<QuizSet> quizSets = new ArrayList<>();




    public Member(String userName,String password, String email,Role role) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public Member() {
    }


    public Member update(String userName, String profile_img) {
        this.userName = userName;
        this.profile_img = profile_img;

        return this;
    }


    public void setProfile_img(String profile_img) {
        this.profile_img = profile_img;
    }
}
