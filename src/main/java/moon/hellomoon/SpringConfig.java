package moon.hellomoon;

import jakarta.persistence.EntityManager;
import moon.hellomoon.aop.TimeTraceAop;
import moon.hellomoon.repository.BoardRepository;
import moon.hellomoon.repository.JpaMemberRepository;
import moon.hellomoon.repository.MemberRepository;
import moon.hellomoon.repository.jpaBoardRepository;
import moon.hellomoon.service.MemberService;
import moon.hellomoon.service.board.BoardWriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
    public MemberService memberService(){
        return new MemberService(memberRepository(),passwordEncoder());
    }

    @Bean
    public BoardWriteService boardWriteService(){return new BoardWriteService(boardRepository());}
    @Bean
    public MemberRepository memberRepository(){
        return new JpaMemberRepository(em);
    }

    @Bean
    public BoardRepository boardRepository(){return new jpaBoardRepository(em);
    }

//    /**
//     * 컴포넌트 어노테이션 써두 되지만 이렇게 명시적으로 AOP쓰는것 알려줘두 좋음
//     * */
//    @Bean
//    public TimeTraceAop timeTraceAop(){
//        return new TimeTraceAop();
//    }
}
