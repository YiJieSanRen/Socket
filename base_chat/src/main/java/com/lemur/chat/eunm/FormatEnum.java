package com.lemur.chat.eunm;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FormatEnum {
    YEAR_MONTH_DAY("yyyy-MM-dd"),
    YEAR_MONTH_DAY_HOUR_MINUTE_SECOND("yyyy-MM-dd HH:mm:ss");

    private final String format;
}
