package moon.hellomoon.repository;

import jakarta.transaction.Transactional;
import moon.hellomoon.domain.Member;
import moon.hellomoon.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class MemberServiceIntegrationTest {


    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    void 회원가입(){
        Member member = new Member();
        member.setName("JJung");

        Long saveId= memberService.join(member);

        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }
    @Test
    public void 중복_회원_예외(){
        Member member1 = new Member();
        member1.setName("JJung");

        Member member2 = new Member();
        member2.setName("JJung");

        memberService.join(member1);
        /**
         * memberService.join(member2) 이게 터지면 IllegalStateException이 예외가 터집니다.
         * */
        IllegalStateException e= assertThrows(IllegalStateException.class,() -> memberService.join(member2));

        Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }
}
