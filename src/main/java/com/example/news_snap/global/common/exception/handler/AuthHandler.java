package com.example.news_snap.global.common.exception.handler;

import com.example.news_snap.global.common.code.BaseErrorCode;
import com.example.news_snap.global.common.exception.GeneralException;

public class AuthHandler extends GeneralException {
    public AuthHandler(BaseErrorCode code) {
        super(code);
    }
}
