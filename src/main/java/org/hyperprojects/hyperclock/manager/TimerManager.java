package org.hyperprojects.hyperclock.manager;

public class TimerManager {

    private long durationMillis;
    private long endTime;
    private boolean running;

    public void set(long durationMillis) {
        this.durationMillis = durationMillis;
        this.endTime = 0;
        this.running = false;
    }

    public void start() {
        if (!running && durationMillis > 0) {
            endTime = System.currentTimeMillis() + durationMillis;
            running = true;
        }
    }

    public void stop() {
        if (running) {
            durationMillis = getRemainingMillis();
            running = false;
        }
    }

    public boolean isRunning() {
        return running;
    }

    public long getRemainingMillis() {
        if (!running) {
            return Math.max(durationMillis, 0);
        }

        long remaining = endTime - System.currentTimeMillis();
        if (remaining <= 0) {
            running = false;
            durationMillis = 0;
            return 0;
        }

        return remaining;
    }

    public boolean isFinished() {
        return getRemainingMillis() == 0;
    }

    public String getFormattedTime() {
        long totalSeconds = getRemainingMillis() / 1000;
        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
