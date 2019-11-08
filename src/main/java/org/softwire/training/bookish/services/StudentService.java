package org.softwire.training.bookish.services;

import org.softwire.training.bookish.models.database.Student;
import org.softwire.training.bookish.models.database.Technology;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StudentService extends DatabaseService {
        private BookService bookService;

    @Autowired
    public StudentService(BookService bookService) {
        this.bookService = bookService;
    }

    public List<Student> getAllStudents(String searchWord) {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM student")
                        .mapToBean(Student.class)
                        .list()
        );

    }

    public void checkOutBookCopy(String studentId, String bookCopyId) {

        jdbi.useHandle(handle ->
                handle.createUpdate("INSERT INTO student_copy SET student_id=:studentId, book_copy_id=:bookCopyId, checked_out=:checkedOut, check_in_due=:checkedInDue" )
                        .bind("studentId",studentId)
                        .bind("bookCopyId", bookCopyId)
                        .bind("checkedOut", LocalDate.now().toString())
                        .bind("checkedInDue", LocalDate.now().plusDays(7))
                        .execute()
        );
        bookService.reserveCopy(bookCopyId);
    }
}
