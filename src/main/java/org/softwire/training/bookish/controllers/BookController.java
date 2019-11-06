package org.softwire.training.bookish.controllers;

import org.softwire.training.bookish.models.database.Book;
import org.softwire.training.bookish.models.page.BooksPageModel;
import org.softwire.training.bookish.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @RequestMapping("")
    ModelAndView getAllBooks(@RequestParam String search, @RequestParam String searchType, @RequestParam String sort){
        List<Book> bookList = bookService.getAllBooks(search, searchType, sort);
        BooksPageModel booksPageModel = new BooksPageModel();
        booksPageModel.setSearchWord(search);
        booksPageModel.setSearchType(searchType);
        booksPageModel.setSortBy(sort);
        booksPageModel.setBookList(bookList);

        return new ModelAndView("books", "model", booksPageModel);
    }
    
//    @RequestMapping("")
//    ModelAndView getAllBooksSorted(@RequestParam String books, @RequestParam String sortBy, @RequestParam String sortType){
//        bookService.searchForBooks(books, sortBy, sortType);
//        return null;
//    }


}
