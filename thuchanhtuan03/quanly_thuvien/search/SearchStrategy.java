package quanly_thuvien.search;

import quanly_thuvien.book.Book;
import java.util.List;

public interface SearchStrategy {
    List<Book> search(List<Book> books, String keyword);
}
