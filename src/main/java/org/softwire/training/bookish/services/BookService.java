package org.softwire.training.bookish.services;

import org.jdbi.v3.core.mapper.reflect.BeanMapper;
import org.softwire.training.bookish.models.database.Author;
import org.softwire.training.bookish.models.database.Book;
import org.softwire.training.bookish.models.database.BookCopy;
import org.softwire.training.bookish.models.database.Technology;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.softwire.training.bookish.models.page.SearchType.AUTHOR;
import static org.softwire.training.bookish.models.page.SearchType.BOOK;
import static org.softwire.training.bookish.models.page.SortBy.*;

@Service
public class BookService extends DatabaseService {


    private static final String SELECT_ALL = "SELECT book.id as b_id, book.isbn as b_isbn, book.title as b_title, author.id as a_id, author.first_name as a_firstName, author.last_name as a_lastName\n" +
            "FROM book\n" +
            "JOIN book_author\n" +
            "ON book.id = book_author.book_id\n" +
            "JOIN author\n" +
            "ON book_author.author_id = author.id\n";

    private static final String SELECT_ALL_Q = "SELECT book.id as b_id, book.isbn as b_isbn, book.title as b_title, author.id as a_id, author.first_name as a_firstName, author.last_name as a_lastName,book_copy.id as bc_id, book_copy.is_available as bc_isAvailable\n" +
            "FROM book\n" +
            "JOIN book_author\n" +
            "ON book.id = book_author.book_id\n" +
            "JOIN author\n" +
            "ON book_author.author_id = author.id\n" +
            "JOIN book_copy\n" +
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
        if(search.length() == 0){
            return "";
        }
        if (searchType.equals(AUTHOR.filter)) {
            return "WHERE author.first_name REGEXP " + "\"" + search + "\"\n";
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
            return "ORDER BY author.first_name ASC;";
        }
        if (sort.equals(AUTHOR_DESCENDING.filter)) {
            return "ORDER BY author.first_name DESC;";
        }
        return "ORDER BY title ASC;";
    }
}
