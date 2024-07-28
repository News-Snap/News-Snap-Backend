package com.example.news_snap.domain.myWord.dto;

import com.example.news_snap.domain.myWord.entity.MyWord;
import lombok.Builder;

import java.util.List;


public record MyWordResponse() {

    @Builder
    public record MyWordListDTO(
            List<MyWord> myWordList,
            Integer listSize){

    }


    @Builder
    public record addResultDTO(String word){


    }
}
