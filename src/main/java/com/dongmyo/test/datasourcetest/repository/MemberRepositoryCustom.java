package com.dongmyo.test.datasourcetest.repository;

import com.dongmyo.test.datasourcetest.entity.MemberEntity;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface MemberRepositoryCustom {
    Optional<MemberEntity> getMember(Long memberId);

}
