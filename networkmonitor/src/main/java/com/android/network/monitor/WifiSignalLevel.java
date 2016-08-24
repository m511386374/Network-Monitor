package com.android.network.monitor;

public enum WifiSignalLevel {
    /**
     * Placeholder for unknown bandwidth. This is the initial value and will stay at this value
     * if a bandwidth cannot be accurately found.
     */
    NO_SIGNAL(0, "no signal"),
    /**
     * Bandwidth under 150 kbps.
     */
    POOR(1, "poor"),
    /**
     * Bandwidth between 150 and 550 kbps.
     */
    FAIR(2, "fair"),
    /**
     * Bandwidth between 550 and 2000 kbps.
     */
    GOOD(3, "good"),
    /**
     * EXCELLENT - Bandwidth over 2000 kbps.
     */
    EXCELLENT(4, "excellent");

    public final int level;
    public final String description;

    WifiSignalLevel(final int level, final String description) {
        this.level = level;
        this.description = description;
    }

    public static int getMaxLevel() {
        return EXCELLENT.level;
    }

    /**
     * Gets WifiSignalLevel enum basing on integer value
     *
     * @param level as an integer
     * @return WifiSignalLevel enum
     */
    public static WifiSignalLevel fromLevel(final int level) {
        switch (level) {
            case 0:
                return NO_SIGNAL;
            case 1:
                return POOR;
            case 2:
                return FAIR;
            case 3:
                return GOOD;
            case 4:
                return EXCELLENT;
            default:
                return NO_SIGNAL;
        }
    }

    @Override
    public String toString() {
        return "WifiSignalLevel{" + "level=" + level + ", description='" + description + '\'' + '}';
    }

}
