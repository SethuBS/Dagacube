package com.casino.dagacube.repository;


import com.casino.dagacube.model.Transaction;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

   public List<Transaction> findTopTen(Integer limit){

        return entityManager.createQuery("SELECT t FROM Transaction t ORDER BY t.transactionId desc",
                Transaction.class).setMaxResults(limit).getResultList();
    }
}
