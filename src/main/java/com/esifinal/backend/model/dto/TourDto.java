package com.esifinal.backend.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class TourDto {
    private String name;
    private String location;
    private Date startTime;
    private Date finishTime;
}
