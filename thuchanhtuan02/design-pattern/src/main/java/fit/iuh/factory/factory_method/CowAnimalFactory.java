package fit.iuh.factory.factory_method;

public class CowAnimalFactory implements AnimalFactory {
   @Override
   public Animal createAnimal() {
      return new Cow();
   }
}
