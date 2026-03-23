package quanly_thuvien.book;

public class AudioBook extends Book {
    public AudioBook(String id, String title, String author, String category) {
        super(id, title, author, category);
    }

    @Override
    public void displayInfo() {
        System.out.println("Audio Book: " + getTitle() + " by " + getAuthor());
    }
}
