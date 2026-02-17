package org.hyperprojects.hyperclock.manager;

public class StopwatchManager {

    private long startTime;
    private long elapsedTime;
    private boolean running;

    public void start() {
        if (!running) {
            startTime = System.currentTimeMillis();
            running = true;
        }
    }

    public void stop() {
        if (running) {
            elapsedTime += System.currentTimeMillis() - startTime;
            running = false;
        }
    }

    public void reset() {
        startTime = 0;
        elapsedTime = 0;
        running = false;
    }

    public void autoStart() {
        start();
    }

    public boolean isRunning() {
        return running;
    }

    public long getElapsedMillis() {
        if (running) {
            return elapsedTime + (System.currentTimeMillis() - startTime);
        }
        return elapsedTime;
    }

    public String getFormattedTime() {
        long totalSeconds = getElapsedMillis() / 1000;
        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}

