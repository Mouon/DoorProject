package moon.hellomoon.repository.jpaRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import moon.hellomoon.domain.Member;
import moon.hellomoon.domain.Project;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class JpaProjectRepository {
    private final EntityManager em;

    public JpaProjectRepository(EntityManager em) {
        this.em = em;
    }

    public Optional<Project> findById(long id) {
        Project project = em.find(Project.class,id);
        return Optional.ofNullable(project);
    }
    @Transactional
    public Project save(Project project) {
        em.persist(project);
        return project;
    }

    public List<Project> findByUser(Long userId) {
        Member member = em.find(Member.class, userId);
        TypedQuery<Project> query = em.createQuery("SELECT p FROM Project p WHERE p.member = :member", Project.class);
        query.setParameter("member", member);

        return query.getResultList();
    }


    public List<Project> findByTag(String tagName) {
        return em.createQuery("SELECT p FROM Project p JOIN p.tags t WHERE t = :tagName", Project.class)
                .setParameter("tagName", tagName)
                .getResultList();
    }

    public List<Object[]> CountUserTag(Long userId) {
        Member member = em.find(Member.class, userId);
        return em.createQuery("SELECT t, COUNT(p) FROM Project p JOIN p.tags t WHERE p.member = :member GROUP BY t", Object[].class)
                .setParameter("member", member)
                .getResultList();
    }

    public List<Object[]> CountTag(String tagName){
        return em.createQuery("SELECT t, COUNT(p) FROM Project p JOIN p.tags t WHERE t = :tagName GROUP BY t", Object[].class)
                .setParameter("tagName",tagName)
                .getResultList();
    }


    public List<Project> findAll() {
        return (List<Project>) em.createQuery("select p from Project p",Project.class)
                .getResultList();
    }

    public List<Object[]> countAllTags(){
        return em.createQuery("SELECT t, COUNT(p) FROM Project p JOIN p.tags t GROUP BY t", Object[].class)
                .getResultList();
    }

}
