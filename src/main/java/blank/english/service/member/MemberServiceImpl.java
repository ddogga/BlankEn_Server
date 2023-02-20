package blank.english.service.member;

import blank.english.dto.EmailAuthRequestDto;
import blank.english.dto.JoinResponseDTO;
import blank.english.entity.EmailAuthToken;
import blank.english.entity.Member;
import blank.english.exception.EmailAuthTokenNotFoundException;
import blank.english.exception.MemberNotFoundException;
import blank.english.repository.member.EmailRepository;
import blank.english.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final EmailRepository emailRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final EmailSanderService emailSanderService;

    @Transactional
    @Override
    public JoinResponseDTO join(Member member) {
//        if (validateDuplicateMember(member) != 1) {
//            return "";//예외 생성하기
//        }
        member.hashPassword(bCryptPasswordEncoder);
        memberRepository.save(member);

        EmailAuthToken token = EmailAuthToken.createEmailAuthToken(member.getEmail());
        emailRepository.save(token);

        emailSanderService.sender(token.getEmail(),token.getUuid());

        return createJoinResponseDTO(member, token.getUuid());
    }

    private JoinResponseDTO createJoinResponseDTO(Member member, String tokenId){
        return JoinResponseDTO.builder()
                .nickName(member.getNickname())
                .email(member.getEmail())
                .tokenId(tokenId)
                .build();
    }

    /**
    * 이메일 인증 로직
    *
    */

    @Transactional
    public void confirmEmail(EmailAuthRequestDto requestDto ) {
        EmailAuthToken fineEmailAuthToken = emailRepository.findValidTokenByEmail(requestDto.getEmail(),requestDto.getUuid(), LocalDateTime.now())
                .orElseThrow(EmailAuthTokenNotFoundException::new);
        Member findMember = memberRepository.findOneByEmail(requestDto.getEmail()).orElseThrow(MemberNotFoundException::new);
        fineEmailAuthToken.useToken();	// 토큰 만료
        findMember.emailVerifiedSuccess();	// 유저의 이메일 인증 값 변경
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
