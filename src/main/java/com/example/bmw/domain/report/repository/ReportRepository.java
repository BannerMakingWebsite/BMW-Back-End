package com.example.bmw.domain.report.repository;

import com.example.bmw.domain.report.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Integer> {
}
