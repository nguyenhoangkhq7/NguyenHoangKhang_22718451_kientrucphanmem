package fit.iuh.factory.abstact_factory;

import fit.iuh.factory.abstact_factory.entity.Quiz;
import fit.iuh.factory.abstact_factory.entity.User;
import fit.iuh.factory.abstact_factory.entity.Video;
import fit.iuh.factory.abstact_factory.factory.CourseFactory;
import fit.iuh.factory.abstact_factory.factory.HighQualityFactory;
import fit.iuh.factory.abstact_factory.factory.LowQualityFactory;

public class Main {
   public static void main(String[] args) {
      User user = new User(true); // user có mua premium hay không
      CourseFactory factory;
      Video video = null;
      Quiz quiz = null;
      if(user.isPremium()) {
         factory = new HighQualityFactory();
      } else {
         factory = new LowQualityFactory();
      }
      video = factory.createVideo();
      quiz = factory.createQuiz();
      video.playVideo();
      quiz.startQuiz();
   }
}
