package moon.hellomoon.repository.jpaRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import moon.hellomoon.domain.Member;
import moon.hellomoon.domain.Project;
import moon.hellomoon.domain.Question;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;

@Repository
public class QuestionRepository {
    private final EntityManager em;


    public QuestionRepository(EntityManager em) {
        this.em = em;
    }
    public Optional<Question> findById(long id) {
        Question question = em.find(Question.class,id);
        return Optional.ofNullable(question);
    }

    @Transactional
    public Question save(Question question) {
        em.persist(question);
        return question;
    }

//    public QuestionDetailResponse detail(Long questionId) {
//        Question question = em.find(Question.class, questionId);
//        if (question == null) {
//            return null; // 또는 적절한 예외 처리
//        }
//
//        QuestionDetailResponse response = new QuestionDetailResponse();
//        response.setId(question.getId());
//        response.setTitle(question.getTitle());
//        response.setContent(question.getContent());
//        response.setLikeCount(question.getLikeCount());
//        response.setMemberName(question.getMember().getName()); // Member가 null이 아니라고 가정
//
//        return response;
//    }

    public List<Question> findByUser(Long userId) {
        Member member = em.find(Member.class, userId);
        TypedQuery<Question> query = em.createQuery("SELECT q FROM Question q WHERE q.member = :member", Question.class);
        query.setParameter("member", member);

        return query.getResultList();
    }


    public List<Question> findTop3LikedQuestionsOfTheMonth() {
        LocalDateTime startOfMonth = LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth());
        TypedQuery<Question> query = em.createQuery(
                "SELECT q FROM Question q WHERE q.creationDate >= :startOfMonth ORDER BY q.likeCount DESC",
                Question.class);
        query.setParameter("startOfMonth", startOfMonth);
        query.setMaxResults(3);

        return query.getResultList();
    }



    public List<Question> findAll() {
        return (List<Question>) em.createQuery("select q from Question q",Question.class)
                .getResultList();
    }

}
