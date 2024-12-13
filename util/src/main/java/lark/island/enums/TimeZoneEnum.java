package lark.island.enums;

import java.time.ZoneId;

public enum TimeZoneEnum {
    HK_TIMEZONE("HK", "Asia/Hong_Kong"),
    UK_TIMEZONE("UK", "Europe/London");

    private final String code;

    private final String zone;

    TimeZoneEnum(String code, String zone) {
        this.code = code;
        this.zone = zone;
    }

    public static ZoneId getZoneId(String code) {
        for (TimeZoneEnum item : TimeZoneEnum.values()) {
            if (item.getCode().contains(code)) {
                return ZoneId.of(item.getZone());
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getZone() {
        return zone;
    }

}