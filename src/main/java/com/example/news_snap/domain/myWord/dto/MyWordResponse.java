package com.example.news_snap.domain.myWord.dto;


import lombok.Builder;




public record MyWordResponse() {

    @Builder
    public record MyWordDTO(String word){
    }

    @Builder
    public record addResultDTO(String word){


    }

}
