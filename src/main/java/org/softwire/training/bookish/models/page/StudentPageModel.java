package org.softwire.training.bookish.models.page;

import org.softwire.training.bookish.models.database.Student;

import java.util.List;

public class StudentPageModel {

    private List<Student> studentList;
    private String searchWord;
    private String bookCopyId;

    public String getBookCopyId() {
        return bookCopyId;
    }

    public void setBookCopyId(String bookCopyId) {
        this.bookCopyId = bookCopyId;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    public String getSearchWord() {
        return searchWord;
    }

    public void setSearchWord(String searchWord) {
        this.searchWord = searchWord;
    }
}
