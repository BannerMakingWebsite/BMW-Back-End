package com.example.bmw.domain.report.controller;

import com.example.bmw.domain.report.controller.dto.response.ReportResponse;
import com.example.bmw.domain.report.entity.Report;
import com.example.bmw.domain.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @PostMapping("/report/{postId}")
    public void report(@PathVariable int postId){
        reportService.report(postId);
    }

    @DeleteMapping("/report/{reportId}")
    public void delete(@PathVariable int reportId){
        reportService.deleteReport(reportId);
    }

    @GetMapping("/report/list")
    public List<ReportResponse> list(){
        return reportService.reportList();
    }

    @GetMapping("/report/{reportId}")
    public ReportResponse detail(@PathVariable int reportId){
        return reportService.detail(reportId);
    }
}
