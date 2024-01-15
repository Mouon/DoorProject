package moon.hellomoon.repository.jpaRepository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import moon.hellomoon.domain.Member;
import moon.hellomoon.domain.Project;
import org.springframework.stereotype.Repository;

import java.util.List;
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

    public List<Project> findAll() {
        return (List<Project>) em.createQuery("select p from Project p",Project.class)
                .getResultList();
    }
}
