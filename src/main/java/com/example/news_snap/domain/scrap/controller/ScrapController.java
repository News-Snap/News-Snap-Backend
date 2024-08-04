package com.example.news_snap.domain.scrap.controller;

import com.example.news_snap.domain.login.entity.User;
import com.example.news_snap.domain.scrap.dto.ScrapRequest;
import com.example.news_snap.domain.scrap.dto.ScrapResponse;
import com.example.news_snap.domain.scrap.service.ScrapService;
import com.example.news_snap.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/scrap")
@Tag(name = "Scrap Domain API")
public class ScrapController {

    private final ScrapService scrapService;

    @Operation(summary = "스크랩 목록 조회", description = "내가 작성한 스크랩 목록을 조회합니다.")
    @GetMapping()
    public ApiResponse<List<ScrapResponse.PreviewDto>> getScrapList(
            @AuthenticationPrincipal User user,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "date", required = false) LocalDate date
            ) {
        return ApiResponse.onSuccess(scrapService.findScrapList(user, keyword, date));
    }

    @Operation(summary = "스크랩 상세 조회", description = "내가 작성한 스크랩을 조회합니다.")
    @GetMapping("/{scrap_id}")
    public ApiResponse<ScrapResponse.DetailDto> getScrap(
            @PathVariable(name = "scrap_id") Long scrapId
    ) {
        return ApiResponse.onSuccess(scrapService.findScrap(scrapId));
    }

    @Operation(summary = "스크랩 작성: 내용 작성", description = "스크랩의 내용을 작성합니다.")
    @PostMapping()
    public ApiResponse<ScrapResponse.IdDto> createScrap(
            @AuthenticationPrincipal User user,
            @RequestBody ScrapRequest.ContentDto request
            ) {
        return ApiResponse.onSuccess(scrapService.createScrap(user, request));
    }

    @Operation(summary = "스크랩 작성: 첨부 파일 추가", description = "스크랩 작성 시 파일을 첨부합니다.")
    @PostMapping(value = "/{scrap_id}", consumes = "multipart/form-data")
    public ApiResponse<?> uploadScrapFile(
            @PathVariable(name = "scrap_id") Long scrapId,
            @RequestParam("file")MultipartFile file
            ) {
        return ApiResponse.onSuccess(scrapService.uploadScrapFile(scrapId, file));
    }

    @Operation(summary = "스크랩 수정", description = "스크랩을 수정합니다.")
    @PutMapping("/{scrap_id}")
    public ApiResponse<?> updateScrap(
            @RequestBody ScrapRequest.ContentDto request,
            @PathVariable(name = "scrap_id") Long scrapId
    ) {
        return ApiResponse.onSuccess(scrapService.updateScrap(request, scrapId));
    }

    @Operation(summary = "스크랩 삭제", description = "스크랩을 삭제합니다.")
    @DeleteMapping("/{scrap_id}")
    public ApiResponse<?> deleteScrap(
            @PathVariable(name = "scrap_id") Long scrapId
    ) {
        return ApiResponse.onSuccess(scrapService.deleteScrap(scrapId));
    }

}
