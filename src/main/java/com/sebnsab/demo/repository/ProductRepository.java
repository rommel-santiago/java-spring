package com.sebnsab.demo.repository;

import com.sebnsab.demo.model.relationship.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
}
