package observe_pattern.task_tracker;

public class Main {
   public static void main(String[] args) {
      ObserverTask user = new User("Khang 1");
      ObserverTask user1 = new User("Khang 2");
      ObserverTask user2 = new User("Khang 3");
      TaskProject project = new TaskProject();
      project.addSubscriber(user);
      project.addSubscriber(user1);
      project.addSubscriber(user2);
      project.updateTask();
   }
}
