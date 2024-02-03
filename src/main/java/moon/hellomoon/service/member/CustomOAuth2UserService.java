package moon.hellomoon.service.member;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import moon.hellomoon.domain.Member;
import moon.hellomoon.repository.repositoryInterface.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final Logger logger = LoggerFactory.getLogger(CustomOAuth2UserService.class);

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        logger.info("OAuth2User loadUser() 호출");

        DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // OAuth2User에서 사용자 정보를 가져옵니다.
        Map<String, Object> attributes = oAuth2User.getAttributes();

        // GitHub 사용자 ID를 사용자 이름으로 사용
        String githubUserId = (String) attributes.get("login");  // GitHub 사용자 ID 필드는 "login"으로 가정
        attributes.put("name", githubUserId);  // "name" 필드에 사용자 ID를 넣어 반환

        // 사용자 정보를 DB에서 찾거나 새로 생성합니다.
        Member member = memberRepository.findByEmail(githubUserId)
                .orElseGet(() -> {
                    Member newMember = new Member();
                    newMember.setName(githubUserId);  // 여기도 GitHub 사용자 ID로 설정
                    newMember.setEmail(githubUserId + "@github.com");  // 이메일이 없으므로 GitHub ID를 이용해 가상의 이메일 주소 생성
                    return memberRepository.save(newMember);
                });

        // 사용자 정보를 Spring Security의 DefaultOAuth2User 형식으로 반환합니다.
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                attributes,
                "name");
    }
}
