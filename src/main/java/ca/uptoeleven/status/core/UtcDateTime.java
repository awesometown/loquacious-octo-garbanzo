package ca.uptoeleven.status.core;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class UtcDateTime {
    public static LocalDateTime nowUtc() {
        return LocalDateTime.now(ZoneOffset.UTC);
    }
}
