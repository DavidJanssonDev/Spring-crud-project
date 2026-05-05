package com.example.Spring_crud_project;

import com.example.Spring_crud_project.entity.Customer;
import com.example.Spring_crud_project.repository.CustomerRepository;
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
	public CommandLineRunner demo(CustomerRepository repository) {
		return (args) -> {
			// save a few customers
			repository.save(new Customer("Jack", "Bauer"));
			repository.save(new Customer("Chloe", "O'Brian"));
			repository.save(new Customer("Kim", "Bauer"));
			repository.save(new Customer("David", "Palmer"));
			repository.save(new Customer("Michelle", "Dessler"));

			// fetch all customers
			logger.info("Customers found with finaAll()");
			logger.info("-------------------------------");
			repository.findAll().forEach(customer -> {
				logger.info(customer.toString());
			});
			logger.info("");

			// fetch an individual customer by ID
			Customer customer = repository.findCustomerById(1L);
			logger.info("Customer found with findById(1L)");
			logger.info("-------------------------------");
			logger.info(customer.toString());
			logger.info("");

			// fetch customers by last name
			logger.info("Customer found with findByLastName('Bauer'):");
			logger.info("--------------------------------------------");
			logger.info(Integer.toString(repository.findCustomersByLastName("Bauer").size()));
			logger.info("--------------------------------------------");

		};
	}
}
