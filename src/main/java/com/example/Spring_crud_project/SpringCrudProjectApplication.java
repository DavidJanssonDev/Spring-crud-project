package com.example.Spring_crud_project;

import com.example.Spring_crud_project.entity.Author;
import com.example.Spring_crud_project.entity.Book;
import com.example.Spring_crud_project.repository.AuthorRepository;
import com.example.Spring_crud_project.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class SpringCrudProjectApplication {

	private static final Logger logger = LoggerFactory.getLogger(SpringCrudProjectApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringCrudProjectApplication.class, args);
	}


	@Bean
	public CommandLineRunner demo(AuthorRepository authorRepository, BookRepository bookRepository) {
		return (args) -> {
			// ── Create authors ──────────────────────────────────────
			Author tolkien = new Author();
			tolkien.setFirstName("J.R.R");
			tolkien.setLastName("Tolkien");
			authorRepository.save(tolkien);

			Author orwell = new Author();
			orwell.setFirstName("George");
			orwell.setLastName("Orwell");
			authorRepository.save(orwell);

			Author rowling = new Author();
			rowling.setFirstName("J.K");
			rowling.setLastName("Rowling");
			authorRepository.save(rowling);

			// ── Create books and link to authors ────────────────────
			Book fellowship = new Book();
			fellowship.setTitle("The Fellowship of the Ring");
			fellowship.setIsbn("978-0-261-10235-4");
			fellowship.setAuthor(tolkien);
			bookRepository.save(fellowship);

			Book twoTowers = new Book();
			twoTowers.setTitle("The Two Towers");
			twoTowers.setIsbn("978-0-261-10236-1");
			twoTowers.setAuthor(tolkien);
			bookRepository.save(twoTowers);

			Book nineteenEightyFour = new Book();
			nineteenEightyFour.setTitle("1984");
			nineteenEightyFour.setIsbn("978-0-451-52493-5");
			nineteenEightyFour.setAuthor(orwell);
			bookRepository.save(nineteenEightyFour);

			Book philosophersStone = new Book();
			philosophersStone.setTitle("Harry Potter and the Philosophers Stone");
			philosophersStone.setIsbn("978-0-7475-3269-9");
			philosophersStone.setAuthor(rowling);
			bookRepository.save(philosophersStone);

			// ── Log what was saved ──────────────────────────────────
			logger.info("=== Authors saved ===");
			authorRepository.findAll().forEach(a ->
					logger.info("Author: {} {}", a.getFirstName(), a.getLastName())
			);

			logger.info("=== Books saved ===");
			bookRepository.findAll().forEach(b ->
					logger.info("Book: '{}' by {} {} | ISBN: {}",
							b.getTitle(),
							b.getAuthor().getFirstName(),
							b.getAuthor().getLastName(),
							b.getIsbn()
					)
			);
		};
	}
}
