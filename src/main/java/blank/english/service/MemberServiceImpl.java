package blank.english.service;

import blank.english.dto.JoinResponseDTO;
import blank.english.entity.EmailAuthToken;
import blank.english.entity.Member;
import blank.english.repository.EmailRepository;
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
    private final EmailRepository emailRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    @Transactional
    @Override
    public JoinResponseDTO join(Member member) {
//        if (validateDuplicateMember(member) != 1) {
//            return "";//예외 생성하기
//        }
        member.hashPassword(bCryptPasswordEncoder);
        memberRepository.save(member);
        EmailAuthToken emailAuthToken = emailRepository.save(
                EmailAuthToken.createEmailAuthToken(member.getEmail()));

        return createJoinResponseDTO(member, emailAuthToken.getId());
    }

    private JoinResponseDTO createJoinResponseDTO(Member member, String tokenId){
        return JoinResponseDTO.builder()
                .nickName(member.getNickname())
                .email(member.getEmail())
                .tokenId(tokenId)
                .build();
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
