package com.google.common.util.concurrent;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class MonitorWaitForTest {
    @Test
    public void testWaitDuration() {
        Monitor monitor = new Monitor();
        assertThrows(IllegalMonitorStateException.class, () -> monitor.waitFor(monitor.newGuard(() -> true), Duration.ZERO));
    }
}
