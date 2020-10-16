// Book.aidl
package com.example.wp.awesomemmz.book;
import com.example.wp.awesomemmz.book.Book;

// Declare any non-default types here with import statements

interface IBookManager {
    List<Book> listBooks();
    void addBook(in Book book);
}