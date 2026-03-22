package observe_pattern.bitcoin_tracker;

import java.util.ArrayList;
import java.util.List;

public class BitcoinTracker {
   private List<ObserverTrader> subscribers;
   public BitcoinTracker(List<ObserverTrader> subscribers) {
      this.subscribers = subscribers == null ? new ArrayList<>() : subscribers;
   }

   public BitcoinTracker() {

   }

   void updateBitcoin() {
      System.out.println("Bitcoin just update");
      for(ObserverTrader trader : subscribers) {
         trader.bitcoinNotify();
      }
   }

   void addSubscriber(ObserverTrader subscriber) {
      if(subscribers == null) {
         subscribers = new ArrayList<>();
      }
      this.subscribers.add(subscriber);
   }
   void removeSubscriber(ObserverTrader subscriber) {
      if(subscribers == null) {
         subscribers = new ArrayList<>();
      }
      this.subscribers.remove(subscriber);
   }

   public List<ObserverTrader> getSubscribers() {
      return subscribers;
   }

   public void setSubscribers(List<ObserverTrader> subscribers) {
      this.subscribers = subscribers;
   }
}
