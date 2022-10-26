package com.example.bmw.domain.design.controller;

import com.example.bmw.domain.design.controller.dto.reqeust.DesignNameRequest;
import com.example.bmw.domain.design.entity.Design;
import com.example.bmw.domain.design.service.DesignService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class DesignController {
    private final DesignService designService;

    @PostMapping("/design")
    public Design save(@ModelAttribute DesignNameRequest request) throws IOException {
        return designService.save(request.getFile(), request.getDesignName());
    }

    @DeleteMapping("/design")
    public void delete(@RequestBody DesignNameRequest request){
        designService.delete(request.getDesignName());
    }
}
