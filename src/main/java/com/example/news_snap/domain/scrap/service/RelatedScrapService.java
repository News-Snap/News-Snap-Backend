package com.example.news_snap.domain.scrap.service;

import com.example.news_snap.domain.scrap.repository.RelatedScrapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RelatedScrapService {

    private final RelatedScrapRepository relatedScrapRepository;

}
