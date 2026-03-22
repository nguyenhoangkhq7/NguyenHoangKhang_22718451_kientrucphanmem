package observe_pattern.bitcoin_tracker;

public class HolderTrader implements ObserverTrader{
   private String name;
   public HolderTrader(String name){
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
