package com.example.news_snap.domain.scrap.repository.impl;

import com.example.news_snap.domain.login.entity.QUser;
import com.example.news_snap.domain.login.entity.User;
import com.example.news_snap.domain.scrap.dto.ScrapResponse;
import com.example.news_snap.domain.scrap.entity.QKeyword;
import com.example.news_snap.domain.scrap.entity.QScrap;
import com.example.news_snap.domain.scrap.entity.enums.FinancialTerms;
import com.example.news_snap.domain.scrap.repository.ScrapRepositoryCustom;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
            LocalDateTime startOfDay = date.atStartOfDay();
            LocalDateTime endOfDay = date.atTime(23, 59, 59, 999999999);

            builder.and(scrap.createdAt.between(startOfDay, endOfDay));
        }

        List<Tuple> results = queryFactory
                .selectDistinct(scrap.scrapId, scrap.title, scrap.updatedAt)
                .from(scrap)
                .join(user).on(scrap.user.eq(requestUser))
                .where(builder)
                .orderBy(scrap.updatedAt.desc())
                .fetch();

        return results.stream()
                .map(tuple -> {
                    Long scrapId = tuple.get(scrap.scrapId);
                    List<String> keywords = queryFactory
                            .select(keyword.term.stringValue())
                            .from(keyword)
                            .where(keyword.scrap.scrapId.eq(scrapId))
                            .fetch();

                    return new ScrapResponse.PreviewDto(
                            scrapId,
                            tuple.get(scrap.title),
                            keywords,
                            tuple.get(scrap.updatedAt)
                    );
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<ScrapResponse.KeywordDto> findTopKeywordByUser(User requestUser) {
        QScrap scrap = QScrap.scrap;
        QKeyword keyword = QKeyword.keyword;
        QUser user = QUser.user;

        return queryFactory
                .select(Projections.constructor(ScrapResponse.KeywordDto.class,
                        keyword.term.stringValue().as("keyword")))
                .from(keyword)
                .join(keyword.scrap, scrap)
                .join(scrap.user, user)
                .where(user.eq(requestUser))
                .groupBy(keyword.term)
                .orderBy(keyword.term.count().desc())
                .limit(4)
                .fetch();
    }
}

