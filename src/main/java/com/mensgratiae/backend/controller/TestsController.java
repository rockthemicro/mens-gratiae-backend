package com.mensgratiae.backend.controller;

import com.mensgratiae.backend.dto.*;
import com.mensgratiae.backend.service.TestService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @PostMapping("/add")
    public ResponseEntity<AddOrUpdateTestOutput> addTest(@RequestBody @Valid TestDto testDto) {
        AddOrUpdateTestOutput addOrUpdateTestOutput = testService.addOrUpdateTest(testDto, true);

        return ResponseEntity
                .ok()
                .body(addOrUpdateTestOutput);
    }

    @PutMapping("/update")
    public ResponseEntity<AddOrUpdateTestOutput> updateTest(@RequestBody @Valid TestDto testDto) {
        AddOrUpdateTestOutput addOrUpdateTestOutput = testService.addOrUpdateTest(testDto, false);

        return ResponseEntity
                .ok()
                .body(addOrUpdateTestOutput);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BasicOutput> deleteTest(@PathVariable @Valid long id) {
        return ResponseEntity
                .ok()
                .body(testService.deleteTest(id));
    }

    @PostMapping("/rangeTestQuestions/add")
    public ResponseEntity<AddOrUpdateRangeTestQuestionOutput> addRangeTestQuestion(
            @RequestBody @Valid RangeTestQuestionDto questionDto) {

        AddOrUpdateRangeTestQuestionOutput output = testService.addOrUpdateRangeTestQuestion(questionDto, true);

        return ResponseEntity
                .ok()
                .body(output);
    }

    @PutMapping("/rangeTestQuestions/update")
    public ResponseEntity<AddOrUpdateRangeTestQuestionOutput> updateRangeTestQuestion(
            @RequestBody @Valid RangeTestQuestionDto questionDto) {

        AddOrUpdateRangeTestQuestionOutput output = testService.addOrUpdateRangeTestQuestion(questionDto, false);

        return ResponseEntity
                .ok()
                .body(output);
    }

    @DeleteMapping("/rangeTestQuestions/{id}")
    public ResponseEntity<BasicOutput> deleteRangeTestQuestion(@PathVariable @Valid long id) {
        return ResponseEntity
                .ok()
                .body(testService.deleteRangeTestQuestion(id));
    }

//    @PostMapping("/rangeTestQuestionAnswers")
//    public ResponseEntity<BasicOutput> addRangeTestQuestionAnswers(
//            @RequestBody List<RangeTestQuestionAnswerDto> answersDto) {
//
//        return ResponseEntity
//                .ok()
//                .body(testService.addRangeTestQuestionAnswers(answersDto));
//    }
//
//    @GetMapping("/rangeTestQuestionAnswers/{testId}")
//    public ResponseEntity<RangeTestQuestionAnswersGetOutput> getRangeTestQuestionAnswers(
//            @PathVariable @Valid long testId) {
//
//        return ResponseEntity
//                .ok()
//                .body(testService.getRangeTestQuestionAnswers(testId));
//    }
}
