package ttc.ifish.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ttc.ifish.models.entities.CardRecharge;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

@Repository
@Transactional
public class CardRechargeRepositoryImpl {

  @Autowired
  EntityManager em;

  public List<CardRecharge>getCardRecharge(Calendar startHour,Calendar endHour){
    Query query = em.createNativeQuery("select e.* from fishcardrecharge e where e.RequestedTime >= ? and e.RequestedTime <= ?",CardRecharge.class);

    query.setParameter(1,startHour);
    query.setParameter(2,endHour);
    return query.getResultList();
  }
  public Long sumValueCardRecharge(Integer userId,Calendar startHour,Calendar endHour){
    Query query = em.createNativeQuery("select sum(e.ClaimedValue) from fishcardrecharge  e where e.UserID =? and e.RequestedTime >= ? and e.RequestedTime<=?");
    query.setParameter(1,userId);
    query.setParameter(2,startHour);
    query.setParameter(3,endHour);
    try{
      BigDecimal result = (BigDecimal)query.getSingleResult();
      Long totalAmount = result.longValue();
      return totalAmount;
    } catch (NullPointerException e){
      return 0L;
    }
  }

}
