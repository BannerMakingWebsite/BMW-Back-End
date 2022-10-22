package com.example.bmw.domain.design.repository;

import com.example.bmw.domain.design.entity.Design;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DesignRepository extends JpaRepository<Design, Integer> {
    Optional<Design> findByDesignName(String designName);
}
