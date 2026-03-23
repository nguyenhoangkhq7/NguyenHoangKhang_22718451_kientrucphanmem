package quanly_thuvien.book;

public class PaperBook extends Book {
    public PaperBook(String id, String title, String author, String category) {
        super(id, title, author, category);
    }

    @Override
    public void displayInfo() {
        System.out.println("Paper Book: " + getTitle() + " by " + getAuthor());
    }
}
