package com.mensgratiae.backend.dto.outputs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasicOutput {

    private StatusEnum status = StatusEnum.OK;

    private List<String> info = new ArrayList<>();
    private List<String> warnings = new ArrayList<>();
    private List<String> errors = new ArrayList<>();

    public enum StatusEnum {
        OK,
        ERROR
    }

    public void addInfoItem(String infoItem) {
        if (this.info == null) this.info = new ArrayList<>();

        this.info.add(infoItem);
    }

    public void addWarningsItem(String warningsItem) {
        if (this.warnings == null) this.warnings = new ArrayList<>();

        this.warnings.add(warningsItem);
    }

    public void addErrorsItem(String errorItem) {
        if (this.errors == null) this.errors = new ArrayList<>();

        this.errors.add(errorItem);
    }
}
