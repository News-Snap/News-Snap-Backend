package com.example.news_snap.domain.scrap.service;

import com.example.news_snap.domain.login.entity.User;
import com.example.news_snap.domain.scrap.converter.ScrapConverter;
import com.example.news_snap.domain.scrap.dto.ScrapRequest;
import com.example.news_snap.domain.scrap.dto.ScrapResponse;
import com.example.news_snap.domain.scrap.entity.Keyword;
import com.example.news_snap.domain.scrap.entity.RelatedUrl;
import com.example.news_snap.domain.scrap.entity.Scrap;
import com.example.news_snap.domain.scrap.entity.enums.FinancialTerms;
import com.example.news_snap.domain.scrap.repository.KeywordRepository;
import com.example.news_snap.domain.scrap.repository.RelatedUrlRepository;
import com.example.news_snap.domain.scrap.repository.ScrapRepository;
import com.example.news_snap.global.common.code.status.ErrorStatus;
import com.example.news_snap.global.common.exception.handler.ScrapHandler;
import com.example.news_snap.s3.S3Manager;
import com.example.news_snap.s3.uuid.Uuid;
import com.example.news_snap.s3.uuid.UuidRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ScrapService {

    private final ScrapRepository scrapRepository;
    private final KeywordRepository keywordRepository;
    private final RelatedUrlRepository relatedUrlRepository;
    private final UuidRepository uuidRepository;
    private final S3Manager s3Manager;

    public List<ScrapResponse.PreviewDto> findScrapList(User user, String keyword, LocalDate date) {
        return scrapRepository.findScrapByConditions(user, keyword, date);
    }

    public ScrapResponse.DetailDto findScrap(Long scrapId) {
        Scrap scrap = scrapRepository.findById(scrapId)
                .orElseThrow(() -> new ScrapHandler(ErrorStatus._NOT_FOUND_SCRAP));

        List<FinancialTerms> terms = scrap.getKeywordList().stream()
                .map(Keyword::getTerm).toList();
        List<String> relatedUrlStringList = scrap.getRelatedUrlList().stream()
                .map(RelatedUrl::getRelatedUrl).toList();

        return ScrapResponse.DetailDto.builder()
                .title(scrap.getTitle())
                .content(scrap.getContent())
                .articleUrl(scrap.getArticleUrl())
                .keywords(terms)
                .modifiedAt(scrap.getUpdatedAt())
                .fileUrl(scrap.getFileUrl())
                .relatedUrlList(relatedUrlStringList)
                .build();
    }

    public ScrapResponse.IdDto createScrap(User user, ScrapRequest.ContentDto request) {

        Scrap savedScrap = scrapRepository.save(Scrap.builder()
                .title(request.title())
                .content(request.content())
                .articleUrl(request.articleUrl())
                .articleCreatedAt(request.articleCreatedAt())
                .user(user)
                .build());

        for (String url : request.relatedUrlList()) {
            RelatedUrl relatedUrl = ScrapConverter.toRelatedUrl(url, savedScrap);
            relatedUrlRepository.save(relatedUrl);
        }

        for (FinancialTerms term : request.keywords()) {
            Keyword keyword = ScrapConverter.toKeyword(term, savedScrap);
            keywordRepository.save(keyword);
        }

        return ScrapResponse.IdDto.builder()
                .scrapId(savedScrap.getScrapId())
                .build();
    }

    public String uploadScrapFile(Long scrapId, MultipartFile file) {
        String url = null;
        Scrap scrap = scrapRepository.findById(scrapId)
                .orElseThrow(() -> new ScrapHandler(ErrorStatus._NOT_FOUND_SCRAP));

        if (file != null && !file.isEmpty()) {
            String uuid = UUID.randomUUID().toString();
            Uuid savedUuid = uuidRepository.save(Uuid.builder()
                    .uuid(uuid).build());

            url = s3Manager.uploadFile(s3Manager.generateImage(savedUuid), file);
            scrap.uploadFile(url);
            scrapRepository.save(scrap);
            return "파일 첨부 완료";
        }
        return "첨부된 파일이 없습니다.";
    }

    public String updateScrap(ScrapRequest.ContentDto request, Long scrapId) {
        Scrap scrap = scrapRepository.findById(scrapId)
                .orElseThrow(() -> new ScrapHandler(ErrorStatus._NOT_FOUND_SCRAP));

        scrap.update(request);
        scrapRepository.save(scrap);

        return "수정 되었습니다.";
    }

    public String deleteScrap(Long scrapId) {
        Scrap scrap = scrapRepository.findById(scrapId)
                .orElseThrow(() -> new ScrapHandler(ErrorStatus._NOT_FOUND_SCRAP));

        scrapRepository.delete(scrap);

        return "삭제 되었습니다.";
    }

    public List<ScrapResponse.KeywordDto> getTopKeywords(User user) {
        return scrapRepository.findTopKeywordByUser(user);
    }
}
