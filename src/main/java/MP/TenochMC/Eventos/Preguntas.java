package MP.TenochMC.Eventos;

import MP.TenochMC.Evento;
import MP.TenochMC.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;
import java.util.Random;

public class Preguntas {

    private final Evento plugin;
    private boolean eventoEnCurso = false;
    private BukkitTask temporizadorPregunta;

    public Preguntas(Evento plugin) {
        this.plugin = plugin;
    }

    public boolean seleccionarPregunta() {
        if (eventoEnCurso) {
            return false;
        }

        List<String> listaPreguntas = plugin.getMainConfigManager().getPreguntaDisponible();
        if (listaPreguntas == null || listaPreguntas.isEmpty()) {
            return false;
        }

        eventoEnCurso = true;

        Random random = new Random();
        int randomIndex = random.nextInt(listaPreguntas.size());
        String preguntaTexto = listaPreguntas.get(randomIndex);

        // Enviar Title y congelar a todos los jugadores conectados
        for (Player player : Bukkit.getOnlinePlayers()) {
            plugin.getPlayerDataManager().setPregunta(player, randomIndex);
            plugin.getPlayerDataManager().setCongelado(player, true);

            player.sendTitle(
                    MessageUtils.GetColoredMessage("&c&lResponde en el chat"), MessageUtils.GetColoredMessage(preguntaTexto), 10, 100, 20);
        }

        int segundos = plugin.getMainConfigManager().getTiempoLimiteSegundos();
        long ticks = segundos * 20L;

        temporizadorPregunta = Bukkit.getScheduler().runTaskLater(plugin, () -> {expirarPregunta();}, ticks);
        return true;
    }

    public void expirarPregunta() {
        eventoEnCurso = false;
        cancelarTemporizador();

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (plugin.getPlayerDataManager().isCongelado(player)) {
                player.sendMessage(MessageUtils.GetColoredMessage(Evento.prefix + "&c¡Se agotó el tiempo y no respondiste!"));
                plugin.getCastigoManager().aplicarCastigoAleatorio(player);
                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 1.0f, 0.5f);
                plugin.getPlayerDataManager().setCongelado(player, false);
                plugin.getPlayerDataManager().setPregunta(player, -1);
            }
        }
    }

    public void finalizarEventoExitoso() {
        eventoEnCurso = false;
        cancelarTemporizador();
    }

    public void cancelarTemporizador() {
        if (temporizadorPregunta != null) {
            temporizadorPregunta.cancel();
            temporizadorPregunta = null;
        }
    }

    public boolean isEventoEnCurso() {
        return eventoEnCurso;
    }
}