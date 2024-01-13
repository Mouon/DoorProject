package moon.hellomoon.repository.jpaRepository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import moon.hellomoon.domain.Board;
import moon.hellomoon.domain.Bookmarks;
import moon.hellomoon.domain.Diary;
import moon.hellomoon.repository.repositoryInterface.BookmarkRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaBookmarksRepository implements BookmarkRepository {

    private final EntityManager em;

    public JpaBookmarksRepository(EntityManager em){
        this.em=em;
    }


    @Override
    @Transactional
    public Bookmarks save(Bookmarks bookmarks) {
        em.persist(bookmarks);
        return bookmarks;    }

    @Override
    @Transactional
    public Optional<Bookmarks> findById(long id) {
        Bookmarks bookmarks= em.find(Bookmarks.class,id);
        return Optional.ofNullable(bookmarks);    }

    @Override
    public Optional<Bookmarks> findByName(String name) {
        List<Bookmarks> result = em.createQuery("select b from Bookmarks b where b.name = :name",Bookmarks.class)
                .setParameter("name",name)
                .getResultList();

        return result.stream().findAny();    }

    @Override
    public List<Bookmarks> findByMemberId(long memberId) {
        return em.createQuery("select b from Bookmarks b where b.member.id = :memberId", Bookmarks.class)
                .setParameter("memberId", memberId)
                .getResultList();    }

    @Override
    public List<Bookmarks> findAll() {
        return (List<Bookmarks>) em.createQuery("select b from Bookmarks b",Bookmarks.class)
                .getResultList();    }
}
