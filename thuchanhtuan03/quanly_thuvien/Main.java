package quanly_thuvien;

import quanly_thuvien.book.Book;
import quanly_thuvien.book.BookFactory;
import quanly_thuvien.book.BookType;
import quanly_thuvien.library.Library;
import quanly_thuvien.notification.Observer;
import quanly_thuvien.notification.Staff;
import quanly_thuvien.notification.User;
import quanly_thuvien.search.SearchByAuthor;
import quanly_thuvien.search.SearchByCategory;
import quanly_thuvien.search.SearchByName;
import quanly_thuvien.search.SearchStrategy;
import quanly_thuvien.borrow.*;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Library library = Library.getInstance();
        Scanner scanner = new Scanner(System.in);

        library.registerObserver(new User("Alice"));
        library.registerObserver(new Staff("S001"));

        library.addBook(BookFactory.createBook(BookType.PAPER, "B001", "Design Patterns", "GoF", "Programming"));
        library.addBook(BookFactory.createBook(BookType.EBOOK, "B002", "Clean Code", "Robert C. Martin", "Programming"));

        while (true) {
            System.out.println("\n===== HỆ THỐNG QUẢN LÝ THƯ VIỆN =====");
            System.out.println("1. Xem danh sách sách");
            System.out.println("2. Thêm sách mới");
            System.out.println("3. Tìm kiếm sách");
            System.out.println("4. Mượn sách");
            System.out.println("5. Trả sách");
            System.out.println("0. Thoát");
            System.out.print("Chọn chức năng: ");
            
            int choice = -1;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số hợp lệ!");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.println("--- Danh sách sách hiện có ---");
                    for (Book b : library.getAllBooks()) {
                        System.out.println(b.toString());
                    }
                    break;
                case 2:
                    System.out.println("--- Thêm sách mới ---");
                    System.out.print("Chọn loại sách (1. Giấy, 2. E-Book, 3. Audio): ");
                    String typeChoice = scanner.nextLine();
                    BookType type = BookType.PAPER;
                    if (typeChoice.equals("2")) type = BookType.EBOOK;
                    if (typeChoice.equals("3")) type = BookType.AUDIO;

                    System.out.print("Nhập ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Nhập Tên Sách: ");
                    String title = scanner.nextLine();
                    System.out.print("Nhập Tác Giả: ");
                    String author = scanner.nextLine();
                    System.out.print("Nhập Thể Loại: ");
                    String category = scanner.nextLine();

                    library.addBook(BookFactory.createBook(type, id, title, author, category));
                    System.out.println("Thêm thành công!");
                    break;
                case 3:
                    System.out.println("--- Tìm kiếm sách ---");
                    System.out.println("Tìm theo: 1. Tên, 2. Tác giả, 3. Thể loại");
                    System.out.print("Chọn: ");
                    String searchType = scanner.nextLine();
                    System.out.print("Nhập từ khóa: ");
                    String keyword = scanner.nextLine();

                    SearchStrategy strategy = new SearchByName();
                    if (searchType.equals("2")) strategy = new SearchByAuthor();
                    if (searchType.equals("3")) strategy = new SearchByCategory();

                    List<Book> results = library.searchBooks(strategy, keyword);
                    if (results.isEmpty()) {
                        System.out.println("Không tìm thấy sách phù hợp.");
                    } else {
                        System.out.println("Kết quả:");
                        results.forEach(b -> System.out.println(b.toString()));
                    }
                    break;
                case 4:
                    System.out.println("--- Mượn sách ---");
                    System.out.print("Nhập ID sách muốn mượn: ");
                    String borrowId = scanner.nextLine();
                    Book foundBook = library.getAllBooks().stream()
                            .filter(b -> b.getId().equals(borrowId))
                            .findFirst()
                            .orElse(null);

                    if (foundBook == null) {
                        System.out.println("Không tìm thấy sách!");
                        break;
                    }

                    if (foundBook.isBorrowed()) {
                        System.out.println("Sách này đã có người mượn!");
                        break;
                    }

                    Borrowing borrowing = new BaseBorrowing();
                    System.out.print("Bạn có muốn gia hạn thêm thời gian không? (y/n): ");
                    if (scanner.nextLine().equalsIgnoreCase("y")) {
                        borrowing = new ExtendedTimeDecorator(borrowing);
                    }

                    System.out.print("Bạn có yêu cầu bản dịch không? (y/n): ");
                    if (scanner.nextLine().equalsIgnoreCase("y")) {
                        borrowing = new TranslatedDecorator(borrowing, "Vietnamese");
                    }

                    borrowing.borrowBook(foundBook);
                    System.out.println("Chi tiết mượn: " + borrowing.getDescription());
                    foundBook.setBorrowed(true);
                    System.out.println("Mượn sách thành công!");
                    break;
                case 5:
                    System.out.println("--- Trả sách ---");
                    System.out.print("Nhập ID sách muốn trả: ");
                    String returnId = scanner.nextLine();
                    Book returnBook = library.getAllBooks().stream()
                            .filter(b -> b.getId().equals(returnId))
                            .findFirst()
                            .orElse(null);

                    if (returnBook == null) {
                        System.out.println("Không tìm thấy sách!");
                    } else if (!returnBook.isBorrowed()) {
                        System.out.println("Sách này chưa được mượn!");
                    } else {
                        returnBook.setBorrowed(false);
                        System.out.println("Trả sách " + returnBook.getTitle() + " thành công!");
                    }
                    break;
                case 0:
                    System.out.println("Tạm biệt!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }
}
