package quanly_thuvien.borrow;

import quanly_thuvien.book.Book;

public class BrailleDecorator extends BorrowingDecorator {
    public BrailleDecorator(Borrowing borrowing) {
        super(borrowing);
    }

    @Override
    public void borrowBook(Book book) {
        super.borrowBook(book);
        System.out.println(" - Ordering Braille version.");
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " + Braille Version";
    }
}
