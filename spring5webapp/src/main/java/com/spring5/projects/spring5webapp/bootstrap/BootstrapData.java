package com.spring5.projects.spring5webapp.bootstrap;

import com.spring5.projects.spring5webapp.domain.Address;
import com.spring5.projects.spring5webapp.domain.Author;
import com.spring5.projects.spring5webapp.domain.Book;
import com.spring5.projects.spring5webapp.domain.Publisher;
import com.spring5.projects.spring5webapp.repositories.AuthorRepository;
import com.spring5.projects.spring5webapp.repositories.BookRepository;
import com.spring5.projects.spring5webapp.repositories.PublisherRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootstrapData implements CommandLineRunner {

    private AuthorRepository authorRepository;
    private BookRepository bookRepository;
    private PublisherRepository publisherRepository;

    public BootstrapData(AuthorRepository authorRepository, BookRepository bookRepository,
                         PublisherRepository publisherRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Started in Bootstrap...");
        Address address = new Address("Anna Salai", "Chennai", "India", "600001");
        Publisher tata = new Publisher("Tata Mcgraw  Hill", address);
        publisherRepository.save(tata);

        Author eric = new Author("Eric", "Evans");
        Book ddd = new Book("Domain Driven Design", "12345");
        eric.getBooks().add(ddd);
        ddd.getAuthors().add(eric);
        ddd.setPublisher(tata);
        tata.getBooks().add(ddd);

        authorRepository.save(eric);
        bookRepository.save(ddd);
        publisherRepository.save(tata);

        Author rod = new Author("Rod", "Johnson");
        Book springBook = new Book("JEE using Spring Framework", "123456");
        rod.getBooks().add(springBook);
        springBook.getAuthors().add(rod);
        springBook.setPublisher(tata);
        tata.getBooks().add(springBook);

        authorRepository.save(rod);
        bookRepository.save(springBook);
        publisherRepository.save(tata);

        System.out.println("Number of Books : " + bookRepository.count());
        System.out.println("Number of Publishers : " + publisherRepository.count());
        System.out.println("Number of Books published  : " + tata.getBooks().size());
    }
}
