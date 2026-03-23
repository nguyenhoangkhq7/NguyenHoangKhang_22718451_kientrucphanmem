package quanly_thuvien.borrow;

import quanly_thuvien.book.Book;

public interface Borrowing {
    void borrowBook(Book book);
    String getDescription();
}
