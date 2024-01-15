package moon.hellomoon;

import jakarta.persistence.EntityManager;
import moon.hellomoon.repository.jpaRepository.JpaBoardRepository;
import moon.hellomoon.repository.jpaRepository.JpaDiaryRepository;
import moon.hellomoon.repository.jpaRepository.JpaMemberRepository;
import moon.hellomoon.repository.repositoryInterface.BoardRepository;
import moon.hellomoon.repository.repositoryInterface.DiaryRepository;
import moon.hellomoon.repository.repositoryInterface.MemberRepository;
import moon.hellomoon.service.member.MemberService;
import moon.hellomoon.service.board.BoardWriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SpringConfig {

    private EntityManager em;
    @Autowired
    public SpringConfig(EntityManager em){
        this.em=em;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

//    /**
//     * 컴포넌트 어노테이션 써두 되지만 이렇게 명시적으로 AOP쓰는것 알려줘두 좋음
//     * */
//    @Bean
//    public TimeTraceAop timeTraceAop(){
//        return new TimeTraceAop();
//    }
}
