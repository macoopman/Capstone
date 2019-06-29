package capstone.enums;

public enum Subject {

   ENG("English"),
   BIO("Biology"),
   CSC("Computer Science");

   // add more later

   public final String label;

   private Subject(String label) {
      this.label = label;
   }
}
