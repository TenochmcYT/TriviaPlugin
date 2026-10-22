package MP.TenochMC.Model;
import java.util.UUID;

public class PlayerData {
    private UUID uuid;
    private String name;
    private boolean congelado;
    private int pregunta;

    public PlayerData(UUID uuid, String name, boolean congelado, int pregunta) {
        this.uuid = uuid;
        this.name = name;
        this.congelado = congelado;
        this.pregunta = pregunta;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getCongelado() {
        return congelado;
    }

    public void setCongelado(boolean congelado) {
        this.congelado = congelado;
    }
    public int getPregunta(){
        return pregunta;
    }
    public void setPregunta(int pregunta){
        this.pregunta = pregunta;
    }

}