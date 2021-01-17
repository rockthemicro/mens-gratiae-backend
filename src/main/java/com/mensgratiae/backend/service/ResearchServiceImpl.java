package com.mensgratiae.backend.service;

import com.mensgratiae.backend.dto.*;
import com.mensgratiae.backend.model.*;
import com.mensgratiae.backend.model.mapper.GenericResearchQuestionMapper;
import com.mensgratiae.backend.model.mapper.ResearchMapper;
import com.mensgratiae.backend.model.mapper.TestMapper;
import com.mensgratiae.backend.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.supercsv.io.CsvListWriter;
import org.supercsv.io.ICsvListWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@AllArgsConstructor
public class ResearchServiceImpl implements ResearchService {

    private ResearchRepository researchRepository;
    private GenericResearchQuestionRepository genericResearchQuestionRepository;
    private GenericResearchQuestionAnswerRepository genericResearchQuestionAnswerRepository;
    private RangeTestQuestionAnswerRepository rangeTestQuestionAnswerRepository;
    private ResearchSubmissionRepository researchSubmissionRepository;
    private TestRepository testRepository;
    private TestSubmissionRepository testSubmissionRepository;

    private static final String GENERIC_RESEARCH_QUESTION_FORMAT = "generic_question_%d";
    private static final String RANGE_TEST_QUESTION_FORMAT = "test_%d_question_%d";

    @Override
    public ResearchesGetOutput getResearches() {
        ResearchesGetOutput researchesGetOutput = new ResearchesGetOutput();
        List<Research> researchList = researchRepository.findAll();

        List<ResearchDto> researchDtoList = researchList
                .stream()
                .map(ResearchMapper.INSTANCE::researchToResearchDto)
                .collect(Collectors.toList());
        researchesGetOutput.setResearches(researchDtoList);

        return researchesGetOutput;
    }

    @Override
    public ResearchGetOutput getResearch(long id) {
        ResearchGetOutput researchGetOutput = new ResearchGetOutput();

        Optional<Research> researchOpt = researchRepository.findById(id);
        if (researchOpt.isEmpty()) {
            researchGetOutput.setStatus(BasicOutput.StatusEnum.ERROR);
            researchGetOutput.addErrorsItem("Research Id not found");

            return researchGetOutput;
        }

        Research research = researchOpt.get();
        researchGetOutput.setResearch(ResearchMapper.INSTANCE.researchToResearchDto(research));

        List<Test> tests = testRepository.findAllByResearchId(id);
        researchGetOutput.setTests(tests
                .stream()
                .map(TestMapper.INSTANCE::testToTestDto)
                .collect(Collectors.toList()));

        List<GenericResearchQuestion> genericResearchQuestions =
                genericResearchQuestionRepository.findAllByResearch_Id(id);
        researchGetOutput.setGenericResearchQuestions(genericResearchQuestions
                .stream()
                .map(GenericResearchQuestionMapper.INSTANCE::questionToQuestionDto)
                .collect(Collectors.toList()));

        return researchGetOutput;
    }

    @Override
    public AddOrUpdateResearchOutput addOrUpdateResearch(ResearchDto researchDto, boolean isAdd) {
        AddOrUpdateResearchOutput addOrUpdateResearchOutput = new AddOrUpdateResearchOutput();

        Research research = ResearchMapper.INSTANCE.researchDtoToResearch(researchDto);
        if (isAdd) {
            /* if we want to add, then remove the id so that .save() will generate a new one */
            research.setId(0);
        }

        research = researchRepository.save(research);
        addOrUpdateResearchOutput.setResearchId(research.getId());

        return addOrUpdateResearchOutput;
    }

    @Override
    public AddOrUpdateGenericResearchQuestionOutput addOrUpdateGenericResearchQuestion(
            GenericResearchQuestionDto questionDto, boolean isAdd) {

        AddOrUpdateGenericResearchQuestionOutput output = new AddOrUpdateGenericResearchQuestionOutput();

        GenericResearchQuestion question = GenericResearchQuestionMapper.INSTANCE.questionDtoToQuestion(questionDto);
        question.setResearch(new Research());
        question.getResearch().setId(questionDto.getResearchId());

        if (isAdd) {
            question.setId(0);
        }

        question = genericResearchQuestionRepository.save(question);
        output.setGenericResearchQuestionId(question.getId());

        return output;
    }

    @Override
    public BasicOutput deleteResearch(long id) {
        researchRepository.deleteById(id);

        return new BasicOutput();
    }

    @Override
    public BasicOutput deleteGenericResearchQuestion(long id) {
        genericResearchQuestionRepository.deleteById(id);

        return new BasicOutput();
    }

    @Transactional
    @Override
    public BasicOutput addSubmission(AddSubmissionInput submission) {
        ResearchSubmission researchSubmission = new ResearchSubmission();

        researchSubmission.setId(0L);
        researchSubmission.setResearch(new Research());
        researchSubmission.getResearch().setId(submission.getResearchId());

        ResearchSubmission finalResearchSubmission = researchSubmissionRepository.save(researchSubmission);

        submission.getGenericResearchQuestionAnswers().forEach((questionId, answers) -> {
            for (String answer : answers) {
                GenericResearchQuestionAnswer genericResearchQuestionAnswer =
                        new GenericResearchQuestionAnswer();

                genericResearchQuestionAnswer.setId(0);
                genericResearchQuestionAnswer.setAnswer(answer);

                genericResearchQuestionAnswer.setQuestion(new GenericResearchQuestion());
                genericResearchQuestionAnswer
                        .getQuestion()
                        .setId(questionId);

                genericResearchQuestionAnswerRepository
                        .save(genericResearchQuestionAnswer);
            }
        });

        submission.getRangeTestQuestionAnswers().forEach((testId, answers) -> {
            TestSubmission testSubmission = new TestSubmission();

            testSubmission.setId(0);

            testSubmission.setTest(new Test());
            testSubmission.getTest().setId(testId);

            testSubmission.setResearchSubmission(new ResearchSubmission());
            testSubmission
                    .getResearchSubmission()
                    .setId(finalResearchSubmission.getId());

            TestSubmission finalTestSubmission = testSubmissionRepository.save(testSubmission);

            answers.forEach((questionId, answer) -> {
                RangeTestQuestionAnswer rangeTestQuestionAnswer = new RangeTestQuestionAnswer();

                rangeTestQuestionAnswer.setId(0);
                rangeTestQuestionAnswer.setAnswer(answer);

                rangeTestQuestionAnswer.setTestSubmission(new TestSubmission());
                rangeTestQuestionAnswer
                        .getTestSubmission()
                        .setId(finalTestSubmission.getId());

                rangeTestQuestionAnswer.setQuestion(new RangeTestQuestion());
                rangeTestQuestionAnswer
                        .getQuestion()
                        .setId(questionId);

                rangeTestQuestionAnswerRepository
                        .save(rangeTestQuestionAnswer);
            });
        });

        return new BasicOutput();
    }

    @Override
    public BasicOutput deleteSubmission(long submissionId) {
        researchSubmissionRepository.deleteById(submissionId);

        return new BasicOutput();
    }

    @Override
    public BasicOutput getSubmissions(long researchId) {
        BasicOutput result = new BasicOutput();

        if (researchRepository.findById(researchId).isEmpty()) {
            result.setStatus(BasicOutput.StatusEnum.ERROR);
            result.addErrorsItem(format("Could not find research with id %d", researchId));
            return result;
        }

        List<ResearchSubmission> foundResearchSubmissionList = researchSubmissionRepository.findAllByResearchId(researchId);

        Map<Long, Map<String, String>> submissions = new TreeMap<>();
        for (ResearchSubmission foundResearchSubmission : foundResearchSubmissionList) {
            submissions.put(foundResearchSubmission.getId(), new TreeMap<>(this::compareMultiPartStringsByValuesInside));
        }

        foundResearchSubmissionList.stream()
                .map(ResearchSubmission::getAnswers)
                .flatMap(Collection::stream)
                .forEach(answer -> putGenericResearchQuestionAnswer(answer, submissions));

        foundResearchSubmissionList.stream()
                .map(ResearchSubmission::getTestSubmissions)
                .flatMap(Collection::stream)
                .map(TestSubmission::getAnswers)
                .flatMap(Collection::stream)
                .forEach(answer -> putRangeTestQuestionAnswer(answer, submissions));

        writeSubmissionsToFile(submissions);

        return result;
    }

    private void writeSubmissionsToFile(Map<Long, Map<String, String>> submissions) {
        TreeSet<String> headersSet = new TreeSet<>(this::compareMultiPartStringsByValuesInside);
        List<String> headers = new ArrayList<>();
        headers.add("research_submission_id");

        submissions.forEach((researchSubmissionId, map) -> headersSet.addAll(map.keySet()));
        headers.addAll(headersSet);

        try (BufferedWriter output = new BufferedWriter(new FileWriter(new File("output.csv")));
             ICsvListWriter listWriter = new CsvListWriter(output, CsvPreference.STANDARD_PREFERENCE)) {

            listWriter.write(headers);

            submissions.forEach((researchSubmissionId, map) -> {
                List<String> row = new ArrayList<>();

                row.add(researchSubmissionId.toString());
                headersSet.forEach(header -> row.add(map.getOrDefault(header, "NULL")));

                try {
                    listWriter.write(row);
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            });

        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

    }

    private void putGenericResearchQuestionAnswer(GenericResearchQuestionAnswer answer,
                                                  Map<Long, Map<String, String>> submissions) {

        Map<String, String> submission = submissions.get(answer.getResearchSubmission().getId());
        String submissionQuestionId = format(GENERIC_RESEARCH_QUESTION_FORMAT, answer.getQuestion().getId());

        if (submission.containsKey(submissionQuestionId)) {
            String existentResponse = submission.get(submissionQuestionId);
            submission.put(submissionQuestionId, format("%s,%s", existentResponse, answer.getAnswer()));
        } else {
            submission.put(submissionQuestionId, answer.getAnswer());
        }
    }

    private void putRangeTestQuestionAnswer(RangeTestQuestionAnswer answer,
                                            Map<Long, Map<String, String>> submissions) {


        Map<String, String> submission = submissions.get(answer.getTestSubmission().getResearchSubmission().getId());
        String submissionTestQuestionId = format(
                RANGE_TEST_QUESTION_FORMAT,
                answer.getTestSubmission().getTest().getId(),
                answer.getQuestion().getId());

        submission.put(submissionTestQuestionId, answer.getAnswer().toString());
    }

    private int compareMultiPartStringsByValuesInside(String s1, String s2) {
        String[] parts1 = s1.split("_");
        String[] parts2 = s2.split("_");
        int i = 0;

        while (true) {
            /* first string has more parts */
            if (parts1.length > i && parts2.length <= i) {
                return 1;
            }
            /* second string has more parts */
            if (parts1.length <= i && parts2.length > i) {
                return -1;
            }
            /* both strings have the same number of parts, and they are equal */
            if (parts1.length <= i && parts2.length <= i) {
                return 0;
            }

            /* we found a part with different values */
            if (!parts1[i].equalsIgnoreCase(parts2[i])) {
                try {
                    /* the part is a number and can be compared */
                    Long nr1 = Long.valueOf(parts1[i]);
                    Long nr2 = Long.valueOf(parts2[i]);

                    return (int) (nr1 - nr2);

                } catch (NumberFormatException e) {
                    /* the part is not a number and is compared in the usual way */
                    return s1.compareTo(s2);
                }
            }

            i++;
        }
    }

}
