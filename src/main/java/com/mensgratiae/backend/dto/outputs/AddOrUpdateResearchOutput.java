package com.mensgratiae.backend.dto.outputs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddOrUpdateResearchOutput extends BasicOutput {
    private long researchId;
}
