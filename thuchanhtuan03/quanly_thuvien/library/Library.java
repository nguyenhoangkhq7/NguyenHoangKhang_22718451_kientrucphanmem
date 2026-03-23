package quanly_thuvien.library;

import quanly_thuvien.book.Book;
import quanly_thuvien.notification.Observer;
import quanly_thuvien.notification.Subject;
import quanly_thuvien.search.SearchStrategy;

import java.util.ArrayList;
import java.util.List;

public class Library implements Subject {
    private static Library instance;
    private List<Book> books;
    private List<Observer> observers;

    private Library() {
        books = new ArrayList<>();
        observers = new ArrayList<>();
    }

    public static Library getInstance() {
        if (instance == null) {
            instance = new Library();
        }
        return instance;
    }

    public void addBook(Book book) {
        books.add(book);
        notifyObservers("New book added: " + book.getTitle() + " by " + book.getAuthor());
    }

    public void removeBook(Book book) {
        books.remove(book);
        notifyObservers("Book removed: " + book.getTitle());
    }

    public List<Book> searchBooks(SearchStrategy strategy, String keyword) {
        return strategy.search(books, keyword);
    }
    
    public List<Book> getAllBooks() {
        return books;
    }

    public void getOverdueNotifications(Book book) {
        notifyObservers("OVERDUE ALARM: Please return the book - " + book.getTitle());
    }

    @Override
    public void registerObserver(Observer o) {
        if (!observers.contains(o)) {
            observers.add(o);
        }
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers(String message) {
        for (Observer o : observers) {
            o.update(message);
        }
    }
}
