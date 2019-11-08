package org.softwire.training.bookish.controllers;

import org.softwire.training.bookish.models.database.Book;
import org.softwire.training.bookish.models.database.BookCopy;
import org.softwire.training.bookish.models.database.Technology;
import org.softwire.training.bookish.models.page.BooksPageModel;
import org.softwire.training.bookish.models.page.EditBookPageModel;
import org.softwire.training.bookish.models.page.NewBookPageModel;
import org.softwire.training.bookish.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;


import java.util.List;

import static org.softwire.training.bookish.models.page.SearchType.BOOK;
import static org.softwire.training.bookish.models.page.SortBy.TITLE_ASCENDING;

@Controller
@RequestMapping("/books")
public class BookController {

    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @RequestMapping("/edit")
    ModelAndView getBookDetails(@RequestParam String bookId) {
        Book book = bookService.getBook(bookId);
        List<BookCopy> bookCopyList = bookService.getBookCopyList(bookId);
        EditBookPageModel editBookPageModel = new EditBookPageModel();
        editBookPageModel.setBook(book);
        System.err.println(bookCopyList);
        editBookPageModel.setBookCopyList(bookCopyList);

        return new ModelAndView("edit-book", "model", editBookPageModel);
    }

    @RequestMapping("")
    ModelAndView getAllBooks(@RequestParam(required = false) String search, @RequestParam(required = false) String searchType, @RequestParam(required = false) String sort) {
        List<Book> bookList;
        String searchWord = search;
        String type = searchType;
        String filter = sort;
        BooksPageModel booksPageModel = new BooksPageModel();
        if (searchWord == null) {
            searchWord = "";
        }
        if (type == null) {
            type = BOOK.filter;
        }
        if (filter == null) {
            filter = TITLE_ASCENDING.filter;
        }
        bookList = bookService.getAllBooks(searchWord, type, filter);
        booksPageModel.setSearchWord(searchWord);
        booksPageModel.setSearchType(type);
        booksPageModel.setSortBy(filter);


        booksPageModel.setBookList(bookList);

        return new ModelAndView("books", "model", booksPageModel);
    }


    @RequestMapping("/save")
    RedirectView saveBook(@ModelAttribute Book book) {
        bookService.saveBook(book);

        return new RedirectView("/books");
    }

    @RequestMapping("/deleteCopy")
    RedirectView deleteCopy(@RequestParam String bookId, @RequestParam String bookCopyId){
        bookService.deleteCopy(bookId, bookCopyId);
        return  new RedirectView("/books/edit?bookId=" + bookId);
    }

    @RequestMapping("/delete")
    RedirectView delete(@RequestParam String bookId){
        bookService.deleteBook(bookId);
        return  new RedirectView("/books/");
    }

    @RequestMapping("/addCopy")
    RedirectView addCopy(@RequestParam String bookId){
        bookService.addCopy(bookId);
        return  new RedirectView("/books/edit?bookId=" + bookId);
    }

    @RequestMapping("/reserve")
    RedirectView reserveBookCopy(@RequestParam String bookId){
        bookService.reserveCopy(bookId);
        return  new RedirectView("/books");
    }

    @RequestMapping("/addNewBook")
    ModelAndView addNewBook() {


        return new ModelAndView("new_book");
    }
}
