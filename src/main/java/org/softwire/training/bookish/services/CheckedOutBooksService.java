package org.softwire.training.bookish.services;

import org.softwire.training.bookish.models.database.Book;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CheckedOutBooksService {
    public List<Book> getAllCheckedOutBooks() {
        return new ArrayList<>();
    }
}
