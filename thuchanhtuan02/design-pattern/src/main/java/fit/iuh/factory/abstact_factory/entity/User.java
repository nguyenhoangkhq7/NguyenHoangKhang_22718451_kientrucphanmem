package fit.iuh.factory.abstact_factory.entity;

public class User {
   private boolean isPremium;
   public User (boolean isPremium) {
      this.isPremium = isPremium;
   }

   public boolean isPremium() {
      return isPremium;
   }
   public void setPremium (boolean isPremium) {
      this.isPremium = isPremium;
   }
}
