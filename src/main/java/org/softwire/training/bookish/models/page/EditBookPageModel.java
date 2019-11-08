package org.softwire.training.bookish.models.page;

import org.softwire.training.bookish.models.database.Book;
import org.softwire.training.bookish.models.database.BookCopy;

import java.util.List;

public class EditBookPageModel {

    private Book book;
    private List<BookCopy> bookCopyList;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public List<BookCopy> getBookCopyList() {
        return bookCopyList;
    }

    public void setBookCopyList(List<BookCopy> bookCopyList) {
        this.bookCopyList = bookCopyList;
    }
}
