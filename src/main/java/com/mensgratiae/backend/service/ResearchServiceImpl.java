package com.mensgratiae.backend.service;

import com.mensgratiae.backend.dto.*;
import com.mensgratiae.backend.dto.inputs.AddSubmissionInput;
import com.mensgratiae.backend.dto.outputs.AddOrUpdateGenericResearchQuestionOutput;
import com.mensgratiae.backend.dto.outputs.AddOrUpdateResearchOutput;
import com.mensgratiae.backend.dto.outputs.BasicOutput;
import com.mensgratiae.backend.dto.outputs.ResearchGetOutput;
import com.mensgratiae.backend.dto.outputs.ResearchesGetOutput;
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
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static java.lang.String.format;

@Service
@AllArgsConstructor
public class ResearchServiceImpl implements ResearchService {

    private ResearchRepository researchRepository;
    private GenericResearchQuestionRepository genericResearchQuestionRepository;
    private GenericResearchQuestionAnswerRepository genericResearchQuestionAnswerRepository;
    private RangeTestQuestionRepository rangeTestQuestionRepository;
    private RangeTestQuestionAnswerRepository rangeTestQuestionAnswerRepository;
    private ResearchSubmissionRepository researchSubmissionRepository;
    private TestRepository testRepository;
    private TestSubmissionRepository testSubmissionRepository;

    private static final String GENERIC_RESEARCH_QUESTION_FORMAT = "generic_question_%d";
    private static final String RANGE_TEST_QUESTION_FORMAT = "test_%d_question_%d";

    private static final String FILE_SUBMISSIONS = "submissions.csv";
    private static final String FILE_QUESTIONS = "questions.csv";
    private static final String FILE_ZIP_ARCHIVE = "submissions_and_questions.zip";

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

                genericResearchQuestionAnswer.setResearchSubmission(new ResearchSubmission());
                genericResearchQuestionAnswer
                        .getResearchSubmission()
                        .setId(finalResearchSubmission.getId());

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
    public File getSubmissions(long researchId) {
        if (researchRepository.findById(researchId).isEmpty()) {
            throw new RuntimeException(format("Could not find research with id %d", researchId));
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

        writeSubmissionsAndQuestionsToFiles(submissions);

        return new File(FILE_ZIP_ARCHIVE);
    }



    private void writeSubmissionsAndQuestionsToFiles(Map<Long, Map<String, String>> submissions) {
        TreeSet<String> questionNamesSet = new TreeSet<>(this::compareMultiPartStringsByValuesInside);
        submissions.forEach((researchSubmissionId, map) -> questionNamesSet.addAll(map.keySet()));

        try {
            writeQuestionsToFile(questionNamesSet);
            writeSubmissionsToFile(submissions, questionNamesSet);
            zipFiles();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private void zipFiles() throws IOException {
        List<String> srcFiles = Arrays.asList(FILE_QUESTIONS, FILE_SUBMISSIONS);

        try (FileOutputStream fos = new FileOutputStream(FILE_ZIP_ARCHIVE);
             ZipOutputStream zipOut = new ZipOutputStream(fos)) {

            for (String srcFile : srcFiles) {
                File fileToZip = new File(srcFile);
                ZipEntry zipEntry = new ZipEntry(fileToZip.getName());

                zipOut.putNextEntry(zipEntry);

                try (FileInputStream fis = new FileInputStream(fileToZip)) {
                    byte[] bytes = new byte[1024];
                    int length;
                    while ((length = fis.read(bytes)) >= 0) {
                        zipOut.write(bytes, 0, length);
                    }
                }
            }
        }
    }

    private void writeSubmissionsToFile(Map<Long, Map<String, String>> submissions, Set<String> questionNamesSet) throws IOException {
        try (BufferedWriter output = new BufferedWriter(new FileWriter(new File(FILE_SUBMISSIONS)));
             ICsvListWriter listWriter = new CsvListWriter(output, CsvPreference.STANDARD_PREFERENCE)) {

            List<String> headers = new ArrayList<>();
            headers.add("research_submission_id");
            headers.addAll(questionNamesSet);
            listWriter.write(headers);

            for (Map.Entry<Long, Map<String, String>> entry : submissions.entrySet()) {
                List<String> row = new ArrayList<>();
                Long submissionId = entry.getKey();
                Map<String, String> answers = entry.getValue();

                row.add(submissionId.toString());
                questionNamesSet.forEach(questionName -> row.add(answers.getOrDefault(questionName, "NULL")));

                listWriter.write(row);
            }
        }
    }

    private void writeQuestionsToFile(Set<String> questionNamesSet) throws IOException {
        try (BufferedWriter output = new BufferedWriter(new FileWriter(new File(FILE_QUESTIONS)));
             ICsvListWriter listWriter = new CsvListWriter(output, CsvPreference.STANDARD_PREFERENCE)) {

            List<String> headers = new ArrayList<>();
            headers.add("question_name");
            headers.add("type");
            headers.add("question");
            listWriter.write(headers);

            List<String> questionNames = new ArrayList<>(questionNamesSet);
            for (String questionName : questionNames) {

                List<String> row = new ArrayList<>();

                row.add(questionName);
                row.add(getQuestionType(questionName));
                row.add(getQuestionBody(questionName));

                listWriter.write(row);
            }

        }
    }

    private String getQuestionBody(String questionName) {
        String[] questionParts = questionName.split("_");

        if (questionParts.length == 3 && questionParts[0].equalsIgnoreCase("generic")
                && questionParts[1].equalsIgnoreCase("question")) {

            long questionId = Long.valueOf(questionParts[2]);
            Optional<GenericResearchQuestion> question = genericResearchQuestionRepository.findById(questionId);

            if (question.isPresent()) {
                return question.get().getQuestion();
            }
        }


        if (questionParts.length == 4 && questionParts[0].equalsIgnoreCase("test")
                && questionParts[2].equalsIgnoreCase("question")) {

            long questionId = Long.valueOf(questionParts[3]);
            Optional<RangeTestQuestion> question = rangeTestQuestionRepository.findById(questionId);

            if (question.isPresent()) {
                return question.get().getQuestion();
            }
        }

        return "ERROR";
    }

    private String getQuestionType(String questionName) {
        String[] questionParts = questionName.split("_");

        if (questionParts.length == 3 && questionParts[0].equalsIgnoreCase("generic")
                && questionParts[1].equalsIgnoreCase("question")) {

            long questionId = Long.valueOf(questionParts[2]);
            Optional<GenericResearchQuestion> question = genericResearchQuestionRepository.findById(questionId);

            if (question.isPresent()) {
                return format("generic_research_question_%s", question.get().getType().toString());
            }

            return "generic_research_question_ERROR";
        }


        if (questionParts.length == 4 && questionParts[0].equalsIgnoreCase("test")
                && questionParts[2].equalsIgnoreCase("question")) {

            return "range_test_question";
        }

        return "ERROR";
    }

    private void putGenericResearchQuestionAnswer(GenericResearchQuestionAnswer answer,
                                                  Map<Long, Map<String, String>> submissions) {

        Map<String, String> submission = submissions.get(answer.getResearchSubmission().getId());
        String submissionResearchQuestionId = format(GENERIC_RESEARCH_QUESTION_FORMAT, answer.getQuestion().getId());

        if (submission.containsKey(submissionResearchQuestionId)) {
            String existentResponse = submission.get(submissionResearchQuestionId);
            submission.put(submissionResearchQuestionId, format("%s,%s", existentResponse, answer.getAnswer()));
        } else {
            submission.put(submissionResearchQuestionId, answer.getAnswer());
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
