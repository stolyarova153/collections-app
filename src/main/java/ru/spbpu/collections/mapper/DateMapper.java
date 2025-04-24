package ru.spbpu.collections.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.sql.Date;
import java.sql.Timestamp;

import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Optional.ofNullable;
import static ru.spbpu.collections.utils.DateTimeUtils.DATE_TIME_FORMAT_SHORT;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface DateMapper {

    default Long asLong(final Date date) {
        return ofNullable(date).map(Date::getTime).orElse(null);
    }

    default String timestampToDateFormat(final Timestamp timestamp) {

        if (timestamp == null) {
            return null;
        }

        return timestamp.toLocalDateTime().format(ofPattern(DATE_TIME_FORMAT_SHORT));
    }

    default Timestamp toTimestamp(final Long millis) {
        return ofNullable(millis).map(Timestamp::new).orElse(null);
    }
}
