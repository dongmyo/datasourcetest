package com.dongmyo.test.datasourcetest.controller;

import com.dongmyo.test.datasourcetest.model.Member;
import com.dongmyo.test.datasourcetest.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
public class MemberController {
    @Autowired
    private MemberService memberService;


    @GetMapping("/{memberId}")
    public Member getMember(@PathVariable Long memberId) {
        return memberService.getMember(memberId);
    }

    @GetMapping("/{memberId}/update")
    public String updateMemberExternalEmailAddress(@PathVariable Long memberId) {
        memberService.updateMemberExternalEmailAddress(memberId, "dongmyo1@nhn.com");
        return "done";
    }

}
