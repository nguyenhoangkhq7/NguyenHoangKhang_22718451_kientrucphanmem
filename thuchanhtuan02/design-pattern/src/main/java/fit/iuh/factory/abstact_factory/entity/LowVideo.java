package fit.iuh.factory.abstact_factory.entity;

public class LowVideo implements Video{
   @Override
   public void playVideo() {
      System.out.println("Playing low quality video");
   }
}
