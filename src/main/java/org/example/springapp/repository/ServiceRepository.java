package org.example.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.example.springapp.model.Service;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
}
