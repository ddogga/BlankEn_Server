package blank.english.service.member;

import blank.english.config.auth.dto.SessionUser;
import blank.english.config.auth.token.JwtTokenProvider;
import blank.english.dto.EmailAuthRequestDto;
import blank.english.dto.JoinResponseDTO;
import blank.english.dto.LoginForm;
import blank.english.entity.EmailAuthToken;
import blank.english.entity.Member;
import blank.english.entity.ResetPasswordToken;
import blank.english.exception.EmailAuthTokenNotFoundException;
import blank.english.exception.MemberNotFoundException;
import blank.english.repository.member.TokenRepository;
import blank.english.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final TokenRepository emailRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final EmailSanderService emailSanderService;
    private final JwtTokenProvider jwtTokenProvider;
    private final HttpSession httpSession;

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

    @Transactional
    @Override
    public String login(LoginForm form) {

        Member member = memberRepository.findOneByEmail(form.getEmail())
                .orElseThrow(MemberNotFoundException::new);

        if(!bCryptPasswordEncoder.matches(form.getPassword(),member.getPassword())){
            return "비밀번호가 일치하지 않습니다.";
        }

        httpSession.setAttribute("user", new SessionUser(member)); //세션에 사용자 정보 저장

        return jwtTokenProvider.createToken(member.getEmail(),member.getRole());
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
        EmailAuthToken findEmailAuthToken = emailRepository.findValidTokenByEmail(requestDto.getEmail(),requestDto.getUuid(), LocalDateTime.now())
                .orElseThrow(EmailAuthTokenNotFoundException::new);
        Member findMember = memberRepository.findOneByEmail(requestDto.getEmail()).orElseThrow(MemberNotFoundException::new);
        findEmailAuthToken.useToken();	// 토큰 만료
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

    public String sendPasswordChangeEmail(String email) {
        Member member = memberRepository.findOneByEmail(email)
                .orElseThrow(MemberNotFoundException::new);

        ResetPasswordToken resetPasswordToken = ResetPasswordToken.createRestPasswordToken(email);
        emailSanderService.sendPassLink(resetPasswordToken.getUuid(), email);
        return email;
    }

    public void updatePassword(String email, String uuid, String password) {
        ResetPasswordToken findPasswordResetToken = emailRepository.findPassTokenByEmail(email,uuid, LocalDateTime.now())
                .orElseThrow(EmailAuthTokenNotFoundException::new);
        findPasswordResetToken.useToken();	// 토큰 만료

        Member findMember = memberRepository.findOneByEmail(email)
                .map(entity -> entity.passwordUpdate(password,bCryptPasswordEncoder))
                .orElseThrow(MemberNotFoundException::new);

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
