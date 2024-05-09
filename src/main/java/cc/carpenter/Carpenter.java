package cc.carpenter;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

import static org.bukkit.Bukkit.getConsoleSender;

public final class Carpenter extends JavaPlugin {
    @Override
    public void onEnable() {
        // config
        saveDefaultConfig();
        // prepare cache
        File cache = new File("/home/container/plugins/.renovated/");
        if (!cache.exists()) {
            cache.mkdirs();
        }
        // commands
        PluginCommand command = getCommand("renovate");
        if (command == null) return;
        command.setExecutor(new Renovate());
        // welcome message
        TextComponent message = Component.text("Utility Carpenter protocol running, ready to update the network to a new version.")
            .color(TextColor.fromHexString("#9944FF"));
        getConsoleSender().sendMessage(message);
        // yay, we're up and running!
    }
}