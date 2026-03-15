package fit.iuh.singleton;

import java.util.Random;

// Singleton pattern và giải quyết vấn đề đa luồng
public class SingletonPattern {
   private static SingletonPattern instance;
   private final int number;
   private SingletonPattern(int number) {this.number = number;}

   public synchronized static SingletonPattern getInstance() {
      if(instance == null) {
         synchronized (SingletonPattern.class) {
            if(instance==null) {
               int randNum = new Random().nextInt();
               instance = new SingletonPattern(randNum);
            }
         }
      }
      return instance;
   }
   public void printHello() {
      System.out.println("Hello, i am singleton" + number);
   }
   public static void main(String[] args) {
      Thread t1 = new Thread(() -> SingletonPattern.getInstance().printHello());
      Thread t2 = new Thread(() -> SingletonPattern.getInstance().printHello());
      Thread t3 = new Thread(() -> SingletonPattern.getInstance().printHello());
      Thread t4 = new Thread(() -> SingletonPattern.getInstance().printHello());
      t1.start();
      t2.start();
      t3.start();
      t4.start();
   }
}
