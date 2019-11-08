package org.softwire.training.bookish.services;

import org.jdbi.v3.core.mapper.reflect.BeanMapper;
import org.softwire.training.bookish.exception.BookNotFoundException;
import org.softwire.training.bookish.models.database.Author;
import org.softwire.training.bookish.models.database.Book;
import org.softwire.training.bookish.models.database.BookCopy;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

import static java.util.stream.Collectors.toList;
import static org.softwire.training.bookish.models.page.SearchType.AUTHOR;
import static org.softwire.training.bookish.models.page.SortBy.*;

@Service
public class BookService extends DatabaseService {

    private Map<Integer, BookCopy> bookCopyMap = new HashMap<>();

    private static final String SELECT_ALL = "SELECT book.id as b_id, book.isbn as b_isbn, book.title as b_title, author.id as a_id, author.first_name as a_firstName, author.last_name as a_lastName\n" +
            "FROM book\n" +
            "JOIN book_author\n" +
            "ON book.id = book_author.book_id\n" +
            "JOIN author\n" +
            "ON book_author.author_id = author.id\n";

    private static final String SELECT_ALL_Q = "SELECT book.id as b_id, book.author as b_author, book.isbn as b_isbn, book.title as b_title, author.id as a_id, author.first_name as a_firstName, author.last_name as a_lastName,book_copy.id as bc_id, book_copy.is_available as bc_isAvailable\n" +
            "FROM book\n" +
            "LEFT JOIN book_author\n" +
            "ON book.id = book_author.book_id\n" +
            "LEFT JOIN author\n" +
            "ON book_author.author_id = author.id\n" +
            "LEFT JOIN book_copy\n" +
            "ON book.id = book_copy.book_id\n";

    public List<Book> getAllBooks(String search, String searchType, String sort) {
        return jdbi.withHandle(handle ->
                handle.createQuery(SELECT_ALL_Q + searchList(search, searchType) + orderBy(sort))
                        .registerRowMapper(BeanMapper.factory(Book.class, "b"))
                        .registerRowMapper(BeanMapper.factory(Author.class, "a"))
                        .registerRowMapper(BeanMapper.factory(BookCopy.class, "bc"))

                        .reduceRows(new LinkedHashMap<Long, Book>(),
                                (map, rowView) -> {
                                    Book book = map.computeIfAbsent(

                                            rowView.getColumn("b_id", Long.class),
                                            id -> rowView.getRow(Book.class));

                                    if (rowView.getColumn("a_id", Long.class) != null) {
                                        book.addAuthor(rowView.getRow(Author.class));
                                    }
                                    if (rowView.getColumn("bc_id", Long.class) != null) {
                                        book.addBookCopy(rowView.getRow(BookCopy.class));
                                    }
                                    return map;
                                })
                        .values()
                        .stream()
                        .collect(toList())
        );
    }

    private String searchList(String search, String searchType) {
        if (search.length() == 0) {
            return "";
        }
        if (searchType.equals(AUTHOR.filter)) {
            return "WHERE book.author REGEXP " + "\"" + search + "\"\n";
        }
        return "WHERE book.title REGEXP " + "\"" + search + "\"\n";
    }

    private String orderBy(String sort) {
        if (sort == null) {
            return "ORDER BY title ASC;";
        }
        if (sort.equals(TITLE_ASCENDING.filter)) {
            return "ORDER BY title ASC;";
        }
        if (sort.equals(TITLE_DESCENDING.filter)) {
            return "ORDER BY title DESC;";
        }
        if (sort.equals(AUTHOR_ASCENDING.filter)) {
            return "ORDER BY author ASC;";
        }
        if (sort.equals(AUTHOR_DESCENDING.filter)) {
            return "ORDER BY author DESC;";
        }
        return "ORDER BY title ASC;";
    }

    public Book getBook(String bookId) throws BookNotFoundException {
        return jdbi.withHandle(handle ->
                handle.createQuery(SELECT_ALL_Q + searchById(bookId))
                        .registerRowMapper(BeanMapper.factory(Book.class, "b"))
                        .registerRowMapper(BeanMapper.factory(Author.class, "a"))
                        .registerRowMapper(BeanMapper.factory(BookCopy.class, "bc"))

                        .reduceRows(new LinkedHashMap<Long, Book>(),
                                (map, rowView) -> {
                                    Book book = map.computeIfAbsent(

                                            rowView.getColumn("b_id", Long.class),
                                            id -> rowView.getRow(Book.class));

                                    if (rowView.getColumn("a_id", Long.class) != null) {
                                        book.addAuthor(rowView.getRow(Author.class));
                                    }
                                    if (rowView.getColumn("bc_id", Long.class) != null) {
                                        BookCopy bookCopy = rowView.getRow(BookCopy.class);
                                        Boolean bc_isAvailable = rowView.getColumn("bc_isAvailable", Boolean.class);
//                                        bookCopy.setAvailable(bc_isAvailable);

                                        book.addBookCopy(bookCopy);
                                    }
                                    return map;
                                })
                        .values()
                        .stream()
                        .findFirst()
                        .orElseThrow(() -> {
                            throw new BookNotFoundException("Book with id: " + bookId + "not found");
                        })

        );
    }

    private String searchById(String bookId) {
        return "WHERE book.id = " + bookId + ";";
    }


    public void saveBook(Book book) {
        if (book.getId() != null) {
            jdbi.useHandle(handle ->
                    handle.createUpdate("UPDATE book SET title=:title, author=:author, isbn=:isbn, subtitle=:subtitle,subjects=:subjects," +
                            "cover_photo_url=:coverPhotoUrl, book_category_id=:bookCategoryId," +
                            "created_at=:createdAt, updated_at=:updatedAt, slug=:slug WHERE book.id=:id;")
                            .bind("id", book.getId())
                            .bind("title", book.getTitle())
                            .bind("author", book.getAuthor())
                            .bind("isbn", book.getIsbn())
                            .bind("subtitle", book.getSubtitle())
                            .bind("subjects", book.getSubjects())
                            .bind("coverPhotoUrl", book.getCoverPhotoUrl())
                            .bind("bookCategoryId", book.getBookCategoryId())
                            .bind("createdAt", book.getCreatedAt())
                            .bind("updatedAt", LocalDate.now().toString())
                            .bind("slug", book.getSlug())
                            .execute()
            );
            return;
        }
        jdbi.useHandle(handle ->
                handle.createUpdate("INSERT INTO book SET title=:title, isbn=:isbn, subtitle=:subtitle,subjects=:subjects," +
                        "cover_photo_url=:coverPhotoUrl, book_category_id=:bookCategoryId," +
                        "created_at=:createdAt, updated_at=:updatedAt, slug=:slug ;")
                        .bind("id", book.getId())
                        .bind("title", book.getTitle())
                        .bind("isbn", book.getIsbn())
                        .bind("subtitle", book.getSubtitle())
                        .bind("subjects", book.getSubjects())
                        .bind("coverPhotoUrl", book.getCoverPhotoUrl())
                        .bind("bookCategoryId", book.getBookCategoryId())
                        .bind("createdAt", book.getCreatedAt())
                        .bind("updatedAt", LocalDate.now().toString())
                        .bind("slug", book.getSlug())
                        .execute()
        );
    }

    public List<BookCopy> getBookCopyList(String bookId) {

        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT book_copy.id as id, book_copy.is_available as isAvailable" +
                        " FROM book_copy WHERE book_copy.book_id = " + bookId + ";")
                        .mapToBean(BookCopy.class)
                        .list()
        );
    }

    public void deleteCopy(String bookId, String bookCopyId) {
        jdbi.useHandle(handle ->
                handle.createUpdate("DELETE FROM book_copy WHERE book_copy.book_id = :id " +
                        "AND book_copy.id = :copyId;")
                        .bind("id", bookId)
                        .bind("copyId", bookCopyId)
                        .execute()
        );
    }

    public void addCopy(String bookId) {
        jdbi.useHandle(handle ->
                handle.createUpdate("INSERT INTO book_copy (is_available, book_id)\n" +
                        "VAlUES (1, :id);")
                        .bind("id", bookId)
                        .execute()
        );
    }

    public void reserveCopy(String bookId) {
        List<BookCopy> bookCopyList = getBookCopyList(bookId);
        BookCopy bookCopyToCheckOut = null;
        for(BookCopy bookCopy :  bookCopyList){
            if(bookCopy.isAvailable()){
                bookCopyToCheckOut =bookCopy;
                break;
            }
        }
        if(bookCopyToCheckOut != null){
            bookCopyMap.put(Integer.valueOf(bookId), bookCopyToCheckOut);
            BookCopy finalBookCopyToCheckOut = bookCopyToCheckOut;
            jdbi.useHandle(handle ->
                    handle.createUpdate("UPDATE book_copy set book_copy.is_available = 0\n" +
                            "WHERE book_copy.id = :id;")

                            .bind("id", finalBookCopyToCheckOut.getId())
                            .execute()
            );
            System.out.println(bookCopyMap);
        }
    }

    public void deleteBook(String bookId) {
        jdbi.useHandle(handle ->
                handle.createUpdate("DELETE FROM book WHERE id = :id")
                        .bind("id", bookId)
                        .execute()
        );
    }
}
