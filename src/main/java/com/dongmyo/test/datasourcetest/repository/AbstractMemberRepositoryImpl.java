package com.dongmyo.test.datasourcetest.repository;

import com.dongmyo.test.datasourcetest.entity.MemberEntity;
import com.dongmyo.test.datasourcetest.entity.QMemberEntity;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.Optional;

public abstract class AbstractMemberRepositoryImpl extends QuerydslRepositorySupport
        implements MemberRepositoryCustom {
    public AbstractMemberRepositoryImpl() {
        super(MemberEntity.class);
    }


    @Override
    public Optional<MemberEntity> getMember(Long memberId) {
        QMemberEntity member = QMemberEntity.memberEntity;
        return Optional.ofNullable(from(member).where(member.memberId.eq(memberId)).fetchOne());
    }

}
