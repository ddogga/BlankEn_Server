package blank.english.controller;


import lombok.Getter;

import javax.validation.constraints.NotEmpty;


@Getter
public class MemberForm {

    private String userName;

    private String nickname;

    private String email;

    private String password;


}
