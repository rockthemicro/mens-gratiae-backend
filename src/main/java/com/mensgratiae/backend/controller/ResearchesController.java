package com.mensgratiae.backend.controller;


import com.mensgratiae.backend.dto.ResearchGetOutput;
import com.mensgratiae.backend.dto.ResearchesGetOutput;
import com.mensgratiae.backend.service.ResearchService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/researches")
@CrossOrigin
@AllArgsConstructor
public class ResearchesController {

    private ResearchService researchService;

    @GetMapping
    public ResponseEntity<ResearchesGetOutput> getResearches() {
        ResearchesGetOutput researchesGetOutput = researchService.getResearches();

        return ResponseEntity
                .ok()
                .body(researchesGetOutput);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResearchGetOutput> getResearch(@PathVariable @Valid long id) {
        ResearchGetOutput researchGetOutput = researchService.getResearch(id);

        return ResponseEntity
                .ok()
                .body(researchGetOutput);
    }
}
