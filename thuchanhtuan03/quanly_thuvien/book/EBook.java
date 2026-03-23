package quanly_thuvien.book;

public class EBook extends Book {
    public EBook(String id, String title, String author, String category) {
        super(id, title, author, category);
    }

    @Override
    public void displayInfo() {
        System.out.println("E-Book: " + getTitle() + " by " + getAuthor());
    }
}
