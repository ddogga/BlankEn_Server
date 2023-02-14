package blank.english.config.auth.service;


import blank.english.config.auth.dto.OAuthAttributes;
import blank.english.config.auth.dto.SessionUser;
import blank.english.domain.Member;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;


import javax.servlet.http.HttpSession;
import java.util.Collections;

import blank.english.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

/*
*
* 커스텀한 OAuth2User를 반환하기 위해 DefaultOAuth2UserService를 상속하여 구현한 클래스
*
*
* */


@Service
@Transactional
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService{

    private final MemberRepository memberRepository;
    private final HttpSession httpSession;

    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("service시작");

        OAuth2UserService<OAuth2UserRequest, OAuth2User> service = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = service.loadUser(userRequest); // Oath2 정보를 가져옴

        String registrationId = userRequest.getClientRegistration().getRegistrationId(); // 소셜 정보 가져옴
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        //OAuthAttributes = API로 로그인한 사용자(소셜로그인 사용자) 데이터를 담는 도메인
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        Member member = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionUser(member)); //세션에 사용자 정보 저장

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(member.getRole().getKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    // 회원가입 처리.
    private Member saveOrUpdate(OAuthAttributes attributes){ //해당하는 User의 데이터가 존재한다면 Member 객체로 만들어서 리턴

        Member member =  memberRepository.findOneByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(),attributes.getNickname(),attributes.getPicture()))//회원 정보수정
                .orElse(attributes.toEntity());//새 회원 생성

        return memberRepository.save(member);

    }


}
