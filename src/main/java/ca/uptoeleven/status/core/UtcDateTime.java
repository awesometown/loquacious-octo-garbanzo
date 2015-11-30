package ca.uptoeleven.status.core;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class UtcDateTime {

	public static LocalDateTime nowUtc() {
        return LocalDateTime.now(ZoneOffset.UTC);
    }

	public static ZonedDateTime asUtc(LocalDateTime dateTime) {
		return ZonedDateTime.of(dateTime, ZoneOffset.UTC);
	}

}
