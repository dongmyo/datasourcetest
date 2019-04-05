package com.dongmyo.test.datasourcetest.repository;

import com.dongmyo.test.datasourcetest.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Collection;
import java.util.List;

// 기존에 있던 repository
@NoRepositoryBean
public interface CommonMemberRepository extends MemberRepositoryCustom, JpaRepository<MemberEntity, Long> {
    List<MemberEntity> findByMemberIdIn(Collection<Long> ids);

}
