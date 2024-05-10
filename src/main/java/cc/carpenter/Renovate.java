package cc.carpenter;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Renovate implements CommandExecutor {
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        // check if the command is correct
        if (args.length != 1) {
            return false;
        }
        // get the plugin object
        Plugin plugin = Carpenter.getPlugin(Carpenter.class);
        // get logger
        Logger logger = plugin.getLogger();
        // decide what to do
        switch (args[0]) {
            case "all": {
                logger.log(Level.INFO,"Booting up server-side system renovation, file downloads incoming...");
                Manager.renovate();
                return true;
            }
            case "reload": {
                plugin.reloadConfig();
                logger.log(Level.INFO,"Reloaded successfully! All new entries are registered.");
                return true;
            }
        }
        return false;
    }
}