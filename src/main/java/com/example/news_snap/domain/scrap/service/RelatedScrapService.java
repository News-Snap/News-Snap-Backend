package com.example.news_snap.domain.scrap.service;

import com.example.news_snap.domain.login.entity.User;
import com.example.news_snap.domain.scrap.dto.RelatedScrapResponse;
import com.example.news_snap.domain.scrap.entity.RelatedScrap;
import com.example.news_snap.domain.scrap.entity.Scrap;
import com.example.news_snap.domain.scrap.repository.RelatedScrapRepository;
import com.example.news_snap.domain.scrap.repository.ScrapRepository;
import com.example.news_snap.global.common.code.status.ErrorStatus;
import com.example.news_snap.global.common.exception.handler.ScrapHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RelatedScrapService {

    private final RelatedScrapRepository relatedScrapRepository;
    private final ScrapRepository scrapRepository;

    public List<RelatedScrapResponse.PreviewDto> getRelatedScrapPreviewList(User user, Long scrapId) {
        Scrap current = scrapRepository.findById(scrapId)
                .orElseThrow(() -> new ScrapHandler(ErrorStatus._NOT_FOUND_SCRAP));
        List<Scrap> scrapList = scrapRepository.findAllByUserExceptScrap(user, current);

        List<RelatedScrapResponse.PreviewDto> previewDtoList = new ArrayList<>();

        for (Scrap scrap : scrapList) {
            previewDtoList.add(RelatedScrapResponse.PreviewDto.builder()
                    .title(scrap.getTitle())
                    .termList(scrap.getTermList())
                    .updatedAt(scrap.getUpdatedAt())
                    .build());
        }

        return previewDtoList;
    }

    public RelatedScrapResponse.DetailDto setRelatedStatus(Long currentScrapId, Long followingScrapId) {
        Scrap current = scrapRepository.findById(currentScrapId)
                .orElseThrow(() -> new ScrapHandler(ErrorStatus._NOT_FOUND_SCRAP));

        Scrap following = scrapRepository.findById(followingScrapId)
                .orElseThrow(() -> new ScrapHandler(ErrorStatus._NOT_FOUND_SCRAP));

        RelatedScrap relatedScrap = RelatedScrap.builder()
                .followerScrap(current)
                .followingScrap(following)
                .build();

        RelatedScrap savedRelation = relatedScrapRepository.save(relatedScrap);

        return RelatedScrapResponse.DetailDto.builder()
                .relateScrapId(savedRelation.getRelatedScrapId())
                .relatedUrl(following.getArticleUrl())
                .scrapId(current.getScrapId())
                .followingScrapId(following.getScrapId())
                .build();
    }
}
