package com.mensgratiae.backend.controller;


import com.mensgratiae.backend.dto.ResearchesGetOutput;
import com.mensgratiae.backend.service.ResearchService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
