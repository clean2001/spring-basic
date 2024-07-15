package com.beyond.basic.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // Request에서 NoArgs 꼭 필요하구나..
@AllArgsConstructor
public class TestRequest {
    private String requestStr;
}
