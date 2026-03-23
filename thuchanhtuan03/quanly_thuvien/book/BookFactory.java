package quanly_thuvien.book;

public class BookFactory {
    public static Book createBook(BookType type, String id, String title, String author, String category) {
        switch (type) {
            case PAPER: return new PaperBook(id, title, author, category);
            case EBOOK: return new EBook(id, title, author, category);
            case AUDIO: return new AudioBook(id, title, author, category);
            default: throw new IllegalArgumentException("Unknown book type: " + type);
        }
    }
}
