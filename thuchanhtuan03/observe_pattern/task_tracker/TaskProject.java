package observe_pattern.task_tracker;

import observe_pattern.bitcoin_tracker.ObserverTrader;

import java.util.ArrayList;
import java.util.List;

public class TaskProject {
   private List<ObserverTask> projectMembers;

   public TaskProject(List<ObserverTask> projectMembers) {
      this.projectMembers = projectMembers;
   }

   public TaskProject() {
   }

   public List<ObserverTask> getProjectMembers() {
      return projectMembers;
   }

   public void setProjectMembers(List<ObserverTask> projectMembers) {
      this.projectMembers = projectMembers;
   }

   public void updateTask() {
      System.out.println("Task updated!");
      if(projectMembers!=null)
         for (ObserverTask projectMember : projectMembers) {
            projectMember.taskNotify();
         }
   }
   void addSubscriber(ObserverTask member) {
      if(projectMembers == null) {
         projectMembers = new ArrayList<>();
      }
      this.projectMembers.add(member);
   }
   void removeSubscriber(ObserverTask member) {
      if(projectMembers == null) {
         projectMembers = new ArrayList<>();
      }
      this.projectMembers.remove(member);
   }
}
