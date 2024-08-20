package com.example.news_snap.domain.scrap.controller;

import com.example.news_snap.domain.login.entity.User;
import com.example.news_snap.domain.scrap.dto.RelatedScrapRequest;
import com.example.news_snap.domain.scrap.dto.RelatedScrapResponse;
import com.example.news_snap.domain.scrap.service.RelatedScrapService;
import com.example.news_snap.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/related")
public class RelatedScrapController {

    private final RelatedScrapService relatedScrapService;

    @Operation(summary = "이어쓸 스크랩 목록을 조회합니다.", description = "이어쓸 스크랩 목록을 조회합니다.")
    @GetMapping("/{scrap_id}")
    public ApiResponse<List<RelatedScrapResponse.PreviewDto>> getRelatedScrapPreviewList(
            @AuthenticationPrincipal User user,
            @PathVariable(name = "scrap_id") Long scrapId
    ) {
        return ApiResponse.onSuccess(relatedScrapService.getRelatedScrapPreviewList(user, scrapId));
    }

    @Operation(summary = "스크랩 연관 관계를 설정합니다.", description = "연관 관계를 설정합니다.\ncurrent = 현재 스크랩, following = 연관 관계로 설정할 스크랩")
    @PutMapping("/{scrap_id}/{following_id}")
    public ApiResponse<RelatedScrapResponse.DetailDto> setRelatedStatus(
            @PathVariable(name = "scrap_id") Long currentScrapId,
            @PathVariable(name = "following_id") Long followingScrapId
    ) {
        return ApiResponse.onSuccess(relatedScrapService.setRelatedStatus(currentScrapId, followingScrapId));
    }
}
