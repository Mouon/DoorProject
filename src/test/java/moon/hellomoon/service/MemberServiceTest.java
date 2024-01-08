package moon.hellomoon.service;

import moon.hellomoon.domain.Member;
import moon.hellomoon.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.*;
class MemberServiceTest {


    /**
     * 컨트롤 R 이전 실행 재실행
     * */
    /**
     * 테스트는 한국어로 하셔두 되요
     * */


    MemberService memberService;
    MemoryMemberRepository memberRepository;

    BCryptPasswordEncoder passwordEncoder;



    /**
     * 각 테스트 시작하기 전에 실행
     * MemoryMemberRepository 인스턴스 만들고 MemberService에 주입 DI
     * */
    @BeforeEach
    public void beforeEach(){
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository,passwordEncoder);
    }

    @AfterEach
    public void afterEach(){
        memberRepository.clearStore();
    }

    @Test
    void 회원가입() {
        //given
        Member member = new Member();
        member.setName("hello");
        //when
        Long saveId = memberService.join(member);

        //then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    public void 중복_회원_예외(){
       Member member1 = new Member();
       member1.setName("Moon");

       Member member2 = new Member();
       member2.setName("Moon");

        memberService.join(member1);
        /**
         * memberService.join(member2) 이게 터지면 IllegalStateException이 예외가 터집니다.
         * */
        IllegalStateException e= assertThrows(IllegalStateException.class,() -> memberService.join(member2));

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원 입니다.");

//       memberService.join(member1);
//       try{
//           memberService.join(member2);
//       }catch(IllegalStateException e){
//           assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
//       }



    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}