package io.github.toberocat.toberocore.item;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record ItemResponse(@NotNull ItemStack item, @Nullable String clickActionsPath) {

}
