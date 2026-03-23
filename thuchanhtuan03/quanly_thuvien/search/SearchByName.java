package quanly_thuvien.search;

import quanly_thuvien.book.Book;
import java.util.List;
import java.util.stream.Collectors;

public class SearchByName implements SearchStrategy {
    @Override
    public List<Book> search(List<Book> books, String keyword) {
        return books.stream()
                .filter(b -> b.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }
}
