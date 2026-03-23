package quanly_thuvien.borrow;

import quanly_thuvien.book.Book;

public class BaseBorrowing implements Borrowing {
    @Override
    public void borrowBook(Book book) {
        System.out.println("Borrowing base version of: " + book.getTitle());
    }

    @Override
    public String getDescription() {
        return "Standard Borrowing (14 days)";
    }
}
