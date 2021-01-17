package com.mensgratiae.backend.controller;


import com.mensgratiae.backend.dto.*;
import com.mensgratiae.backend.service.ResearchService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

    @PostMapping("/addSubmission")
    public ResponseEntity<BasicOutput> addSubmission(@RequestBody @Valid AddSubmissionInput submission) {
        return ResponseEntity
                .ok()
                .body(researchService.addSubmission(submission));
    }

    @DeleteMapping("/deleteSubmission/{id}")
    public ResponseEntity<BasicOutput> deleteSubmission(@PathVariable @Valid long id) {
        return ResponseEntity
                .ok()
                .body(researchService.deleteSubmission(id));
    }

    @GetMapping("/getSubmissions/{id}")
    public ResponseEntity<Resource> getSubmissions(@PathVariable @Valid long id) throws IOException {
        try {
            File result = researchService.getSubmissions(id);
            return getByteArrayResourceResponseEntityFromFile(result);

        } catch (Exception e) {
            File errorFile = new File("ERROR.txt");
            if (!errorFile.exists()) {
                errorFile.createNewFile();
            }

            return getByteArrayResourceResponseEntityFromFile(errorFile);
        }
    }

    private ResponseEntity<Resource> getByteArrayResourceResponseEntityFromFile(File file) throws IOException {
        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        return ResponseEntity.ok()
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
