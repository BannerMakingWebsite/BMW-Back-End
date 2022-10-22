package com.example.bmw.domain.report.controller;

import com.example.bmw.domain.report.entity.Report;
import com.example.bmw.domain.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @PostMapping("template/report/{id}")
    public void report(@PathVariable int id){
        reportService.report(id);
    }

    @DeleteMapping("/report/{id}")
    public void delete(@PathVariable int id){
        reportService.deleteReport(id);
    }

    @GetMapping("/report/list")
    public List<Report> list(){
        return reportService.reportList();
    }

    @GetMapping("/report/{id}")
    public Report detail(@PathVariable int id){
        return reportService.detail(id);
    }
}
