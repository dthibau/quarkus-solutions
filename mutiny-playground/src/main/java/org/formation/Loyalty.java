package org.formation;

public record Loyalty(String tier) {
    public static Loyalty unknown() {
        return new Loyalty("UNKNOWN");
    }
}
