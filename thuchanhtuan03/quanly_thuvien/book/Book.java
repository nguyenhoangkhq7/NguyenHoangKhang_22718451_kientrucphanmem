package quanly_thuvien.book;

public abstract class Book {
    private String id;
    private String title;
    private String author;
    private String category;
    private boolean isBorrowed;

    public Book(String id, String title, String author, String category) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.isBorrowed = false;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getCategory() { return category; }
    public boolean isBorrowed() { return isBorrowed; }
    public void setBorrowed(boolean borrowed) { this.isBorrowed = borrowed; }

    public abstract void displayInfo();
    
    @Override
    public String toString() {
        return "Book{" + "id='" + id + '\'' + ", title='" + title + '\'' + 
               ", author='" + author + '\'' + ", category='" + category + '\'' + 
               ", status='" + (isBorrowed ? "Borrowed" : "Available") + '\'' + '}';
    }
}
