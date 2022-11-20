package io.github.toberocat.toberocore.item;

public enum Priority {
    SET(2),
    MATERIAL(1),
    META(0),
    IGNORANT(-1);

    private final int weight;

    Priority(int weight) {
        this.weight = weight;
    }

    public int weight() {
        return weight;
    }
}
