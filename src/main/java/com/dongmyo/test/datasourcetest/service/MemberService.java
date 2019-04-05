package com.dongmyo.test.datasourcetest.service;

import com.dongmyo.test.datasourcetest.model.Member;

public interface MemberService {
    Member getMember(Long memberId);

    void updateMemberExternalEmailAddress(Long memberId, String externalEmailAddress);

}
