package fit.iuh.factory.factory_method;

import java.util.Scanner;

public class Main {
   public static void main(String[] args) {
      Scanner sc = new Scanner(System.in);
      AnimalFactory factory;
      Animal animal = null;
      System.out.println("Bạn chọn âm thanh loài vật nào:");
      System.out.println("1. Con chó");
      System.out.println("2. Con bò");
      int choice = sc.nextInt();
      if(choice == 1) {
         factory = new DogAnimalFactory();
         animal = factory.createAnimal();
      } else if(choice == 2) {
         factory = new CowAnimalFactory();
         animal = factory.createAnimal();
      } else {
         System.out.println("Hãy chọn 1 hoặc 2");
      }
      if(animal!=null)
         animal.sound();
   }
}
