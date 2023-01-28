package fr.erased.clans.manager;

import fr.erased.clans.Main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    private final Main main;

    public FileManager(Main main) {
        this.main = main;
    }

    public void createFile(String folder, String filename){
        File file = new File(main.getDataFolder() + File.separator + folder + File.separator + filename + ".yml");

        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void createFile(String filename){
        File file = new File(main.getDataFolder() + File.separator + filename + ".yml");

        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void removeFile(String folder, String filename){
        File file = new File(main.getDataFolder() + File.separator + folder + File.separator + filename + ".yml");

        if(file.exists()){
            file.delete();
        }
    }

    public void removeFile(String filename){
        File file = new File(main.getDataFolder() + File.separator + filename + ".yml");

        if(file.exists()){
            file.delete();
        }
    }

    public File getFile(String folder, String filename){
        return new File(main.getDataFolder() + File.separator + folder + File.separator + filename + ".yml");
    }

    public List<File> getFiles(String folder){
        List<File> files = new ArrayList<>();

        for (File file : new File(main.getDataFolder() + File.separator + folder).listFiles()) {
            if (file.isFile()) {
                files.add(file);
            }
        }

        return files;
    }

    public File getFile(String filename){
        return new File(main.getDataFolder() + File.separator + filename + ".yml");
    }

    public void createFolder(String foldername){
        File folder = new File(main.getDataFolder(), foldername);

        if(!folder.exists()){
            folder.mkdir();
        }
    }

    public File getFolder(String foldername){
        return new File(main.getDataFolder(), foldername);
    }

    public boolean fileExists(String folder, String filename){
        return getFile(folder, filename).exists();
    }
}
