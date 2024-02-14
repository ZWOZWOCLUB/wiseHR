package com.wisehr.wisehr.pay.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PayMemberDTO {
    private int memCode;
    private String memHireDate;
    private List<String> hireDateList;
}
