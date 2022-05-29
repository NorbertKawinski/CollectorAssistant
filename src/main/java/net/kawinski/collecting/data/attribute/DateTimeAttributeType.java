package net.kawinski.collecting.data.attribute;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateTimeAttributeType implements BaseAttributeType<Date> {
    @Override
    public Date parse(final String value) {
        final LocalDateTime dateTime = LocalDateTime.parse(value, DateTimeFormatter.ISO_DATE_TIME);
//        return new SimpleDateFormat().parse(value);
        return new Date(dateTime.toEpochSecond(ZoneOffset.UTC) * 1000);
    }

    @Override
    public String toString(final Date value) {
        return Instant.ofEpochMilli(value.getTime())
                .atZone(ZoneId.of("UTC+0"))
//                .toLocalDate()
                .format(DateTimeFormatter.ISO_DATE_TIME);
    }
}
