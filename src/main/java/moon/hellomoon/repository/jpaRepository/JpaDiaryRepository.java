package moon.hellomoon.repository.jpaRepository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import moon.hellomoon.domain.Diary;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import moon.hellomoon.repository.repositoryInterface.DiaryRepository;
import org.springframework.stereotype.Repository;

@Repository
public class JpaDiaryRepository implements DiaryRepository {

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

    /**
     * 쿼리문으로 삭제하기
     * */
    @Override
    @Transactional
    public void deleteDiaryById(Long diaryId) {
        em.createQuery("DELETE FROM Diary d WHERE d.id = :diaryId")
                .setParameter("diaryId", diaryId)
                .executeUpdate();
    }

    /** 일반적으로 수정 작업을 수행하고 나면 변경 사항을 저장하기 위해 save 또는 flush 메서드를 호출해야함 */
    @Override
    @Transactional
    public void modifyDiaryById(Long diaryId, String newEventDescription) {
        em.createQuery("UPDATE Diary d SET d.eventDescription = :newEventDescription WHERE d.id = :diaryId")
                .setParameter("diaryId", diaryId)
                .setParameter("newEventDescription", newEventDescription)
                .executeUpdate();
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
