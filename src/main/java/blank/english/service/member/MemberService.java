package blank.english.service.member;

import blank.english.dto.EmailAuthRequestDto;
import blank.english.dto.JoinResponseDTO;
import blank.english.entity.Member;

import java.util.List;

public interface MemberService {

    JoinResponseDTO join(Member member);

    List<Member> findMembers();

    Member findOne(Long memberId);

    void confirmEmail(EmailAuthRequestDto requestDto);

}
