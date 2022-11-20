package io.github.toberocat.toberocore.util;

import org.bukkit.potion.PotionEffectType;

public final class PotionUtils {

    public static int getDuration(PotionEffectType effect, boolean extended, boolean upgraded) {
        if (effect == PotionEffectType.REGENERATION) {
            if (upgraded) {
                return 22;
            }

            return extended ? 90 : 45;
        }
        if (effect == PotionEffectType.POISON) {
            if (upgraded) {
                return 21;
            }

            return extended ? 90 : 45;
        }
        if (
                effect == PotionEffectType.INCREASE_DAMAGE
                        || effect == PotionEffectType.SPEED
                        || effect == PotionEffectType.JUMP
        ) {
            if (upgraded) {
                return 90;
            }

            return extended ? 480 : 180;
        }
        if (
                effect == PotionEffectType.INVISIBILITY
                        || effect == PotionEffectType.NIGHT_VISION
                        || effect == PotionEffectType.FIRE_RESISTANCE
                        || effect == PotionEffectType.WATER_BREATHING
        ) {
            return extended ? 480 : 180;
        }
        if (
                effect == PotionEffectType.SLOW
                        || effect == PotionEffectType.WEAKNESS
        ) {
            return extended ? 240 : 90;
        }

        return 0;
    }
}
