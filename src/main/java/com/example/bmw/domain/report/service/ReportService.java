package com.example.bmw.domain.report.service;

import com.example.bmw.domain.post.entity.Post;
import com.example.bmw.domain.post.repository.PostRepository;
import com.example.bmw.domain.report.entity.Report;
import com.example.bmw.domain.report.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final PostRepository postRepository;

    @Transactional
    public void report(int id){
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found"));
        Report report = new Report(post);
        reportRepository.save(report);
    }

    @Transactional
    public void deleteReport(int id){
        reportRepository.deleteById(id);
    }

    @Transactional
    public List<Report> reportList(){
        return reportRepository.findAll();
    }

    @Transactional
    public Report detail(int id){
        return reportRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found"));
    }
}
