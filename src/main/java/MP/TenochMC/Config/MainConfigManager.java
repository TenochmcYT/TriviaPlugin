package MP.TenochMC.Config;

import MP.TenochMC.Evento;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class MainConfigManager{

    private CustomConfig configFile;
    private Evento plugin;
    private String Denegacion;
    private boolean BienvenidaActiva;
    private List<String> MensajeBienvenida;
    private List<String> PreguntaDisponible;
    private List<String> RespuestaDisponible;
    private boolean isAutomaticoAlIniciar;
    private int IntervaloTicks;
    private int TiempoLimiteSegundos;
    private boolean castigosGeneralesActivos;

    public MainConfigManager(Evento plugin){
        this.plugin = plugin;
        configFile = new CustomConfig("Config.yml",null, plugin ,false);
        configFile.registerConfig();
        loadConfig();
    }

    public void loadConfig(){
        FileConfiguration config = configFile.getConfig();
        Denegacion = config.getString("mensajes.denegacion");
        BienvenidaActiva = config.getBoolean("config.bienvenida.activo");
        MensajeBienvenida = config.getStringList("config.bienvenida.mensajes");
        PreguntaDisponible = config.getStringList("eventos.preguntas.preguntas");
        RespuestaDisponible = config.getStringList("eventos.preguntas.respuestas");
        isAutomaticoAlIniciar = config.getBoolean("eventos.automatico");
        IntervaloTicks = config.getInt("eventos.intervalo");
        TiempoLimiteSegundos = config.getInt("eventos.preguntas.tiempo-limite", 15);
        castigosGeneralesActivos = config.getBoolean("eventos.castigos.activados", true);
    }

    public List<String> getCastigosHabilitados() {
        List<String> activos = new ArrayList<>();
        ConfigurationSection section = configFile.getConfig().getConfigurationSection("eventos.castigos.lista");

        if (section != null) {
            for (String clave : section.getKeys(false)) {
                if (section.getBoolean(clave)) {
                    activos.add(clave.toLowerCase());
                }
            }
        }else {
            Bukkit.getLogger().warning("[Evento] ¡No se encontró la sección preguntas.castigos.lista en el config.yml!");
        }
        return activos;
    }

    public boolean isCastigosGeneralesActivos() {
        return castigosGeneralesActivos;
    }

    public void reloadConfig(){
        configFile.reloadConfig();
        loadConfig();
    }

    public List<String> getMensajeBienvenida() {
        return MensajeBienvenida;
    }

    public String getDenegacion() {
        return Denegacion;
    }

    public boolean isBienvenidaActiva() {
        return BienvenidaActiva;
    }

    public List<String> getPreguntaDisponible() {
        return PreguntaDisponible;
    }

    public List<String> getRespuestaDisponible() {
        return RespuestaDisponible;
    }

    public CustomConfig getConfigFile() {
        return configFile;
    }

    public int getIntervaloTicks() {
        return IntervaloTicks;
    }

    public boolean isAutomaticoAlIniciar() {
        return isAutomaticoAlIniciar;
    }
    public int getTiempoLimiteSegundos() {
        return TiempoLimiteSegundos;
    }
}
