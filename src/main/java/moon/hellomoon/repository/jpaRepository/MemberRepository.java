package moon.hellomoon.repository.jpaRepository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import moon.hellomoon.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MemberRepository implements moon.hellomoon.repository.repositoryInterface.MemberRepository {

    private final EntityManager em;

    public MemberRepository(EntityManager em){
        this.em=em;
    }
    @Override
    @Transactional
    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    @Override
    @Transactional
    public void delete(Member member) {
        em.remove(em.merge(member));
    }


    @Override
    public Optional<Member> findById(long id) {
        Member member = em.find(Member.class,id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name = :name",Member.class)
                .setParameter("name",name)
                .getResultList();

        return result.stream().findAny();
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        List<Member> result = em.createQuery("select m from Member m where m.email = :email", Member.class)
                .setParameter("email", email)
                .getResultList();

        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return (List<Member>) em.createQuery("select m from Member m",Member.class)
                .getResultList();
    }
}
