package ttc.ifish.repositories.fishlog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ttc.ifish.models.entities.fishlog.DiaryUser;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Calendar;
import java.util.List;

@Repository
@Transactional
public class DiaryUserRepositoryImpl {
  @Qualifier("fishEntityManager")
  @Autowired
  EntityManager em;


  public List<DiaryUser> findAllUserOnlineAtTheSameHour(String startDate, Calendar startHour, Calendar endHour){
    StringBuilder sql = new StringBuilder();
    sql.append("select e.* FROM fishroleonlinelog_");
    sql.append(startDate);
    sql.append(" e where e.LogTime >= ? and e.LogTime <= ? and IsOnline = true" +
      " group by e.UserID" +
      " order by e.UserID DESC");

    Query query = em.createNativeQuery(sql.toString());
    query.setParameter(1, startHour);
    query.setParameter(2, endHour);

    return query.getResultList();
  }

  public List<DiaryUser> findAllUserOnlineInDate(String startDate){
    StringBuilder sql = new StringBuilder();
    sql.append("select e.* FROM fishroleonlinelog_");
    sql.append(startDate);
    sql.append(" e where IsOnline = true" +
      " group by e.UserID" +
      " order by e.UserID DESC");

    Query query = em.createNativeQuery(sql.toString());


    return query.getResultList();
  }
}
