package MP.TenochMC.utils;

import org.bukkit.ChatColor;

public class MessageUtils {
    public static String GetColoredMessage(String message){
        return ChatColor.translateAlternateColorCodes('&',message);
    }
}
