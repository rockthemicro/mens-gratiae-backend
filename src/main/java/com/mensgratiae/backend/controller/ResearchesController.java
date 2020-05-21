package com.mensgratiae.backend.controller;


import com.mensgratiae.backend.dto.*;
import com.mensgratiae.backend.service.ResearchService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @PostMapping("/add")
    public ResponseEntity<AddOrUpdateResearchOutput> addResearch(@RequestBody @Valid ResearchDto researchDto) {
        AddOrUpdateResearchOutput addOrUpdateResearchOutput = researchService.addOrUpdateResearch(researchDto, true);

        return ResponseEntity
                .ok()
                .body(addOrUpdateResearchOutput);
    }

    @PutMapping("/update")
    public ResponseEntity<AddOrUpdateResearchOutput> updateResearch(@RequestBody @Valid ResearchDto researchDto) {
        AddOrUpdateResearchOutput addOrUpdateResearchOutput = researchService.addOrUpdateResearch(researchDto, false);

        return ResponseEntity
                .ok()
                .body(addOrUpdateResearchOutput);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BasicOutput> deleteResearch(@PathVariable @Valid long id) {
        return ResponseEntity
                .ok()
                .body(researchService.deleteResearch(id));
    }

    @PostMapping("/genericResearchQuestions/add")
    public ResponseEntity<AddOrUpdateGenericResearchQuestionOutput> addGenericResearchQuestion(
            @RequestBody @Valid GenericResearchQuestionDto questionDto) {

        return ResponseEntity
                .ok()
                .body(researchService.addOrUpdateGenericResearchQuestion(questionDto, true));
    }


    @PutMapping("/genericResearchQuestions/update")
    public ResponseEntity<AddOrUpdateGenericResearchQuestionOutput> updateGenericResearchQuestion(
            @RequestBody @Valid GenericResearchQuestionDto questionDto) {

        return ResponseEntity
                .ok()
                .body(researchService.addOrUpdateGenericResearchQuestion(questionDto, false));
    }

    @DeleteMapping("/genericResearchQuestions/{id}")
    public ResponseEntity<BasicOutput> deleteGenericResearchQuestion(@PathVariable @Valid long id) {
        return ResponseEntity
                .ok()
                .body(researchService.deleteGenericResearchQuestion(id));
    }

    @PostMapping("/genericResearchQuestionAnswers")
    public ResponseEntity<BasicOutput> addGenericResearchQuestionAnswers(
            @RequestBody List<GenericResearchQuestionAnswerDto> answers) {

        return ResponseEntity
                .ok()
                .body(researchService.addGenericResearchQuestionAnswers(answers));
    }

    @GetMapping("/genericResearchQuestionAnswers/{researchId}")
    public ResponseEntity<GenericResearchQuestionAnswersGetOutput> getGenericResearchQuestionAnswers(
            @PathVariable @Valid long researchId) {

        return ResponseEntity
                .ok()
                .body(researchService.getGenericResearchQuestionAnswers(researchId));
    }
}
