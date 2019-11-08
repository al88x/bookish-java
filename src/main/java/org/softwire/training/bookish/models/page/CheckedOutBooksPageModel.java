package org.softwire.training.bookish.models.page;

import org.softwire.training.bookish.models.database.Book;

import java.util.List;

public class CheckedOutBooksPageModel {

    private List<Book> checkedOutBooksList;

    public List<Book> getCheckedOutBooksList() {
        return checkedOutBooksList;
    }

    public void setCheckedOutBooksList(List<Book> checkedOutBooksList) {
        this.checkedOutBooksList = checkedOutBooksList;
    }
}
