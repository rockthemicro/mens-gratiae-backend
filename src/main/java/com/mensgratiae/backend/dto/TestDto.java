package com.mensgratiae.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestDto {
    private long id;
    private long researchId;
    private String name;
    private String description;
    private int scale;
    private List<String> options;
    private int relativePosition;
}
