package fit.iuh.factory.abstact_factory.factory;

import fit.iuh.factory.abstact_factory.entity.HighQuiz;
import fit.iuh.factory.abstact_factory.entity.HighVideo;
import fit.iuh.factory.abstact_factory.entity.Quiz;
import fit.iuh.factory.abstact_factory.entity.Video;

public class HighQualityFactory implements CourseFactory{
   @Override
   public Video createVideo() {
      return new HighVideo();
   }

   @Override
   public Quiz createQuiz() {
      return new HighQuiz();
   }
}
