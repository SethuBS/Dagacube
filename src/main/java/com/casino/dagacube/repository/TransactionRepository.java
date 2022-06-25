package com.casino.dagacube.repository;

import com.casino.dagacube.model.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TransactionRepository extends CrudRepository<Transaction,Integer> {
}
