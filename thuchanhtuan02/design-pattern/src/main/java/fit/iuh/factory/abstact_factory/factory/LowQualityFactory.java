package fit.iuh.factory.abstact_factory.factory;

import fit.iuh.factory.abstact_factory.entity.LowQuiz;
import fit.iuh.factory.abstact_factory.entity.LowVideo;
import fit.iuh.factory.abstact_factory.entity.Quiz;
import fit.iuh.factory.abstact_factory.entity.Video;

public class LowQualityFactory implements CourseFactory{

   @Override
   public Video createVideo() {
      return new LowVideo();
   }

   @Override
   public Quiz createQuiz() {
      return new LowQuiz();
   }
}
