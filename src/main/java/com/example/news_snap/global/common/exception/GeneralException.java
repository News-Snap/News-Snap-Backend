package com.example.news_snap.global.common.exception;

import com.example.news_snap.global.common.code.BaseErrorCode;
import com.example.news_snap.global.common.code.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GeneralException extends RuntimeException {

    private BaseErrorCode code;

    public ErrorReasonDTO getErrorReason() {
        return this.code.getReason();
    }

    public ErrorReasonDTO getErrorReasonHttpStatus() {
        return this.code.getReasonHttpStatus();
    }

}