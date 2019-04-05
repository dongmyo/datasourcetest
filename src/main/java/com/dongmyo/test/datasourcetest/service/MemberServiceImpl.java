package com.dongmyo.test.datasourcetest.service;

import com.dongmyo.test.datasourcetest.entity.MemberEntity;
import com.dongmyo.test.datasourcetest.model.Member;
import com.dongmyo.test.datasourcetest.repository.MemberCommandRepository;
import com.dongmyo.test.datasourcetest.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberCommandRepository memberCommandRepository;


    @Override
    public Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                               .map(Member::of)
                               .orElse(null);
    }

    @Transactional(transactionManager = "transactionalTransactionManager", propagation = Propagation.REQUIRES_NEW)
    @Override
    public void updateMemberExternalEmailAddress(Long memberId, String externalEmailAddress) {
        Optional<MemberEntity> optionalMember = memberCommandRepository.getMember(memberId);
        if (!optionalMember.isPresent()) {
            return;
        }

        MemberEntity memberEntity = optionalMember.get();
        memberEntity.setExternalEmailAddress(externalEmailAddress);

        memberCommandRepository.saveAndFlush(memberEntity);
    }

}
