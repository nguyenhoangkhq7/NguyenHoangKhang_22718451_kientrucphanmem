package observe_pattern.bitcoin_tracker;

public class Main {
   public static void main(String[] args) {
      ObserverTrader dayTrader = new DayTrader("HK Day Trader");
      ObserverTrader holderTrader = new HolderTrader("HK Holder Trader");
      BitcoinTracker tracker = new BitcoinTracker();
      tracker.addSubscriber(dayTrader);
      tracker.addSubscriber(holderTrader);
      tracker.updateBitcoin();
   }
}
