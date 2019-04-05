package com.dongmyo.test.datasourcetest.repository;

import org.springframework.transaction.annotation.Transactional;

@Transactional(transactionManager = "readOnlyTransactionManager")
public interface MemberRepository extends CommonMemberRepository {
}
