package observe_pattern.bitcoin_tracker;

public class DayTrader implements ObserverTrader{
   private String name;
   public DayTrader(String name){
      this.name = name;
   }

   @Override
   public void bitcoinNotify() {
      System.out.println(name + " get a notify");
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }
}
