package io.github.toberocat.toberocore.item.property;

import io.github.toberocat.toberocore.item.ItemInfo;
import io.github.toberocat.toberocore.item.Priority;
import org.bukkit.Color;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class LeatherArmorColorProperty extends ItemProperty {

    @Override
    public @NotNull Priority priority() {
        return Priority.META;
    }

    @Override
    public boolean apply(@NotNull ItemMeta meta, @NotNull ConfigurationSection cs, @NotNull ItemInfo itemInfo) {
        if (!(meta instanceof LeatherArmorMeta leatherArmorMeta)) {
            return false;
        }

        String values = cs.getString("armor-color");
        if (values == null) return false;

        List<Integer> rgb = new ArrayList<>();

        for (String value : values.split(",")) {
            try {
                int val = Integer.parseInt(value);
                rgb.add(val);
            } catch (NumberFormatException ex) {
                return false;
            }
        }

        if (rgb.size() < 3) {
            return false;
        }

        Color color = Color.fromRGB(rgb.get(0), rgb.get(1), rgb.get(2));
        leatherArmorMeta.setColor(color);
        return true;
    }
}
