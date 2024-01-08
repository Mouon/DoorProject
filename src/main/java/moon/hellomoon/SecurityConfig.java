package moon.hellomoon;

import moon.hellomoon.service.MemberDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * 버전이 바뀌어서 이 셋팅 기억하면 좋을듯
 * */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final MemberDetailService memberDetailService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfig(MemberDetailService memberDetailService, PasswordEncoder passwordEncoder) {
        this.memberDetailService = memberDetailService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authz -> authz
                        /** 서비스 권한 제한 */
                        .requestMatchers("/members", "/aichat","board/write").authenticated()
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
