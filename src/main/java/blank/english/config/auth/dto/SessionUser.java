package blank.english.config.auth.dto;

import blank.english.domain.Member;
import lombok.Getter;

@Getter
public class SessionUser {

    private String name;
    private String nickname;
    private String email;
    private String picture;

    public SessionUser(Member member){
        this.name = member.getUserName();
        this.nickname = member.getNickname();
        this.email = member.getEmail();
        this.picture = member.getPicture();
    }
}
