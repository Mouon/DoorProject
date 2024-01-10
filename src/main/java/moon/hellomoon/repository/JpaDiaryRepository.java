package moon.hellomoon.repository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import moon.hellomoon.domain.Board;
import moon.hellomoon.domain.Diary;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import jakarta.persistence.EntityManager;
import moon.hellomoon.domain.Member;
import org.springframework.stereotype.Repository;

@Repository
public class JpaDiaryRepository implements DiaryRepository{

    private final EntityManager em;

    public JpaDiaryRepository(EntityManager em){
        this.em=em;
    }

    @Override
    @Transactional
    public Diary save(Diary diary) {
        em.persist(diary);
        return diary;
    }

    @Override
    public Optional<Diary> findById(long id) {
        Diary diary = em.find(Diary.class,id);
        return Optional.ofNullable(diary);     }

    @Override
    public List<Diary> findByMemberId(long memberId, LocalDate eventDate) {
        return em.createQuery("select e from Diary e where e.member.id = :memberId", Diary.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }


    @Override
    public List<Diary> findByDate(LocalDate eventDate) {
        return em.createQuery("select e from Diary e where e.eventDate = :event_date", Diary.class)
                .setParameter("event_date", eventDate)
                .getResultList();
    }

    @Override
    public List<Diary> findByMemberIdAndDate(long memberId, LocalDate eventDate) {
        return em.createQuery("select e from Diary e where e.member.id = :memberId and e.eventDate = :eventDate", Diary.class)
                .setParameter("memberId", memberId)
                .setParameter("eventDate", eventDate)
                .getResultList();
    }


    @Override
    public List<Diary> findAll() {
        return (List<Diary>) em.createQuery("select e from events e",Diary.class)
                .getResultList();    }
}
