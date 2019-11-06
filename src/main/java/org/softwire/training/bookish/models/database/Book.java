package org.softwire.training.bookish.models.database;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Book {

    private String id;
    private String isbn;
    private String title;
    private Set<Author> authorList;
    private Set<BookCopy> bookCopyList;
    private int availableBooks;


    public Book() {
        authorList = new HashSet<>();
        bookCopyList = new HashSet<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void addAuthor(Author author) {

        authorList.add(author);

    }

    public void addBookCopy(BookCopy bookCopy) {
        bookCopy.setAvailable(true);
        this.bookCopyList.add(bookCopy);
    }

    public Set<Author> getAuthorList() {
        return authorList;
    }

    public String getAuthorListAsString() {
        String divider = ", ";
        StringBuilder builder = new StringBuilder();
        for (Author author : authorList) {
            if (authorList.size() > 0) {
                builder.append(author.getFullName() + divider);
            } else {
                builder.append(author.getFullName());
            }
        }
        if (builder.substring(builder.length() - 2, builder.length()).equals(divider)) {
            return builder.substring(0, builder.length() - 2);
        }
        return builder.toString();
    }

    public Set<BookCopy> getBookCopyList() {
        return bookCopyList;
    }

    public int getAvailableBooks() {
        return (int) bookCopyList.stream()
                .filter(BookCopy::isAvailable)
                .count();
    }

    public void setAuthorList(Set<Author> authorList) {
        this.authorList = authorList;
    }
}
