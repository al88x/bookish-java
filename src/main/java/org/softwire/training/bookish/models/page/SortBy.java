package org.softwire.training.bookish.models.page;

public enum SortBy {
    TITLE_ASCENDING("title-asc", "Title: Ascending"),
    TITLE_DESCENDING("title-desc", "Title: Descending"),
    AUTHOR_ASCENDING("author-asc", "Author: Ascending"),
    AUTHOR_DESCENDING("author-desc", "Author: Descending");

    public String filter;
    public String displayName;

    SortBy(String filter, String displayName) {
        this.filter = filter;
        this.displayName = displayName;
    }
}
