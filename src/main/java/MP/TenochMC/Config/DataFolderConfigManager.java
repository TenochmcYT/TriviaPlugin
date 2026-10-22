package MP.TenochMC.Config;

import MP.TenochMC.Evento;
import java.io.File;
import java.util.ArrayList;

public abstract class DataFolderConfigManager {
    protected Evento plugin;
    protected String folderName;
    protected ArrayList<CustomConfig> configFiles;

    public DataFolderConfigManager(Evento plugin, String folderName){
        this.plugin = plugin;
        this.folderName = folderName;
        this.configFiles = new ArrayList<>();
        configure();
    }

    public void configure() {
        createFolder();
        reloadConfigs();
    }

    public void reloadConfigs(){
        this.configFiles = new ArrayList<>();
        registerConfigFiles();
        loadConfigs();
    }

    public void createFolder(){
        try {
            File folder = new File(plugin.getDataFolder(), folderName);
            if(!folder.exists()){
                folder.mkdirs();
            }
        } catch(SecurityException e) {
            e.printStackTrace();
        }
    }

    public void saveConfigFiles() {
        for (CustomConfig configFile : configFiles) {
            configFile.saveConfig();
        }
    }

    public void registerConfigFiles(){
        File folder = new File(plugin.getDataFolder(), folderName);
        if (!folder.exists() || !folder.isDirectory()) {
            return; // Si no existe la carpeta, salimos de forma segura
        }

        File[] listOfFiles = folder.listFiles();
        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    registerConfigFile(file.getName());
                }
            }
        }
    }

    public ArrayList<CustomConfig> getConfigs(){
        return this.configFiles;
    }

    public CustomConfig getConfigFile(String pathName) {
        for (CustomConfig configFile : configFiles) {
            if (configFile.getPath().equals(pathName)) {
                return configFile;
            }
        }
        return null;
    }

    public CustomConfig registerConfigFile(String pathName) {
        CustomConfig config = new CustomConfig(pathName, folderName, plugin, true);
        config.registerConfig();
        configFiles.add(config);
        return config;
    }

    public abstract void loadConfigs();

    public abstract void saveConfigs();
}