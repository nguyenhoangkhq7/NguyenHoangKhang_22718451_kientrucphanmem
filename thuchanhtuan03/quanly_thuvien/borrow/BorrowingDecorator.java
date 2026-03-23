package quanly_thuvien.borrow;

import quanly_thuvien.book.Book;

public abstract class BorrowingDecorator implements Borrowing {
    protected Borrowing borrowing;

    public BorrowingDecorator(Borrowing borrowing) {
        this.borrowing = borrowing;
    }

    @Override
    public void borrowBook(Book book) {
        borrowing.borrowBook(book);
    }

    @Override
    public String getDescription() {
        return borrowing.getDescription();
    }
}
