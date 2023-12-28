package com.sippbox.beans;

public enum Category {
    OPTIMIZATION,
    PRODUCTIVITY,
    FEATURES,
    SHADERS;

    @Override
    public String toString() {
        switch (this) {
            case OPTIMIZATION:
                return "Optimization";
            case PRODUCTIVITY:
                return "Productivity";
            case FEATURES:
                return "Features";
            case SHADERS:
                return "Shaders";
            default:
                return "Unknown";
        }
    }
}
