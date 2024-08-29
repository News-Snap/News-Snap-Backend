package com.example.news_snap.domain.scrap.entity.enums;

public enum FinancialTerms {
    INTEREST_RATE("금리"),
    BIG_TECH("빅테크"),
    STARTUP("스타트업"),
    INTERNATIONAL_EXCHANGE_RATE("국제 환율"),
    BLOCK_CHAIN("블록체인"),
    FINANCE("금융"),
    CERTIFICATE("증권"),
    ECONOMIC_POLICY("경제정책"),
    DOMESTIC("국내"),
    GLOBAL("국제"),
    REAL_ESTATE("부동산"),
    SEMICONDUCTOR("반도체"),
    AUTOMOBILE("자동차"),
    FOOD("식료품"),
    CONSTRUCTION("건설");

    private final String description;

    FinancialTerms(String description) {
        this.description = description;
    }

    public String getKrDescription() {
        return description;
    }
}
