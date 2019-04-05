package com.dongmyo.test.datasourcetest.service;

import com.dongmyo.test.datasourcetest.mapper.MemberMapper;
import com.dongmyo.test.datasourcetest.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class MemberServiceImpl implements MemberService {
    @Autowired(required = false)
    private MemberMapper memberMapper;


    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    @Override
    public Member getMember(Long memberId) {
        return memberMapper.getMember(memberId);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void updateMemberExternalEmailAddress(Long memberId, String externalEmailAddress) {
        Member member = memberMapper.getMember(memberId);
        if (Objects.isNull(member)) {
            return;
        }

        member.setExternalEmailAddress(externalEmailAddress);

        memberMapper.updateMemberExternalAddress(member);
    }

}
