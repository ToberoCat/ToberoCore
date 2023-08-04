package io.github.toberocat.toberocore.individual.player;

import io.github.toberocat.toberocore.task.Task;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlayerDecimalIndividuals extends PlayerIndividuals<BigDecimal> {

    public static final BigDecimal START_BALANCE = BigDecimal.ZERO;

    private final File directory;
    private final Logger logger;

    public PlayerDecimalIndividuals(@NotNull File directory, @NotNull JavaPlugin javaPlugin) {
        super(javaPlugin);
        this.directory = directory;
        this.logger = javaPlugin.getLogger();

        if (!directory.exists() && !directory.mkdirs()) {
            logger.warning("Could not create directories for file " + directory.getName());
        }
    }

    private @NotNull File file(@NotNull UUID uuid) {
        return new File(directory, uuid.toString());
    }


    @Override
    protected @NotNull Task<BigDecimal> read(@NotNull UUID uuid) {
        return new Task<>(() -> {
            File file = file(uuid);

            if (file.exists()) {
                try {
                    return new BigDecimal(Files.readString(file.toPath()));
                } catch (IOException | NumberFormatException e) {
                    logger.severe("Could not read BigDecimal " +
                            uuid + " from file " + file.getPath());
                    e.printStackTrace();

                    if (file.delete()) {
                        logger.severe("Deleted " + file.getPath() + " to prevent future crashes");
                    }
                }
            }

            return START_BALANCE;
        });
    }

    @Override
    protected void write(@NotNull UUID uuid) {
        BigDecimal bigDecimal = remove(uuid);
        File file = file(uuid);

        new Task<>(() -> {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(bigDecimal.toPlainString());
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Could not save BigDecimal " +
                        uuid + " to file " + file.getPath());
                e.printStackTrace();

                if (file.delete())
                    logger.info("Deleted " + file.getPath() + " to prevent future crashes");
            }
        });
    }
}
