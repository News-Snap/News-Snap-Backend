package com.example.news_snap.domain.scrap.controller;

import com.example.news_snap.domain.scrap.service.ScrapService;
import com.example.news_snap.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/scrap")
@Tag(name = "Scrap Domain API")
public class ScrapController {

    private final ScrapService scrapService;

    @Operation(summary = "스크랩 목록 조회", description = "내가 작성한 스크랩 목록을 조회합니다.")
    @GetMapping()
    public ApiResponse<?> getScrapList() {
        return ApiResponse.onSuccess("scrapService.findScrapList()");
    }

    @Operation(summary = "스크랩 조회", description = "내가 작성한 스크랩을 조회합니다.")
    @GetMapping("/{scrap_id}")
    public ApiResponse<?> getScrap(
            @PathVariable(name = "scrap_id") Long scrapId
    ) {
        return ApiResponse.onSuccess("scrapService.findScrap()");
    }

    @Operation(summary = "스크랩 작성", description = "스크랩을 작성합니다.")
    @PostMapping()
    public ApiResponse<?> createScrap() {
        return ApiResponse.onSuccess("scrapService.createScrap()");
    }

}
