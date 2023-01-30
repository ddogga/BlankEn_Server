package blank.english.controller;


import lombok.Getter;

import javax.validation.constraints.NotEmpty;


@Getter
public class MemberForm {

    @NotEmpty(message = "회원 이름은 필수 입니다.")
    private String userName;

    private String email;

    private String password;


}
