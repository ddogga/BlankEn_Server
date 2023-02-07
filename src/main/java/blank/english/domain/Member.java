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

    //사용자의 실제 이름
    @Column(name = "user_name")
    private String userName;

    //사용자 별명
    private String nickname;

    private String password;

    @Column(unique=true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private String picture;


    @OneToMany(mappedBy = "member")
    private List<QuizSet> quizSets = new ArrayList<>();




    public Member(String userName,String nickname,String password, String email,Role role) {
        this.userName = userName;
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public Member() {
    }


    public Member update(String userName, String nickname, String picture) {
        this.userName = userName;
        this.nickname = nickname;
        this.picture = picture;

        return this;
    }


    public void setProfile_img(String picture) {
        this.picture = picture;
    }
}
