package com.example.news_snap.global.common.exception.handler;

import com.example.news_snap.global.common.code.BaseErrorCode;
import com.example.news_snap.global.common.exception.GeneralException;

public class KeywordHandler extends GeneralException {
    public KeywordHandler(BaseErrorCode code) {
        super(code);
    }
}
