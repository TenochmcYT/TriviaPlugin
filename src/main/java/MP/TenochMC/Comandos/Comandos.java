package MP.TenochMC.Comandos;

import MP.TenochMC.Evento;
import MP.TenochMC.utils.MessageUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Comandos implements CommandExecutor {

    private final Evento plugin;

    public Comandos(Evento plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {

        // --- MANEJO PARA CONSOLA ---
        if (!(sender instanceof Player)) {
            if (args.length >= 1 && args[0].equalsIgnoreCase("reload")) {
                subCommandReload(sender);
                return true;
            }
            sender.sendMessage(MessageUtils.GetColoredMessage(Evento.prefix + "&cLa consola solo puede ejecutar /eventos reload."));
            return true;
        }
        if (args.length >= 1) {

            if (args[0].equalsIgnoreCase("activar")) {
                if (!sender.hasPermission("eventos.comandos.activar")) {
                    sender.sendMessage(MessageUtils.GetColoredMessage(plugin.getMainConfigManager().getDenegacion()));
                    return true;
                }

                if (plugin.isEventoAutomaticoActivo()) {
                    sender.sendMessage(MessageUtils.GetColoredMessage(Evento.prefix + "&cEl sistema de eventos automáticos ya está activo."));
                    return true;
                }

                long intervaloTicks = plugin.getMainConfigManager().getIntervaloTicks();
                plugin.iniciarEventoAutomatico(200L, intervaloTicks);
                sender.sendMessage(MessageUtils.GetColoredMessage(Evento.prefix + "&a¡Has activado el sistema de eventos automáticos!"));
                return true;

            } else if (args[0].equalsIgnoreCase("desactivar")) {
                if (!sender.hasPermission("eventos.comandos.desactivar")) {
                    sender.sendMessage(MessageUtils.GetColoredMessage(plugin.getMainConfigManager().getDenegacion()));
                    return true;
                }

                if (!plugin.isEventoAutomaticoActivo()) {
                    sender.sendMessage(MessageUtils.GetColoredMessage(Evento.prefix + "&cEl sistema de eventos automáticos no está activo."));
                    return true;
                }

                plugin.detenerEventoAutomatico();
                sender.sendMessage(MessageUtils.GetColoredMessage(Evento.prefix + "&cHas desactivado el sistema de eventos automáticos."));
                return true;

            } else if (args[0].equalsIgnoreCase("forzar")) {
                if (!sender.hasPermission("eventos.comandos.forzar")) {
                    sender.sendMessage(MessageUtils.GetColoredMessage(plugin.getMainConfigManager().getDenegacion()));
                    return true;
                }

                boolean iniciado = plugin.getPreguntas().seleccionarPregunta();
                if (iniciado) {
                    sender.sendMessage(MessageUtils.GetColoredMessage(Evento.prefix + "&a¡Has forzado el inicio del evento!"));
                } else {
                    sender.sendMessage(MessageUtils.GetColoredMessage(Evento.prefix + "&cNo se puede forzar el evento: ya hay un evento activo."));
                }
                return true;

            } else if (args[0].equalsIgnoreCase("reload")) {
                if (!sender.hasPermission("eventos.comandos.reload")) {
                    sender.sendMessage(MessageUtils.GetColoredMessage(plugin.getMainConfigManager().getDenegacion()));
                    return true;
                }
                subCommandReload(sender);
                return true;
            }
        }
        help(sender);
        return true;
    }

    public void help(CommandSender sender) {
        sender.sendMessage(MessageUtils.GetColoredMessage("&8----------- &cComandos disponibles &8-----------"));
        sender.sendMessage(MessageUtils.GetColoredMessage("&8• &c/eventos &7activar"));
        sender.sendMessage(MessageUtils.GetColoredMessage("&8• &c/eventos &7desactivar"));
        sender.sendMessage(MessageUtils.GetColoredMessage("&8• &c/eventos &7forzar"));
        sender.sendMessage(MessageUtils.GetColoredMessage("&8• &c/eventos &7reload"));
        sender.sendMessage(MessageUtils.GetColoredMessage("&8-----------------------------------------"));
    }

    public void subCommandReload(CommandSender sender) {
        if (!sender.hasPermission("eventos.comandos.reload")) {
            sender.sendMessage(MessageUtils.GetColoredMessage(plugin.getMainConfigManager().getDenegacion()));
            return;
        }

        plugin.getMainConfigManager().reloadConfig();
        sender.sendMessage(MessageUtils.GetColoredMessage(Evento.prefix + "&a¡Configuración recargada!"));
    }
}