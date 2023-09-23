package fr.erased.clans.utils;

import fr.erased.clans.ErasedClans;

import java.io.File;

public class FileUtils {

    private final ErasedClans main;

    public FileUtils(ErasedClans main) {
        this.main = main;
    }

    public void createFile(String folder, String filename) {
        File file = new File(main.getDataFolder() + File.separator + folder + File.separator + filename + ".yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void removeFile(String folder, String filename) {
        File file = new File(main.getDataFolder() + File.separator + folder + File.separator + filename + ".yml");

        if (file.exists()) {
            file.delete();
        }
    }

    public File getFile(String folder, String filename) {
        return new File(main.getDataFolder() + File.separator + folder + File.separator + filename + ".yml");
    }

    public void createFolder(String folderName) {
        File folder = new File(main.getDataFolder(), folderName);

        if (!folder.exists()) {
            folder.mkdir();
        }
    }

    public File getFolder(String folderName) {
        return new File(main.getDataFolder(), folderName);
    }

    public boolean fileExists(String folder, String fileName) {
        return getFile(folder, fileName).exists();
    }
}
