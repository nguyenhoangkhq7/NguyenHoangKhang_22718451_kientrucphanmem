package fit.iuh.factory.factory_method;

public class DogAnimalFactory implements AnimalFactory{
   public Animal createAnimal() {
      return new Dog();
   }
}
