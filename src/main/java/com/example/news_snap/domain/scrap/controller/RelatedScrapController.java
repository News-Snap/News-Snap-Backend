package com.example.news_snap.domain.scrap.controller;

import com.example.news_snap.domain.scrap.service.RelatedScrapService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/related")
public class RelatedScrapController {
    private final RelatedScrapService relatedScrapService;
}
