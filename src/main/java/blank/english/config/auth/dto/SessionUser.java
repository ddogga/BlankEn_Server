package blank.english.config.auth.dto;

import blank.english.domain.Member;

public class SessionUser {

    private String name;
    private String email;
    private String profile_img;

    public SessionUser(Member member){
        this.name = member.getUserName();
        this.email = member.getEmail();
        this.profile_img = member.getProfile_img();
    }
}
