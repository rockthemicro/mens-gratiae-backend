package com.mensgratiae.backend.controller;

import com.mensgratiae.backend.dto.TestGetOutput;
import com.mensgratiae.backend.dto.TestsGetOutput;
import com.mensgratiae.backend.service.TestService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/tests")
@CrossOrigin
@AllArgsConstructor
public class TestsController {


    private TestService testService;

    @GetMapping("/{researchId}")
    public ResponseEntity<TestsGetOutput> getTests(@PathVariable @Valid long researchId) {
        TestsGetOutput testsGetOutput = testService.getTests(researchId);

        return ResponseEntity
                .ok()
                .body(testsGetOutput);
    }

    @GetMapping("/test/{id}")
    public ResponseEntity<TestGetOutput> getTest(@PathVariable @Valid long id) {
        TestGetOutput testGetOutput = testService.getTest(id);

        return ResponseEntity
                .ok()
                .body(testGetOutput);
    }
}
