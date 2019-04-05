package com.dongmyo.test.datasourcetest.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Members")
@Getter
@Setter
public class MemberEntity {
    @Id
    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "user_code")
    private String userCode;

    @Column(name = "external_email_address")
    private String externalEmailAddress;

}
