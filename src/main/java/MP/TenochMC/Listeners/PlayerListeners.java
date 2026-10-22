package MP.TenochMC.Listeners;

import MP.TenochMC.Config.MainConfigManager;
import MP.TenochMC.Evento;
import MP.TenochMC.Model.PlayerData;
import MP.TenochMC.utils.MessageUtils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;

public class PlayerListeners implements Listener {
    private Evento plugin;

    public PlayerListeners(Evento plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (plugin.getPlayerDataManager().isCongelado(player)) {
            event.setCancelled(true);

            int preguntaIndex = plugin.getPlayerDataManager().getPreguntaByPlayer(player);
            if (preguntaIndex == -1) return;

            List<String> respuestas = plugin.getConfig().getStringList("eventos.preguntas.respuestas");
            if (preguntaIndex >= respuestas.size()) return;

            String respuestaCorrecta = respuestas.get(preguntaIndex);
            String mensajeJugador = event.getMessage().trim();

            // RESPUESTA CORRECTA
            if (mensajeJugador.equalsIgnoreCase(respuestaCorrecta)) {
                player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1.0f, 1.0f);
                player.sendMessage(MessageUtils.GetColoredMessage(Evento.prefix + "&a¡Respuesta correcta! ."));
                plugin.getPlayerDataManager().setCongelado(player, false);
                plugin.getPlayerDataManager().setPregunta(player, -1);
                plugin.getPreguntas().finalizarEventoExitoso();
                //Bukkit.broadcastMessage(MessageUtils.GetColoredMessage(Evento.prefix + "&a¡" + player.getName() + " ha respondido correctamente!"))
            // RESPUEStA INCORRECTA
            } else {
                player.sendMessage(MessageUtils.GetColoredMessage(Evento.prefix + "&c¡Respuesta incorrecta!"));
                Bukkit.getScheduler().runTask(plugin, () -> {
                    plugin.getCastigoManager().aplicarCastigoAleatorio(player);
                    plugin.getPlayerDataManager().setCongelado(player, false);
                    plugin.getPlayerDataManager().setPregunta(player, -1);
                });
            }

            int indexPregunta = plugin.getPlayerDataManager().getPreguntaByPlayer(player);
            if (indexPregunta == -1) {
                return;
            }
        }
    }
    // bloquear drops castigos
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity().getScoreboardTags().contains("mob_castigo")) {
            event.getDrops().clear();
            event.setDroppedExp(0);
        }
    }
    // bienvenida
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        plugin.getPlayerDataManager().getPlayer(player, true);
        plugin.getPlayerDataManager().updateName(player);
        MainConfigManager mainConfigManager = plugin.getMainConfigManager();
        if (mainConfigManager.isBienvenidaActiva()) {
            List<String> mensaje = mainConfigManager.getMensajeBienvenida();
            for (String m : mensaje) {
                player.sendMessage(
                        MessageUtils.GetColoredMessage(m.replace("%player%", player.getName())));
            }
        }
        World world = Bukkit.getWorld("world");
        if (world == null) {
            return;
        }
    }
    //empiezan los efectos del congelamiento
    @EventHandler //cuando se mueve
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (plugin.getPlayerDataManager().isCongelado(player)) {
            if (event.getFrom().getBlockX() != event.getTo().getBlockX()
                    || event.getFrom().getBlockY() != event.getTo().getBlockY()
                    || event.getFrom().getBlockZ() != event.getTo().getBlockZ()) {
                event.setCancelled(true);
            }
        }
    }
    @EventHandler //cuando usa un comando
    public void onCommand(org.bukkit.event.player.PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        if (plugin.getPlayerDataManager().isCongelado(player)) {
            event.setCancelled(true);
            player.sendMessage(MessageUtils.GetColoredMessage(Evento.prefix + "&cNo puedes usar comandos mientras estas congelado!"));
        }
    }
    @EventHandler //cuando hace algo en su inventario
    public void onInventoryClick(org.bukkit.event.inventory.InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player player) {
            if (plugin.getPlayerDataManager().isCongelado(player)) {
                event.setCancelled(true);
            }
        }
    }
    @EventHandler //cuando dropea algo
    public void onDrop(org.bukkit.event.player.PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if (plugin.getPlayerDataManager().isCongelado(player)) {
            event.setCancelled(true);
        }
    }
    @EventHandler //cuando interactua
    public void onInteract(org.bukkit.event.player.PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (plugin.getPlayerDataManager().isCongelado(player)) {
            event.setCancelled(true);
        }
    }
    //aqui terminan los efectos del congelamiento
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        // Obtenemos el PlayerData desde el manager (crear en false para no instanciar si no participó)
        PlayerData data = plugin.getPlayerDataManager().getPlayer(player, false);

        // Verificamos que exista y que estuviera participando o congelado
        if (data != null && (data.getCongelado() || data.getPregunta() != -1)) {
            // Guardar en su archivo individual dentro de 'players/'
            plugin.getPlayersConfigManager().guardarPlayerData(data);
        }
    }
}

