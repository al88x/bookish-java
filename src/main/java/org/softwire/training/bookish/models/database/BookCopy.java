package org.softwire.training.bookish.models.database;

import java.util.Objects;

public class BookCopy {

    private String id;
    private boolean isAvailable;

    public BookCopy() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookCopy)) return false;
        BookCopy bookCopy = (BookCopy) o;
        return getId().equals(bookCopy.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
