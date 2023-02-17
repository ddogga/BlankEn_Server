package blank.english.service;

import blank.english.entity.Member;
import blank.english.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    @Transactional
    @Override
    public String join(Member member) {
        if (validateDuplicateMember(member) == 0) {
            return "";
        }
        member.hashPassword(bCryptPasswordEncoder);
        memberRepository.save(member);
        return member.getNickname();
    }


    private int validateDuplicateMember(Member member) {

        Optional<Member> findMembers = memberRepository.findOneByEmail(member.getEmail());
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
