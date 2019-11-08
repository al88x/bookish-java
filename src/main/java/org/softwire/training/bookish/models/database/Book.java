package org.softwire.training.bookish.models.database;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Book {

    private String id;
    private String title;
    private String author;
    private Set<Author> authorList;
    private String bookCategory;
    private String createdAt;
    private String updatedAt;
    private String slug;
    private String isbn;
    private String subtitle;
    private String subjects;
    private String coverPhotoUrl;
    private String numberOfCopies;
    private Set<BookCopy> bookCopyList;


    public Book() {
        authorList = new HashSet<>();
        bookCopyList = new HashSet<>();
    }

    public String getBookCategoryId() {
        return bookCategory;
    }

    public void setBookCategory(String bookCategory) {
        this.bookCategory = bookCategory;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getSubjects() {
        return subjects;
    }

    public void setSubjects(String subjects) {
        this.subjects = subjects;
    }

    public String getCoverPhotoUrl() {
        return coverPhotoUrl;
    }

    public void setCoverPhotoUrl(String coverPhotoUrl) {
        this.coverPhotoUrl = coverPhotoUrl;
    }

    public void setBookCopyList(Set<BookCopy> bookCopyList) {
        this.bookCopyList = bookCopyList;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void addBookCopy(BookCopy bookCopy) {
        this.bookCopyList.add(bookCopy);
    }

    public Set<Author> getAuthorList() {
        return authorList;
    }

    public String getAuthorListAsString() {


        String divider = ", ";
        StringBuilder builder = new StringBuilder();
        if (authorList.size() > 0) {
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
        return "";
    }

    public Set<BookCopy> getBookCopyList() {
        return bookCopyList;
    }

    public int getAvailableBooks() {
        return (int) bookCopyList.stream()
                .filter(BookCopy::isAvailable)
                .count();
    }


    public int getNumberOfCopies() {
        return bookCopyList.size();
    }

    public void setAuthorList(Set<Author> authorList) {
        this.authorList = authorList;
    }


}
