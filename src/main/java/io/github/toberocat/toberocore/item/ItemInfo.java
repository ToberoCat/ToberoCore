package io.github.toberocat.toberocore.item;

import org.bukkit.NamespacedKey;
import org.bukkit.Warning;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public final class ItemInfo implements Cloneable {

    private final Map<String, String> placeholders = new HashMap<>();
    private final Map<NamespacedKey, Integer> persistentInts = new HashMap<>();
    private final Map<NamespacedKey, String> persistentStrings = new HashMap<>();
    private final List<String> tags = new ArrayList<>();
    private double amountMultiplier = 1.0;

    private EntityType entityType;
    private UUID uuid;

    /*
        Subject for removal.
     */
    @Deprecated
    @Warning(reason = "The constructor ItemInfo() should be used instead")
    public static @NotNull ItemInfo def() {
        return new ItemInfo();
    }

    public @Nullable EntityType entityType() {
        return entityType;
    }

    public @NotNull ItemInfo entityType(@Nullable EntityType entityType) {
        this.entityType = entityType;
        return this;
    }

    public @Nullable UUID uuid() {
        return uuid;
    }

    public @NotNull ItemInfo uuid(@Nullable UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public @NotNull Map<String, String> placeholders() {
        return placeholders;
    }

    public @NotNull ItemInfo placeholder(@Nullable String what, @Nullable String with) {
        if (what != null && with != null) {
            placeholders.put(what, with);
        }

        return this;
    }

    public @NotNull ItemInfo tag(@NotNull String tag) {
        tags.add(tag);
        return this;
    }

    public boolean hasTag(@NotNull String tag) {
        return tags.contains(tag);
    }

    public @NotNull Map<NamespacedKey, Integer> persistentInts() {
        return persistentInts;
    }

    public @NotNull ItemInfo persistentInt(@NotNull NamespacedKey namespacedKey, int integer) {
        persistentInts.put(namespacedKey, integer);
        return this;
    }

    public @NotNull Map<NamespacedKey, String> persistentStrings() {
        return persistentStrings;
    }

    public @NotNull ItemInfo persistentString(@NotNull NamespacedKey namespacedKey, @NotNull String string) {
        persistentStrings.put(namespacedKey, string);
        return this;
    }

    public @NotNull ItemInfo multiplyAmount(double amount) {
        amountMultiplier *= amount;
        return this;
    }

    public double amountMultiplier() {
        return amountMultiplier;
    }

    @Override
    public ItemInfo clone() {
        try {
            return (ItemInfo) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
