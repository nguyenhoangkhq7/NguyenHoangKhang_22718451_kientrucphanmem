package fit.iuh.factory.abstact_factory.factory;

import fit.iuh.factory.abstact_factory.entity.Quiz;
import fit.iuh.factory.abstact_factory.entity.Video;

public interface CourseFactory {
   Video createVideo();
   Quiz createQuiz();
}
