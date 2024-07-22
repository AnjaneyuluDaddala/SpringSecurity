package com.devdolphins.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LeaveBalanceDTO {
    private Integer totalLeaves;
    private Integer availableLeaves;
}
