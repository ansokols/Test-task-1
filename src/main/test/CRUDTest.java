import main.DAO.BookDao;
import main.DAO.UserBookDao;
import main.DAO.UserDao;
import main.DTO.Book;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.time.Year;

public class CRUDTest {
    private static BookDao bookDao;
    private static UserBookDao userBookDao;
    private static UserDao userDao;

    @BeforeAll
    static void start() {
        bookDao = new BookDao();
        userBookDao = new UserBookDao();
        userDao = new UserDao();
    }

    @Test
    void addBook() {
        String name = "name";
        String author = "author";
        String genre = "genre";
        String language = "language";
        String publisher = "publisher";
        Year year = Year.of(1987);
        Book book = new Book(null, name, author, genre, language, publisher, year);

        int bookId = bookDao.save(book);
        Book bookFromDB = bookDao.get(bookId);
        Assertions.assertEquals(name, bookFromDB.getName(), "Name must be equals");
        Assertions.assertEquals(author, bookFromDB.getAuthor(), "Author must be equals");
        Assertions.assertEquals(genre, bookFromDB.getGenre(), "Genre must be equals");
        Assertions.assertEquals(language, bookFromDB.getLanguage(), "Language must be equals");
        Assertions.assertEquals(publisher, bookFromDB.getPublisher(), "Publisher must be equals");
        Assertions.assertEquals(year, bookFromDB.getYear(), "Year must be equals");
    }
}
