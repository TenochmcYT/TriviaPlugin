package MP.TenochMC.utils;
import MP.TenochMC.Evento;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class TimeTask extends BukkitRunnable {
    private final Evento plugin;
    private final Runnable accion;
    public TimeTask(Evento plugin, Runnable accion) {
        this.plugin = plugin;
        this.accion = accion;
    }
    @Override
    public void run() {
        if (Bukkit.getOnlinePlayers().isEmpty()) {
            return;
        }
        if (accion != null) {
            accion.run();
        }
    }
}
