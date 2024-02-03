package moon.hellomoon;

import moon.hellomoon.service.member.CustomOAuth2UserService;
import moon.hellomoon.service.member.MemberDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final MemberDetailService memberDetailService;
    private final PasswordEncoder passwordEncoder;

    @Value("${spring.security.oauth2.client.registration.github.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.github.client-secret}")
    private String clientSecret;

    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    @Autowired
    public SecurityConfig(MemberDetailService memberDetailService, PasswordEncoder passwordEncoder) {
        this.memberDetailService = memberDetailService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        // GitHub 클라이언트 설정
        ClientRegistration githubRegistration = ClientRegistration.withRegistrationId("github")
                .clientId(clientId)
                .clientSecret(clientSecret)
                .scope(new String[]{"read:user", "user:email"}) // 필요한 scope 명시
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE) // 인증 방식 설정
                .redirectUri("http://localhost:9000/github-login/callback") // 리디렉션 URI 설정
                .authorizationUri("https://github.com/login/oauth/authorize")
                .tokenUri("https://github.com/login/oauth/access_token")
                .userInfoUri("https://api.github.com/user")
                .userNameAttributeName("login")
                .clientName("GitHub")
                .build();

        return new InMemoryClientRegistrationRepository(githubRegistration);
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CSRF 보호 활성화
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/github-login/callback/**").permitAll()
                        .requestMatchers("/members","/members/member-detail","/project-list/user").authenticated()
                        .anyRequest().permitAll())
                .formLogin(form -> form
                        .loginPage("/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .failureHandler((request, response, exception) -> {
                            response.setContentType("text/html;charset=UTF-8");
                            response.getWriter().write("<script>alert('로그인 실패: " + exception.getMessage() + "');history.back();</script>");
                        })
                        .loginProcessingUrl("/perform_login")
                        .defaultSuccessUrl("/", true)
                        .permitAll())
                .oauth2Login(oauth2Login -> {
                    oauth2Login
                            .loginPage("/login")
                            .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService));
                })
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/"));

        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(memberDetailService)
                .passwordEncoder(passwordEncoder);
    }
}
