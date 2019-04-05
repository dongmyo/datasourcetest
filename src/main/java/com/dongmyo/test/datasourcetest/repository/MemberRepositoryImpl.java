package com.dongmyo.test.datasourcetest.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Transactional(transactionManager = "readOnlyTransactionManager")
public class MemberRepositoryImpl extends AbstractMemberRepositoryImpl {
    @Autowired
    @Qualifier("entityManagerFactory")
    @Override
    public void setEntityManager(EntityManager entityManager) {
        super.setEntityManager(entityManager);
    }

}
