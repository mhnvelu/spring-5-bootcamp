package com.spring5.projects.spring5webapp.bootstrap;

import com.spring5.projects.spring5webapp.domain.Author;
import com.spring5.projects.spring5webapp.domain.Book;
import com.spring5.projects.spring5webapp.repositories.AuthorRepository;
import com.spring5.projects.spring5webapp.repositories.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootstrapData implements CommandLineRunner {

    private AuthorRepository authorRepository;
    private BookRepository bookRepository;

    public BootstrapData(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        Author eric = new Author("Eric","Evans");
        Book ddd = new Book("Domain Driven Design","12345");
        eric.getBooks().add(ddd);
        ddd.getAuthors().add(eric);

        authorRepository.save(eric);
        bookRepository.save(ddd);

        Author rod = new Author("Rod","Johnson");
        Book springBook = new Book("JEE using Spring Framework","123456");
        rod.getBooks().add(springBook);
        springBook.getAuthors().add(rod);

        authorRepository.save(rod);
        bookRepository.save(springBook);

        System.out.println("Started in Bootstrap...");
        System.out.println("Number of Books : " + bookRepository.count());

    }
}
