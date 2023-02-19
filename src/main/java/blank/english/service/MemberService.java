package blank.english.service;

import blank.english.dto.JoinResponseDTO;
import blank.english.entity.Member;

import java.util.List;

public interface MemberService {

    public JoinResponseDTO join(Member member);

    public List<Member> findMembers();

    public Member findOne(Long memberId);

}
