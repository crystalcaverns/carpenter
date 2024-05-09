package cc.carpenter;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import static cc.carpenter.Downloader.download;

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
                // get the config
                FileConfiguration config = plugin.getConfig();
                // start renovating plugins
                for (String name : config.getKeys(false)) {
                    // get the URL from config
                    String url = config.getString(name);
                    if (url == null) {
                        logger.log(Level.SEVERE, "Cannot renovate \"" + name + "\" - no URL found in entry.");
                        break;
                    }
                    // prepare the origin
                    URL origin;
                    try {
                        origin = URI.create(url).toURL();
                    } catch (MalformedURLException e) {
                        logger.log(Level.SEVERE, "Cannot renovate \"" + name + "\" - the URL is malformed.");
                        break;
                    }
                    // download the file
                    download(origin, name, logger);
                }
                return true;
            }
            case "reload": {
                plugin.reloadConfig();
                logger.log(Level.INFO, "Reloaded successfully! All new entries are registered.");
                return true;
            }
        }
        return false;
    }
}