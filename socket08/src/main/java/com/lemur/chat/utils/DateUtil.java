package com.lemur.chat.utils;

import com.lemur.chat.eunm.FormatEnum;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class DateUtil {

    public static <T, S> T parseDate(S date) throws ParseException {
        return parseDate(date, null);
    }

    public static <T, S> T parseDate(S date, FormatEnum format) throws ParseException {

        SimpleDateFormat dateFormat;

        if (Objects.nonNull(format)) {
            // 如果是时间类型，需要转换成字符串类型
            // 如果是字符串类型，需要转换成时间类型
            if (date instanceof Date) {
                dateFormat = new SimpleDateFormat(format.getFormat());
                return (T) dateFormat.format(date);
            } else if (date instanceof String) {
                dateFormat = new SimpleDateFormat(format.getFormat());
                return (T) dateFormat.parse((String) date);
            } else {
                throw new RuntimeException("数据类型异常！");
            }
        }

        // 如果是时间类型，需要转换成字符串类型
        // 如果是字符串类型，需要转换成时间类型
        if (date instanceof Date) {
            dateFormat = new SimpleDateFormat(FormatEnum.YEAR_MONTH_DAY_HOUR_MINUTE_SECOND.getFormat());
            String dateStr = dateFormat.format(date);
            int index = dateStr.indexOf(" 00:00:00");
            return (T) (index >= 0 ? dateStr.substring(0, index) : dateStr);
        } else if (date instanceof String) {
            int index = ((String) date).indexOf(" ");
            if (index >= 0) {
                dateFormat = new SimpleDateFormat(FormatEnum.YEAR_MONTH_DAY_HOUR_MINUTE_SECOND.getFormat());
            } else {
                dateFormat = new SimpleDateFormat(FormatEnum.YEAR_MONTH_DAY.getFormat());
            }
            return (T) dateFormat.parse((String) date);
        } else {
            throw new RuntimeException("数据类型异常！");
        }

    }
}
