package com.dongmyo.test.datasourcetest.repository;

import com.dongmyo.test.datasourcetest.annotation.TransactionalRepository;
import org.springframework.transaction.annotation.Transactional;

@TransactionalRepository
@Transactional(transactionManager = "transactionalTransactionManager")
public interface MemberCommandRepository extends CommonMemberRepository {

}
