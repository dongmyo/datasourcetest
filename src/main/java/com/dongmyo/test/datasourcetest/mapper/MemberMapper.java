package com.dongmyo.test.datasourcetest.mapper;

import com.dongmyo.test.datasourcetest.model.Member;

public interface MemberMapper {
    Member getMember(Long memberId);

    int updateMemberExternalAddress(Member member);

}
