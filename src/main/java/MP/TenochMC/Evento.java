package MP.TenochMC;

import MP.TenochMC.Comandos.Comandos;
import MP.TenochMC.Config.MainConfigManager;
import MP.TenochMC.Config.PlayersConfigManager;
import MP.TenochMC.Eventos.Preguntas;
import MP.TenochMC.Listeners.PlayerListeners;
import MP.TenochMC.Managers.CastigoManager;
import MP.TenochMC.Managers.PlayerDataManager;
import MP.TenochMC.utils.MessageUtils;
import MP.TenochMC.utils.TimeTask;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class Evento extends JavaPlugin {

    public static String prefix = "&cEventos &8» ";
    private String Version = getDescription().getVersion();
    private MainConfigManager mainConfigManager;
    private PlayerDataManager playerDataManager;
    private PlayersConfigManager playersConfigManager;
    private Preguntas preguntas;
    private BukkitTask eventoTask;
    private boolean eventoAutomaticoActivo = false;
    private CastigoManager castigoManager;

    @Override
    public void onEnable() {
        registerCommands();
        registerEvents();

        mainConfigManager = new MainConfigManager(this);
        playerDataManager = new PlayerDataManager();
        playersConfigManager = new PlayersConfigManager(this, "players");
        preguntas = new Preguntas(this);
        castigoManager = new CastigoManager(this);

        if (mainConfigManager.isAutomaticoAlIniciar()) {
            long intervalo = mainConfigManager.getIntervaloTicks();
            iniciarEventoAutomatico(3000L, intervalo);
        }

        Bukkit.getConsoleSender().sendMessage(MessageUtils.GetColoredMessage(Evento.prefix + "&ciniciado correctamente! &7Version: " + Version));
        preguntas = new Preguntas(this);
    }

    @Override
    public void onDisable() {
        detenerEventoAutomatico();
        if (playersConfigManager != null) {
            playersConfigManager.saveConfigs();
        }
        Bukkit.getConsoleSender().sendMessage(MessageUtils.GetColoredMessage(Evento.prefix + "&capagado correctamente! &7Version: " + Version));
    }

    public void registerCommands() {
        this.getCommand("eventos").setExecutor(new Comandos(this));
    }

    public void registerEvents() {
        getServer().getPluginManager().registerEvents(new PlayerListeners(this), this);
    }

    public void iniciarEventoAutomatico(long delayTicks, long periodTicks) {
        if (eventoAutomaticoActivo) return;

        eventoTask = new TimeTask(this, () -> {
            preguntas.seleccionarPregunta();
        }).runTaskTimer(this, delayTicks, periodTicks);

        eventoAutomaticoActivo = true;
    }

    public void detenerEventoAutomatico() {
        if (!eventoAutomaticoActivo) return;

        if (eventoTask != null) {
            eventoTask.cancel();
            eventoTask = null;
        }
        eventoAutomaticoActivo = false;
    }
//getters
    public CastigoManager getCastigoManager() {
        return castigoManager;
    }

    public MainConfigManager getMainConfigManager() {
        return mainConfigManager;
    }

    public PlayersConfigManager getPlayersConfigManager() {
        return playersConfigManager;
    }

    public PlayerDataManager getPlayerDataManager() {
        return playerDataManager;
    }

    public Preguntas getPreguntas() {
        return preguntas;
    }

    public boolean isEventoAutomaticoActivo() {
        return eventoAutomaticoActivo;
    }
}