package blank.english.service.member;

import blank.english.dto.EmailAuthRequestDto;
import blank.english.dto.JoinResponseDTO;
import blank.english.dto.LoginForm;
import blank.english.entity.Member;

import java.util.List;

public interface MemberService {

    JoinResponseDTO join(Member member);

    String login(LoginForm form);

    List<Member> findMembers();

    Member findOne(Long memberId);

    void confirmEmail(EmailAuthRequestDto requestDto);

    String sendPasswordChangeEmail(String email);

    void updatePassword(String email, String uuid, String password);

}
