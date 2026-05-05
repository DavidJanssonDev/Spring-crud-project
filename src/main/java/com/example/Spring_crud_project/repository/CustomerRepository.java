package com.example.Spring_crud_project.repository;

import com.example.Spring_crud_project.entity.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    List<Customer> findCustomersByLastName(String firstName);

    Customer findCustomerById(long id);
}
