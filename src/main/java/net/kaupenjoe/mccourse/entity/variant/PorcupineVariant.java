package net.kaupenjoe.mccourse.entity.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum PorcupineVariant {
    DEFAULT(0),
    GREY(1);

    private static final PorcupineVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.
            comparingInt(PorcupineVariant::getId)).toArray(PorcupineVariant[]::new);
    private final int id;

    PorcupineVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static PorcupineVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
