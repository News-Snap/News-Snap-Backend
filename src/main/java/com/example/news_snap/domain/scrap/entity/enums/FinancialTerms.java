package com.example.news_snap.domain.scrap.entity.enums;

public enum FinancialTerms {
    INTEREST_RATE("금리"),
    BIG_TECH("빅테크"),
    STARTUP("스타트업"),
    INTERNATIONAL_EXCHANGE_RATE("국제 환율"),
    BLOCK_CHAIN("블록체인");

    private final String description;

    FinancialTerms(String description) {
        this.description = description;
    }

    public String getKrDescription() {
        return description;
    }
}
