package com.dongmyo.test.datasourcetest.model;

import com.dongmyo.test.datasourcetest.entity.MemberEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PRIVATE)
public class Member {
    private Long id;

    private String userCode;

    private String externalEmailAddress;


    private Member() {
        // nothing
    }

    public static Member of(MemberEntity entity) {
        Member member = new Member();
        member.setId(entity.getMemberId());
        member.setUserCode(entity.getUserCode());
        member.setExternalEmailAddress(entity.getExternalEmailAddress());

        return member;
    }

}
