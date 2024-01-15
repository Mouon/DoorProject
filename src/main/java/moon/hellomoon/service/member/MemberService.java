package moon.hellomoon.service.member;

import moon.hellomoon.domain.Member;
import moon.hellomoon.repository.repositoryInterface.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public MemberService(MemberRepository memberRepository, BCryptPasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;

    }

    /**
     * 회원 가입
     */
    public Long join(Member member) {
        //커맨드 옵션 브이
        //NULL 같은거 안전하게 하려면 Optional로 감싸기!
        String encodedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encodedPassword);
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    public void deleteMember(Long memberId) {
        Optional<Member> member = memberRepository.findById(memberId);
        member.ifPresent(memberRepository::delete);
    }


    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    // 이제 member 변수에 실제 Member 엔티티가 저장됩니다.
    // 이 엔티티를 수정하거나 사용할 수 있습니다.
    public boolean changePassword(String email, String oldPassword, String newPassword) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            if (passwordEncoder.matches(oldPassword, member.getPassword())) {
                String encodedNewPassword = passwordEncoder.encode(newPassword);
                member.setPassword(encodedNewPassword);
                memberRepository.save(member);
                return true;
            }
        }
        return false;
    }

    public boolean checkPassword(long id, String password) {
        Optional<Member> optionalMember = memberRepository.findById(id);
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            return passwordEncoder.matches(password, member.getPassword());
            }
        return false;
    }

    public Member getMemberByUsername(String username) {
        // 사용자 이름(username)을 기반으로 해당 사용자를 조회합니다.
        Optional<Member> member = memberRepository.findByName(username);

        // 사용자를 찾았을 경우에만 반환합니다.
        if (member.isPresent()) {
            return member.get();
        } else {
            // 사용자를 찾지 못한 경우 예외를 던지거나 다른 처리를 수행할 수 있습니다.
            // 이 예제에서는 예외를 던지도록 하겠습니다.
            throw new RuntimeException("User not found with username: " + username);
        }
    }

    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }

}
