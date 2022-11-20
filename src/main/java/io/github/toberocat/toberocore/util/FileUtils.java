package io.github.toberocat.toberocore.util;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public final class FileUtils {

    /**
     * It creates a file in the plugin's data folder if it doesn't exist
     *
     * @param plugin The plugin that the file is for.
     * @param path   The path to the file you want to get.
     * @return A file object
     */
    public static File getDataFile(JavaPlugin plugin, String path) {
        File file = join(plugin.getDataFolder(), path);
        if (!file.exists()) {
            try {
                plugin.getDataFolder().mkdir();
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * Given a file and a path, return a new file that is the result of joining the path to the file.
     *
     * @param file The file to join with.
     * @param path The path to the file or directory.
     * @return A new File object with the path of the first file and the path of the second file.
     */
    public static File join(File file, String path) {
        return new File(file.getPath(), path);
    }

    /**
     * Moves a file or directory to a directory.
     *
     * @param from the file to move
     * @param to   where the file should be moved to
     * @return true if the file was moved successfully
     */
    public static boolean move(@NotNull File from, @NotNull File to) throws IOException {
        boolean success = true;
        File toParent = to.getParentFile();

        if (toParent != null) {
            success = toParent.mkdirs();
        }

        Path f = from.toPath();
        Path t = to.toPath();

        Files.move(f, t);
        return success;
    }
}
