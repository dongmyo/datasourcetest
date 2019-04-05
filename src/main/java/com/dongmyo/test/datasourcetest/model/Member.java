package com.dongmyo.test.datasourcetest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Member {
    @JsonProperty("id")
    private Long organizationMemberId;

    private String userCode;

    private String externalEmailAddress;

}
