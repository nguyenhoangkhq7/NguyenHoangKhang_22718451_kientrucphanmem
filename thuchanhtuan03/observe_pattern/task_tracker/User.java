package observe_pattern.task_tracker;

public class User implements ObserverTask{
   private String name;

   public User(String name) {
      this.name = name;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   @Override
   public void taskNotify() {
      System.out.println(name + " get a notify");
   }
}
