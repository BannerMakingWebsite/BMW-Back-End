package com.example.bmw.domain.report.service;

import com.example.bmw.domain.post.controller.dto.response.PostResponse;
import com.example.bmw.domain.post.entity.Post;
import com.example.bmw.domain.post.repository.PostRepository;
import com.example.bmw.domain.report.controller.dto.response.ReportResponse;
import com.example.bmw.domain.report.entity.Report;
import com.example.bmw.domain.report.repository.ReportRepository;
import com.example.bmw.global.error.ErrorCode;
import com.example.bmw.global.error.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final PostRepository postRepository;

    @Transactional
    public void report(int id){
        Post post = postRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.TEMPLATE_NOT_FOUND));
        Report report = new Report(post);
        reportRepository.save(report);
    }

    @Transactional
    public void deleteReport(int id){
        reportRepository.deleteById(id);
    }

    @Transactional
    public List<ReportResponse> reportList(){
        List<Report> report = reportRepository.findAll();
        return report.stream().map(r -> new ReportResponse(r.getId(), r.getPost())).collect(Collectors.toList());
    }

    @Transactional
    public ReportResponse detail(int id){
        Report report = reportRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.REPORT_NOT_FOUND));
        return ReportResponse.builder()
                .id(report.getId())
                .post(report.getPost())
                .build();
    }
}
