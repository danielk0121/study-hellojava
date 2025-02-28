package dev.dk.hellojava.timeformat;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Slf4j
public class TimeTypeTest {

    /**
     * Instant
     * LocalDate, LocalTime, LocalDateTime
     * ZonedDateTime
     * OffsetDateTime
     *
     * 생성, 서로 변환
     * 문자열 포맷, 파싱
     * 더하기, 빼기
     */

    @Test
    @DisplayName("xxxDateTime 생성")
    void test_xxxDateTime() {
        Instant now = Instant.now();
        ZoneId systemZoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(now, systemZoneId);
        OffsetDateTime offsetDateTime = OffsetDateTime.ofInstant(now, systemZoneId);
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(now, systemZoneId);

//        LocalDateTime localDateTime = LocalDateTime.now();
//        OffsetDateTime offsetDateTime = OffsetDateTime.now();
//        ZonedDateTime zonedDateTime = ZonedDateTime.now();

        //localDateTime = localDate + localTime
        LocalDate localDate1 = localDateTime.toLocalDate();
        LocalTime localTime1 = localDateTime.toLocalTime();
        log.info("localDate1={}", localDate1); //2025-02-28
        log.info("localTime1={}", localTime1); //08:40:53.588447

        //offsetDateTime = localDateTime + offset
        LocalDate localDate2 = offsetDateTime.toLocalDate();
        LocalTime localTime2 = offsetDateTime.toLocalTime();
        ZoneOffset offset2 = offsetDateTime.getOffset();
        log.info("localDate2={}", localDate2);
        log.info("localTime2={}", localTime2);
        log.info("offset2={}", offset2); //+09:00

        //zonedDateTime = localDateTime + offset + zoneId
        LocalDate localDate3 = zonedDateTime.toLocalDate();
        LocalTime localTime3 = zonedDateTime.toLocalTime();
        ZoneOffset offset3 = zonedDateTime.getOffset();
        ZoneId zoneId3 = zonedDateTime.getZone();
        log.info("localDate3={}", localDate3);
        log.info("localTime3={}", localTime3);
        log.info("offset3={}", offset3); //+09:00
        log.info("zoneId3={}", zoneId3); //Asia/Seoul
    }

    @Test
    @DisplayName("xxxDateTime 서로 변환")
    void test_xxxDateTimeConvert() {
        ZoneId zoneId = ZoneId.of("Asia/Seoul");
        ZoneOffset zoneOffset = ZoneOffset.ofHours(9);
        log.info("zoneId={}", zoneId); //Asia/Seoul
        log.info("zoneOffset={}", zoneOffset); //+09:00

        //localDateTime 을 offset, zoned 로 변환
        LocalDateTime localDateTime = LocalDateTime.now(zoneId);
        OffsetDateTime offsetDateTime = localDateTime.atOffset(zoneOffset); //offset 정보 추가
        ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId); //zone 정보 추가
        log.info("localDateTime={}", localDateTime); //2025-02-28T09:18:07.107257
        log.info("offsetDateTime={}", offsetDateTime); //2025-02-28T09:18:07.107257+09:00
        log.info("zonedDateTime={}", zonedDateTime); //2025-02-28T09:18:07.107257+09:00[Asia/Seoul]

        //offset, zoned 를 localDateTime 으로 변환
        //2025-02-28T09:18:07.107257
        log.info("offsetDateTime.toLocalDateTime()={}", offsetDateTime.toLocalDateTime());
        log.info("zonedDateTime.toLocalDateTime()={}", zonedDateTime.toLocalDateTime());
    }

    @Test
    @DisplayName("instant 생성, 변환, 비교 연산")
    void test_instant() {
        Instant now = Instant.now();
        Instant plus = now.plus(10, ChronoUnit.SECONDS);

        Duration between = Duration.between(now, plus);
        long seconds = between.getSeconds();
        log.info("seconds={}", seconds); //10
    }

    @Test
    @DisplayName("localDateTime 연산")
    void test_localDatetimeCalc() {
        //연도 추가
        //월 추가
        //일 추가
        //시간, 분, 초 추가
        //포맷, 파싱

        //생성
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime localDateTime1 = LocalDateTime.of(2020, 1, 10, 15, 35, 55);

        //포맷, 파싱
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatDateTime = now.format(formatter);
        log.info("formatDateTime={}", formatDateTime);
        String stringDateTime = "2020-01-23 15:35:55";
        LocalDateTime parseDateTime = LocalDateTime.parse(stringDateTime, formatter);
        log.info("parseDateTime={}", parseDateTime);

        //더하기, 빼기
        LocalDateTime localDateTime2 = localDateTime1.plusDays(2);
        LocalDateTime localDateTime3 = localDateTime1.minusDays(2);
        log.info("localDateTime1={}", localDateTime1);
        log.info("localDateTime2={}", localDateTime2);
        log.info("localDateTime3={}", localDateTime3);
    }

    @Test
    @DisplayName("DateTimeFormatter timezone")
    void test_time_format_timezone() {
        Instant now = Instant.now();
        ZoneId zone = ZoneId.of("Asia/Seoul");
        ZonedDateTime zonedDateTime = now.atZone(zone);

        //2025-02-28T09:53:30.443126+09:00 <= 설정된 타임존으로 표시됨
        log.info("{}", zonedDateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));

        //2025-02-28T00:53:30.443126Z <= UTC 기준값으로 표시됨, Z 가 붙음
        log.info("{}", zonedDateTime.format(DateTimeFormatter.ISO_INSTANT));

        //파싱
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        String stringDateTime = "2020-01-23T15:35:55+09:00";
        LocalDateTime parse1 = LocalDateTime.parse(stringDateTime, formatter);
        OffsetDateTime parse2 = OffsetDateTime.parse(stringDateTime, formatter);
        ZonedDateTime parse3 = ZonedDateTime.parse(stringDateTime, formatter);
        log.info("parse1={}", parse1); //2020-01-23T15:35:55
        log.info("parse2={}", parse2); //2020-01-23T15:35:55+09:00
        log.info("parse3={}", parse3); //2020-01-23T15:35:55+09:00
    }
}
