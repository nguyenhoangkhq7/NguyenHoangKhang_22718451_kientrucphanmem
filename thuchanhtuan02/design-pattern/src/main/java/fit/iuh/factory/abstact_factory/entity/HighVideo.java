package fit.iuh.factory.abstact_factory.entity;

public class HighVideo implements Video{
   @Override
   public void playVideo() {
      System.out.println("Playing high quality video");
   }
}
