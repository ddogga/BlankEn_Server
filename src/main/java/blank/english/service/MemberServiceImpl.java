package blank.english.service;

import blank.english.domain.Member;
import blank.english.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public String join(Member member) {
        if (validateDuplicateMember(member) == 0) {
            return "";
        }
        memberRepository.save(member);
        return member.getUserName();
    }

    @Override
    public int validateDuplicateMember(Member member) {

        List<Member> findMembers = memberRepository.findByName(member.getUserName());
        if (!findMembers.isEmpty()) {
            log.info("중복된 회원");
            return 0;
        }
        return 1;
    }

    @Override
    public List<Member> findMembers() {
        return null;
    }

    @Override
    public Member findOne(Long memberId) {
        return null;
    }
}
