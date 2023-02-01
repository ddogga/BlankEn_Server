package blank.english.config.auth.dto;

import blank.english.domain.Member;

public class SessionUser {

    private String name;
    private String email;
    private String picture;

    public SessionUser(Member member){
        this.name = member.getUserName();
        this.email = member.getEmail();
        this.picture = member.getPicture();
    }
}
