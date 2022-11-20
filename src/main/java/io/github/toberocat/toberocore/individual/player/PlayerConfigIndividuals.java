package io.github.toberocat.toberocore.individual.player;

import io.github.toberocat.toberocore.individual.AbstractIndividuals;
import io.github.toberocat.toberocore.util.YamlLoader;
import io.github.toberocat.toberocore.task.Task;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@SuppressWarnings("unused")
public class PlayerConfigIndividuals extends AbstractIndividuals<UUID, YamlConfiguration> {

    private final File directory;
    private final JavaPlugin javaPlugin;

    public PlayerConfigIndividuals(@NotNull File directory, @NotNull JavaPlugin javaPlugin) {
        this.directory = directory;
        this.javaPlugin = javaPlugin;
    }

    private @NotNull File file(@NotNull UUID uuid) {
        return new File(directory, uuid + ".yml");
    }

    @Override
    protected @NotNull Task<YamlConfiguration> load(@NotNull UUID uuid) {
        return new Task<>(() -> new YamlLoader(file(uuid), javaPlugin).load().yamlConfiguration());
    }

    @Override
    protected @NotNull Task<?> unsafeUnload(@NotNull UUID uuid) {
        YamlConfiguration yamlConfiguration = remove(uuid);
        File file = file(uuid);

        return new Task<>(() -> {
            try {
                yamlConfiguration.save(file);
            } catch (IOException e) {
                javaPlugin.getLogger().severe("Could not save YamlConfiguration: " + uuid);
                e.printStackTrace();
            }
        });
    }
}
