package com.dongmyo.test.datasourcetest.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Transactional(transactionManager = "transactionalTransactionManager")
public class MemberCommandRepositoryImpl extends AbstractMemberRepositoryImpl {
    @Autowired
    @Qualifier("transactionalEntityManagerFactory")
    @Override
    public void setEntityManager(EntityManager entityManager) {
        super.setEntityManager(entityManager);
    }

}
