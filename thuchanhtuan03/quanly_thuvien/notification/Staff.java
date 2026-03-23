package quanly_thuvien.notification;

public class Staff implements Observer {
    private String staffId;

    public Staff(String staffId) {
        this.staffId = staffId;
    }

    @Override
    public void update(String message) {
        System.out.println("Staff [" + staffId + "] received notification: " + message);
    }
}
