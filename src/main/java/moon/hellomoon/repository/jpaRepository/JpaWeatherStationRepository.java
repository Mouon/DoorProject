package moon.hellomoon.repository.jpaRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import moon.hellomoon.domain.Member;
import moon.hellomoon.domain.WeatherStation;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public class JpaWeatherStationRepository {
    private final EntityManager em;

    public JpaWeatherStationRepository(EntityManager em) {
        this.em = em;
    }

    public Optional<Integer> findStationIdByName(String name) {
        TypedQuery<Integer> query = em.createQuery(
                "SELECT ws.stnId FROM WeatherStation ws WHERE ws.stnName = :name", Integer.class);
        query.setParameter("name", name);
        return query.getResultStream().findFirst();
    }

    public List<String> findAllStationNames(){
        return em.createQuery("SELECT ws.stnName FROM WeatherStation ws",String.class)
                .getResultList();
    }

}
