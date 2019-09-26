package capstone.services;

import capstone.domain.LearningStyleAnswers;
import capstone.domain.LearningStyleQuestion;
import capstone.domain.Student;
import capstone.dto.GlobalLearningStyleResults;
import capstone.repositories.LearningStyleQuestionRepository;
import capstone.repositories.StudentRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Component
public class LearningStyleService {


   private final LearningStyleQuestionRepository learningStyleQuestionRepository;
   private final StudentRepository studentRepository;

   public LearningStyleService(LearningStyleQuestionRepository learningStyleQuestionRepository, StudentRepository studentRepository) {
      this.learningStyleQuestionRepository = learningStyleQuestionRepository;
      this.studentRepository = studentRepository;
   }

   public void addQuestion(String question) {
      learningStyleQuestionRepository.save(new LearningStyleQuestion(question));
   }

   public List<GlobalLearningStyleResults> getGlobalResults() {
      Iterable<Student> students = studentRepository.findAll();
      Iterable<LearningStyleQuestion> questions= learningStyleQuestionRepository.findAll();

      List<GlobalLearningStyleResults> results = initializeGlobalResults(questions);
      addValuesToGlobalResults(students, results);
      calculateAverage(results);

      return results;
   }

   private void calculateAverage(List<GlobalLearningStyleResults> results) {
      for(GlobalLearningStyleResults result : results){
         result.setAverage( result.getRunningTotal() / result.getCount());
      }
   }

   private void addValuesToGlobalResults(Iterable<Student> students, List<GlobalLearningStyleResults> results) {
      for(Student student : students){
         List<LearningStyleAnswers> answers = student.getLearningStyleAnswersList();

         for(GlobalLearningStyleResults result : results){
            int questionId = result.getQuestionId();
            try{
               result.setRunningTotal( result.getRunningTotal() + findQuestionResult(answers, questionId));
               result.setCount(result.getCount() + 1);
            } catch (Exception e){
               // ignore and dont add
            }
         }
      }
   }

   private List<GlobalLearningStyleResults> initializeGlobalResults(Iterable<LearningStyleQuestion> questions) {
      List<GlobalLearningStyleResults> results = new ArrayList<>();
      for (LearningStyleQuestion learningStyleQuestion : questions){
         GlobalLearningStyleResults result = new GlobalLearningStyleResults();
         result.setQuestion(learningStyleQuestion.getQuestion());
         result.setQuestionId((int) learningStyleQuestion.getId());
         result.setAverage(0);
         result.setRunningTotal(0);
         results.add(result);
      }
      return results;
   }

   private static int findQuestionResult(List<LearningStyleAnswers> answers, int questionId) throws Exception {
      LearningStyleAnswers result;

      for(LearningStyleAnswers answer : answers){
         if((int) answer.getId() == questionId){
            return (int) answer.getAnswers();
         }
      }
      throw new Exception("Not Found");
   }
}
