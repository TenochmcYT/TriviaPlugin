package MP.TenochMC.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemUtils {
    public static ItemStack GenerateEmeraldItem(int amount) {
        ItemStack item = new ItemStack(Material.STICK, amount);
        ItemMeta meta = item.getItemMeta();//nombre custom
        meta.setDisplayName(MessageUtils.GetColoredMessage("&cPalito sabroson"));
        //<>
        List<String> lore = new ArrayList<>();//descripcion custom
        lore.add(MessageUtils.GetColoredMessage("&7Golpea a todos"));
        lore.add(MessageUtils.GetColoredMessage("&7con el palito"));
        lore.add(MessageUtils.GetColoredMessage("&cSabroson"));
        lore.add(MessageUtils.GetColoredMessage(""));
        lore.add(MessageUtils.GetColoredMessage("&bVortrex.net"));
        meta.setLore(lore);
        //encantamientos
        meta.addEnchant(Enchantment.KNOCKBACK,5,true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        //agrega la informacion al item
        item.setItemMeta(meta);

        return item;
    }
}
