package ttc.ifish.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ttc.ifish.models.entities.CardCashOut;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

@Repository
@Transactional
public class CardCashOutRepositoryImpl {
  @Autowired
  private EntityManager em;

  public List<CardCashOut>getCardCashOut(Calendar startHour,Calendar endHour){
    Query query = em.createNativeQuery("select e.* from fishcardcashout e where e.RequestedTime >= ?" +
      "and  e.RequestedTime <= ?",CardCashOut.class);
    query.setParameter(1,startHour);
    query.setParameter(2,endHour);
    return query.getResultList();
  }

  public Long getTotalAmount(Integer userId,Calendar startHour,Calendar endHour){
    Query query = em.createNativeQuery("select sum(e.Value) from fishcardcashout e" +
      " where e.RequestedTime >=? and " +
      "e.RequestedTime <=? and " +
      "e.UserID = ?");
    query.setParameter(1,startHour);
    query.setParameter(2,endHour);
    query.setParameter(3,userId);
    try {
      BigDecimal count =(BigDecimal) query.getSingleResult();
      Long total = count.longValue();
      return total;
    }
    catch (NullPointerException e){
      return 0l;
    }
  }
}
