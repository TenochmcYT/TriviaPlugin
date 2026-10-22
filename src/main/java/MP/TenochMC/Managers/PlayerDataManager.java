package MP.TenochMC.Managers;

import MP.TenochMC.Model.PlayerData;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerDataManager {
    private Map<UUID, PlayerData> players;
    private Map<String, UUID> playerNames;

    public PlayerDataManager() {
        players = new HashMap<>();
        playerNames = new HashMap<>();
    }

    public Map<UUID, PlayerData> getPlayers() {
        return players;
    }

    public void setPlayers(Map<UUID, PlayerData> players) {
        this.players = players;
        for (Map.Entry<UUID, PlayerData> entry : players.entrySet()) {
            playerNames.put(entry.getValue().getName(), entry.getKey());
        }
    }

    public void addPlayer(PlayerData p) {
        players.put(p.getUuid(), p);
        playerNames.put(p.getName(), p.getUuid());
    }

    public PlayerData getPlayer(Player player, boolean create) {
        PlayerData playerData = players.get(player.getUniqueId());
        if (playerData == null && create) {
            playerData = new PlayerData(player.getUniqueId(), player.getName(), false, -1);
            addPlayer(playerData);
        }
        return playerData;
    }

    public PlayerData getPlayerByName(String playerName) {
        UUID uuid = playerNames.get(playerName);
        return players.get(uuid);
    }

    public void setCongelado(Player player, boolean congelado) {
        PlayerData playerData = getPlayer(player, true);
        if (playerData != null) {
            playerData.setCongelado(congelado);
        }
    }
    public boolean isCongelado(Player player) {
        PlayerData playerData = getPlayer(player, false);
        return playerData != null && playerData.getCongelado();
    }
    public boolean isCongeladoByName(String playerName) {
        PlayerData playerData = getPlayerByName(playerName);
        return playerData != null && playerData.getCongelado();
    }

    public void setPregunta(Player player,int index){
        PlayerData playerData = getPlayer(player,true);
        if (playerData != null) {
            playerData.setPregunta(index);
        }
    }

    public int getPreguntaByPlayer(Player player){
        PlayerData playerData = getPlayer(player,false);
        if(playerData != null){
            return playerData.getPregunta();
        }
        return -1;
    }

    public int getPreguntaByName(String playerName){
        PlayerData playerData = getPlayerByName(playerName);
        if(playerData != null){
            return playerData.getPregunta();
        }
        return -1;
    }

    public void updateName(Player player) {
        PlayerData playerData = getPlayer(player, false);
        if (playerData != null) {
            String newName = player.getName();
            String oldName = playerData.getName();
            if (!newName.equals(oldName)) {
                playerNames.remove(oldName);
                playerNames.put(newName, player.getUniqueId());
                playerData.setName(newName);
            }
        }
    }
}