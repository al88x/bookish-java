package org.softwire.training.bookish.models.page;

public enum SearchType {
    BOOK("book", "Books"),
    AUTHOR("author", "Author");

    public String filter;
    public String displayName;

    SearchType(String filter, String displayName) {
        this.filter = filter;
        this.displayName = displayName;
    }
}
