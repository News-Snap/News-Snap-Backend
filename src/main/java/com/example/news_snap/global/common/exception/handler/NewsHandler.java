package com.example.news_snap.global.common.exception.handler;

import com.example.news_snap.global.common.code.BaseErrorCode;
import com.example.news_snap.global.common.exception.GeneralException;

public class NewsHandler extends GeneralException {
    public NewsHandler(BaseErrorCode code) {
        super(code);
    }
}
