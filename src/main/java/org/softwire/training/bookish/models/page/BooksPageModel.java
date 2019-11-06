package org.softwire.training.bookish.models.page;

import org.softwire.training.bookish.models.database.Book;

import java.util.Arrays;
import java.util.List;

import static org.softwire.training.bookish.models.page.SearchType.AUTHOR;
import static org.softwire.training.bookish.models.page.SearchType.BOOK;
import static org.softwire.training.bookish.models.page.SortBy.*;

public class BooksPageModel {

    private List<Book> bookList;
    private SortBy sortBy;
    private SearchType searchType;
    private String searchWord;

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public void setSortBy(SortBy sortBy) {
        this.sortBy = sortBy;
    }

    public SearchType getSearchType() {
        return searchType;
    }

    public List<SearchType> getSearchTypeValues() {
        return Arrays.asList(SearchType.values());
    }

    public void setSearchType(String searchType) {
        if (searchType.equals(BOOK.filter)) {
            this.searchType = BOOK;
        }
        if (searchType.equals(AUTHOR.filter)) {
            this.searchType = AUTHOR;
        }
    }

    public SortBy getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        if (sortBy.equals(TITLE_ASCENDING.filter)) {
            this.sortBy = TITLE_ASCENDING;
        }
        if (sortBy.equals(TITLE_DESCENDING.filter)) {
            this.sortBy = TITLE_DESCENDING;
        }
        if (sortBy.equals(AUTHOR_ASCENDING.filter)) {
            this.sortBy = AUTHOR_DESCENDING;
        }
        if (sortBy.equals(AUTHOR_DESCENDING.filter)) {
            this.sortBy = AUTHOR_DESCENDING;
        }
    }


    public List<SortBy> getSortByValues() {
        return Arrays.asList(SortBy.values());
    }

    public void setSearchWord(String search) {
        this.searchWord = search;
    }

    public String getSearchWord() {
        return searchWord;
    }
}
