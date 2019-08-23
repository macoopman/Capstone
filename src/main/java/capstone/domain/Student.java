package capstone.domain;


import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class Student extends Person {

   private double gpa;

   private String major;

   @ElementCollection(targetClass=LearningStyleAnswers.class)
   List<LearningStyleAnswers> learningStyleAnswersList = new LinkedList<>();


   public Student(Long id, String firstName, String lastName, String email, double gpa, String major, List<LearningStyleAnswers> learningStyleAnswersList) {
      super(id, firstName, lastName, email);
      this.gpa = gpa;
      this.major = major;
      this.learningStyleAnswersList = learningStyleAnswersList;
   }


   public void setLearningStyleAnswersList(List<LearningStyleAnswers> learningStyleAnswersList) {
      this.learningStyleAnswersList = learningStyleAnswersList;
   }

   public List<LearningStyleAnswers> getLearningStyleAnswersList() {

      List<LearningStyleAnswers> resultsList = new ArrayList<>();
      for (LearningStyleAnswers answer : learningStyleAnswersList){
         resultsList.add(answer);
      }
      return resultsList;
   }




}
