package io.github.toberocat.toberocore.item.property;

import io.github.toberocat.toberocore.item.Priority;
import io.github.toberocat.toberocore.util.CUtils;
import io.github.toberocat.toberocore.item.ItemInfo;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AmountProperty extends ItemProperty {

    @Override
    public @NotNull Priority priority() {
        return Priority.MATERIAL;
    }

    @Override
    public @NotNull ItemStack apply(@NotNull ItemStack itemStack, @NotNull ConfigurationSection configurationSection, @NotNull ItemInfo itemInfo) {
        for (String a : List.of("amount", "quantity")) {
            if (configurationSection.isSet(a)) {
                String string = configurationSection.getString(a);
                if (string == null) continue;

                String[] parts = string.split(",");
                int min;
                Integer max = null;

                try {
                    min = Integer.parseInt(parts[0]);
                } catch (NumberFormatException ex) {
                    continue;
                }

                if (parts.length >= 2) {
                    try {
                        max = Integer.parseInt(parts[1]);
                    } catch (NumberFormatException ex) {
                        // ignored
                    }
                }

                int amount;
                double multiplier = itemInfo.amountMultiplier();

                if (max == null) {
                    amount = (int) (min * multiplier);
                } else {
                    amount = CUtils.random((int) (min * multiplier), (int) (max * multiplier));
                }

                itemStack.setAmount(amount);
                break;
            }
        }

        return itemStack;
    }
}
