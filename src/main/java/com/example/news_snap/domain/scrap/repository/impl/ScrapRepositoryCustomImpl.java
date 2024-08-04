package com.example.news_snap.domain.scrap.repository.impl;

import com.example.news_snap.domain.login.entity.QUser;
import com.example.news_snap.domain.login.entity.User;
import com.example.news_snap.domain.scrap.dto.ScrapResponse;
import com.example.news_snap.domain.scrap.entity.QKeyword;
import com.example.news_snap.domain.scrap.entity.QScrap;
import com.example.news_snap.domain.scrap.entity.enums.FinancialTerms;
import com.example.news_snap.domain.scrap.repository.ScrapRepositoryCustom;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ScrapRepositoryCustomImpl implements ScrapRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ScrapResponse.PreviewDto> findScrapByConditions(User requestUser, String requestKeyword, LocalDate date) {
        QScrap scrap = QScrap.scrap;
        QKeyword keyword = QKeyword.keyword;
        QUser user = QUser.user;
        BooleanBuilder builder = new BooleanBuilder();

        if (requestKeyword != null && !requestKeyword.isEmpty()) {
            builder.and(scrap.keywordList.any().term.eq(FinancialTerms.valueOf(requestKeyword)));
        }

        if (date != null) {
            builder.and(scrap.createdAt.eq(date.atStartOfDay()));
        }

        return queryFactory
                .select(Projections.constructor(ScrapResponse.PreviewDto.class,
                        scrap.scrapId,
                        scrap.title,
                        JPAExpressions.select(keyword.term)
                                .from(keyword)
                                .where(keyword.scrap.eq(scrap))
                                .distinct(), // 키워드를 중복 없이 가져오기
                        scrap.updatedAt))
                .from(scrap)
                .join(user).on(scrap.user.eq(requestUser))
                .where(builder)
                .fetch();
    }
}

