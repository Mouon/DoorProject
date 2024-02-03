package moon.hellomoon.repository.jpaRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import moon.hellomoon.domain.Answer;
import moon.hellomoon.domain.Member;
import moon.hellomoon.domain.Project;
import moon.hellomoon.domain.Question;

import java.util.List;
import java.util.Optional;

public class AnswerRepository {
    private final EntityManager em;

    public AnswerRepository(EntityManager em) {
        this.em = em;
    }

    public Optional<Answer> findById(long id) {
        Answer answer = em.find(Answer.class,id);
        return Optional.ofNullable(answer);
    }

    @Transactional
    public Answer save(Answer answer) {
        em.persist(answer);
        return answer;
    }

    public List<Answer> findByUser(Long userId) {
        Member member = em.find(Member.class, userId);
        TypedQuery<Answer> query = em.createQuery("SELECT p FROM Project p WHERE p.member = :member", Answer.class);
        query.setParameter("member", member);

        return query.getResultList();
    }




}
