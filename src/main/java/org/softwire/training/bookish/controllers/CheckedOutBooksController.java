package org.softwire.training.bookish.controllers;

import org.softwire.training.bookish.models.database.Book;
import org.softwire.training.bookish.models.page.CheckedOutBooksPageModel;
import org.softwire.training.bookish.services.CheckedOutBooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
@RequestMapping("/checkedoutBooks")
public class CheckedOutBooksController {

    private CheckedOutBooksService checkedOutBooksService;

    @Autowired
    public CheckedOutBooksController(CheckedOutBooksService checkedOutBooksService) {
        this.checkedOutBooksService = checkedOutBooksService;
    }


    @RequestMapping("")
    ModelAndView getAllBooks() {
        List<Book> bookList;
        CheckedOutBooksPageModel checkedOutBooksPageModel = new CheckedOutBooksPageModel();

        bookList = checkedOutBooksService.getAllCheckedOutBooks();


        checkedOutBooksPageModel.setCheckedOutBooksList(bookList);

        return new ModelAndView("cheked-out-books", "model", checkedOutBooksPageModel);

    }
}
