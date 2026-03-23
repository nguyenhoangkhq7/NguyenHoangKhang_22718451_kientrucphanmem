package quanly_thuvien.borrow;

import quanly_thuvien.book.Book;

public class TranslatedDecorator extends BorrowingDecorator {
    private String language;

    public TranslatedDecorator(Borrowing borrowing, String language) {
        super(borrowing);
        this.language = language;
    }

    @Override
    public void borrowBook(Book book) {
        super.borrowBook(book);
        System.out.println(" - Ordering Translated version in " + language + ".");
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " + Translated (" + language + ")";
    }
}
