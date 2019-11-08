package org.softwire.training.bookish.exception;

import org.softwire.training.bookish.models.database.Book;

import java.util.function.Supplier;

public class BookNotFoundException extends RuntimeException{
    public BookNotFoundException(String s) {
        super(s);
    }
}
