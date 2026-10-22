package MP.TenochMC.Managers;

import MP.TenochMC.Evento;
import MP.TenochMC.utils.MessageUtils;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
import java.util.Random;

public class CastigoManager {

    private final Evento plugin;
    private final Random random = new Random();

    public CastigoManager(Evento plugin) {
        this.plugin = plugin;
    }

    public void aplicarCastigoAleatorio(Player player) {
        if (!plugin.getMainConfigManager().isCastigosGeneralesActivos()) {
            return;
        }
        List<String> castigosActivos = plugin.getMainConfigManager().getCastigosHabilitados();

        if (castigosActivos.isEmpty()) {
            player.sendMessage(MessageUtils.GetColoredMessage(Evento.prefix + "§c[Debug] No hay castigos activos en la lista de la config."));
            return;
        }

        String castigoElegido = castigosActivos.get(random.nextInt(castigosActivos.size()));
        //player.sendMessage(MessageUtils.GetColoredMessage(Evento.prefix + "§c[Debug] &7Castigo seleccionado: &a" +castigoElegido));
        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
        procesarCastigo(player, castigoElegido);
    }

    private void procesarCastigo(Player player, String castigo) {
        if (procesarEfectos(player, castigo)) return;
        if (procesarDano(player, castigo)) return;
        if (procesarInventario(player, castigo)) return;
        if (procesarMobs(player, castigo)) return;
    }
    private void spawnearMobSinDrops(Location loc, EntityType type) {
        Entity entity = loc.getWorld().spawnEntity(loc, type);

        if (entity instanceof LivingEntity) {
            LivingEntity mob = (LivingEntity) entity;
            if (mob.getEquipment() != null) {
                mob.getEquipment().clear();
            }
            mob.addScoreboardTag("mob_castigo");
        }
    }
//efectos
    private boolean procesarEfectos(Player player, String castigo) {
        switch (castigo) {
            case "veneno": player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 0)); // 5 seg
                return true;
            case "wither": player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 100, 0)); // 5 seg
                return true;
            case "nauseas": player.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, 120, 0)); // 6 seg
                return true;
            case "lentitud": player.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 100, 1)); // 5 seg
                return true;
            case "debilidad": player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 100, 0)); // 5 seg
                return true;
            case "ceguera": player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 80, 0)); // 4 seg
                return true;
            case "hambre": player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 100, 1)); // 5 seg
                return true;
            case "fatiga_minera": player.addPotionEffect(new PotionEffect(PotionEffectType.MINING_FATIGUE, 100, 1)); // 5 seg
                return true;
            case "oscuridad": player.addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS, 100, 0)); // 5 seg
                return true;
            case "mal_presagio": player.addPotionEffect(new PotionEffect(PotionEffectType.BAD_OMEN, 100, 0)); // 5 seg
                return true;
        }
        return false;
    }
//daño
    private boolean procesarDano(Player player, String castigo) {
        switch (castigo) {
            case "quitar_vida":
                double vidaActual = player.getHealth();
                if (vidaActual > 2.0) {
                    player.setHealth(vidaActual - 2.0); // Le quita 1 corazón sin activar animación de golpe
                }
                return true;
            case "quemadura":
                player.setFireTicks(60); // 3 segundos en fuego
                return true;
            case "hambre_extrema":
                player.setFoodLevel(Math.max(0, player.getFoodLevel() - 6)); // Quita 3 muslos de comida
                return true;
        }
        return false;
    }
//inventario
    private boolean procesarInventario(Player player, String castigo) {
        switch (castigo) {
            case "dropear_mano":
                ItemStack mano = player.getInventory().getItemInMainHand();
                if (mano != null && !mano.getType().isAir()) {
                    player.dropItem(false);
                }
                return true;

            case "dropear_offhand":
                ItemStack offhand = player.getInventory().getItemInOffHand();
                if (offhand != null && !offhand.getType().isAir()) {
                    player.getWorld().dropItemNaturally(player.getLocation(), offhand.clone());
                    player.getInventory().setItemInOffHand(null);
                }
                return true;

            case "soltar_objeto_aleatorio":
                int slotRandom = random.nextInt(36);
                ItemStack itemRandom = player.getInventory().getItem(slotRandom);
                if (itemRandom != null && !itemRandom.getType().isAir()) {
                    player.getWorld().dropItemNaturally(player.getLocation(), itemRandom.clone());
                    player.getInventory().setItem(slotRandom, null);
                }
                return true;

            case "vaciar_mano":
                player.getInventory().setItemInMainHand(null); // Elimina el ítem de la mano principal
                return true;

            case "perder_experiencia":
                int expActual = player.getTotalExperience();
                player.setTotalExperience(Math.max(0, expActual - 50));
                if (player.getLevel() > 0) {
                    player.setLevel(player.getLevel() - 1);
                }
                return true;
        }
        return false;
    }
//mobs
    private boolean procesarMobs(Player player, String castigo) {
        Location loc = player.getLocation();

        switch (castigo) {
            // Hostiles
            case "mob_zombie":
                spawnearMobSinDrops(loc, EntityType.ZOMBIE);
                return true;
            case "mob_esqueleto":
                spawnearMobSinDrops(loc, EntityType.SKELETON);
                return true;
            case "mob_creeper":
                spawnearMobSinDrops(loc, EntityType.CREEPER);
                return true;
            case "mob_araña":
                spawnearMobSinDrops(loc, EntityType.SPIDER);
                return true;
            case "mob_enderman":
                spawnearMobSinDrops(loc, EntityType.ENDERMAN);
                return true;
            case "mob_bruja":
                spawnearMobSinDrops(loc, EntityType.WITCH);
                return true;
            case "mob_slime":
                spawnearMobSinDrops(loc, EntityType.SLIME);
                return true;
            case "mob_phantom":
                spawnearMobSinDrops(loc, EntityType.PHANTOM);
                return true;
            case "mob_husk":
                spawnearMobSinDrops(loc, EntityType.HUSK);
                return true;
            case "mob_drowned":
                spawnearMobSinDrops(loc, EntityType.DROWNED);
                return true;
            case "mob_pillager":
                spawnearMobSinDrops(loc, EntityType.PILLAGER);
                return true;
            case "mob_silverfish":
                spawnearMobSinDrops(loc, EntityType.SILVERFISH);
                return true;
            case "mob_cave_spider":
                spawnearMobSinDrops(loc, EntityType.CAVE_SPIDER);
                return true;
            case "mob_blaze":
                spawnearMobSinDrops(loc, EntityType.BLAZE);
                return true;
            case "mob_magma_cube":
                spawnearMobSinDrops(loc, EntityType.MAGMA_CUBE);
                return true;
            case "mob_vindicator":
                spawnearMobSinDrops(loc, EntityType.VINDICATOR);
                return true;

            // Pasivos
            case "mob_vaca":
                spawnearMobSinDrops(loc, EntityType.COW);
                return true;
            case "mob_cerdo":
                spawnearMobSinDrops(loc, EntityType.PIG);
                return true;
            case "mob_oveja":
                spawnearMobSinDrops(loc, EntityType.SHEEP);
                return true;
            case "mob_gallina":
                spawnearMobSinDrops(loc, EntityType.CHICKEN);
                return true;
            case "mob_lobo":
                spawnearMobSinDrops(loc, EntityType.WOLF);
                return true;
            case "mob_gato":
                spawnearMobSinDrops(loc, EntityType.CAT);
                return true;
            case "mob_abeja":
                spawnearMobSinDrops(loc, EntityType.BEE);
                return true;
        }
        return false;
    }
}