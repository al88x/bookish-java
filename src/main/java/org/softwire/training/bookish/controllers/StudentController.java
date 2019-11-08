package org.softwire.training.bookish.controllers;

import org.softwire.training.bookish.models.database.Book;
import org.softwire.training.bookish.models.database.Student;
import org.softwire.training.bookish.models.page.BooksPageModel;
import org.softwire.training.bookish.models.page.StudentPageModel;
import org.softwire.training.bookish.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

import static org.softwire.training.bookish.models.page.SearchType.BOOK;
import static org.softwire.training.bookish.models.page.SortBy.TITLE_ASCENDING;

@Controller
@RequestMapping("/students")
public class StudentController {

    private StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @RequestMapping("")
    ModelAndView getAllStudent(@RequestParam(required = false) String bookCopyId, @RequestParam(required = false) String search) {
        List<Student> studentList;
        String searchWord = search;
        StudentPageModel studentPageModel = new StudentPageModel();
        if (searchWord == null) {
            searchWord = "";
        }

        studentList = studentService.getAllStudents(searchWord);
        studentPageModel.setStudentList(studentList);
        studentPageModel.setSearchWord(searchWord);
        studentPageModel.setBookCopyId(bookCopyId);
        return new ModelAndView("student", "model", studentPageModel);
    }


    @RequestMapping("/checkout")
    RedirectView checkOutBook(@RequestParam String studentId, @RequestParam String bookCopyId) {
        studentService.checkOutBookCopy(studentId,bookCopyId);

        return new RedirectView("/books");
    }
}
