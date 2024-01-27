package com.jonispatented.castle_exploration.creatures;

public class Statistic {

    private int min, max, current;

    public Statistic(int min, int max, int current) {
        this.min = min;
        this.max = max;
        setCurrent(current);
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public int getCurrent() {
        return current;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setCurrent(int current) {
        this.current = Math.max(Math.min(current, max), min);
    }

    public int add(int delta) {
        int old = current;
        current = Math.max(Math.min(current + delta, max), min);
        return old - current;
    }
}
