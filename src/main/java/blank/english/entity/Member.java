package blank.english.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Column(name = "email_auth")
    private boolean emailAuth;



    @Builder
    public Member(String userName,String nickname,String password, String email,Role role,Boolean emailAuth) {
        this.userName = userName;
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.role = role;
        this.emailAuth = emailAuth;
    }


    public Member update(String userName, String picture) {
        this.userName = userName;
        this.picture = picture;

        return this;
    }

    public Member update(String userName, String nickname, String picture) {
        this.userName = userName;
        this.nickname = nickname;
        this.picture = picture;

        return this;
    }

    public void hashPassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }

    public boolean checkPassword(String targetPassword, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(targetPassword, this.password);
    }


    public void setProfile_img(String picture) {
        this.picture = picture;
    }

    public void emailVerifiedSuccess() {
        this.emailAuth = true;
    }
}
