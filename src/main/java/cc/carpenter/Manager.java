package cc.carpenter;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.bukkit.Bukkit.getScheduler;

public class Manager {
    public static void renovate() {
        // start renovating plugins
        getScheduler().runTaskAsynchronously(Carpenter.getPlugin(Carpenter.class), () -> {
            // get the plugin object
            Plugin plugin = Carpenter.getPlugin(Carpenter.class);
            // get the config
            FileConfiguration config = plugin.getConfig();
            // get logger
            Logger logger = plugin.getLogger();
            // renovate all plugins
            for (String name : config.getKeys(false)) {
                // get the URL from config
                String rawURL = config.getString(name);
                if (rawURL == null) {
                    logger.log(Level.SEVERE,"Cannot renovate \"" + name + "\" - no URL found in entry.");
                    break;
                }
                // check if the URL is from Modrinth
                if (rawURL.startsWith("[redirect]")) {
                    // get the redirect URL
                    String redirectURL = rawURL.substring(10);
                    // get the origin URL
                    rawURL = Redirector.redirect(redirectURL);
                    // check if origin URL is valid
                    if (rawURL == null) {
                        logger.log(Level.SEVERE,"Cannot renovate \"" + name + "\" - the API URL is invalid.");
                        break;
                    }
                }
                // prepare the origin URL
                URL origin;
                // create the origin URL
                try {
                    origin = URI.create(rawURL).toURL();
                } catch (MalformedURLException e) {
                    logger.log(Level.SEVERE,"Cannot renovate \"" + name + "\" - the URL is malformed.");
                    break;
                }
                // download the file
                Downloader.download(origin, name);
            }
            logger.log(Level.INFO,"All plugins have been renovated successfully!");
        });
        // we're done here
    }
}