package MP.TenochMC.Config;

import MP.TenochMC.Evento;
import MP.TenochMC.Model.PlayerData;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayersConfigManager extends DataFolderConfigManager {

    public PlayersConfigManager(Evento plugin, String folderName) {
        super(plugin, folderName);
    }

    @Override
    public void loadConfigs() {
        Map<UUID, PlayerData> players = new HashMap<>();
        for (CustomConfig customConfig : configFiles) {
            FileConfiguration config = customConfig.getConfig();

            try {
                UUID uuid = UUID.fromString(customConfig.getPath().replace(".yml", ""));
                String name = config.getString("nick");
                boolean congelado = config.getBoolean("congelado");
                int pregunta = config.getInt("pregunta");

                PlayerData playerData = new PlayerData(uuid, name, congelado, pregunta);
                players.put(uuid, playerData);
            } catch (Exception e) {
            }
        }

        plugin.getPlayerDataManager().setPlayers(players);
    }

    @Override
    public void saveConfigs() {
        Map<UUID, PlayerData> players = plugin.getPlayerDataManager().getPlayers();
        for (Map.Entry<UUID, PlayerData> entry : players.entrySet()) {
            guardarPlayerData(entry.getValue());
        }
    }
    public void guardarPlayerData(PlayerData playerData) {
        String pathName = playerData.getUuid().toString() + ".yml";
        CustomConfig customConfig = getConfigFile(pathName);

        if (customConfig == null) {
            customConfig = registerConfigFile(pathName);
        }

        FileConfiguration config = customConfig.getConfig();
        config.set("nick", playerData.getName());
        config.set("congelado", playerData.getCongelado());
        config.set("pregunta", playerData.getPregunta());
        customConfig.saveConfig();
    }
}