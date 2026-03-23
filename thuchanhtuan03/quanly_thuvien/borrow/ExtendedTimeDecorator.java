package quanly_thuvien.borrow;

import quanly_thuvien.book.Book;

public class ExtendedTimeDecorator extends BorrowingDecorator {
    public ExtendedTimeDecorator(Borrowing borrowing) {
        super(borrowing);
    }

    @Override
    public void borrowBook(Book book) {
        super.borrowBook(book);
        System.out.println(" - Adding extended borrowing time (+7 days).");
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " + Extended Time";
    }
}
