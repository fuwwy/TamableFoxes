package net.seanomik.tamablefoxes;

import net.seanomik.tamablefoxes.io.Config;
import net.seanomik.tamablefoxes.versions.NMSInterface;
import net.seanomik.tamablefoxes.io.LanguageConfig;
import net.seanomik.tamablefoxes.versions.version_1_17_1_R1.NMSInterface_1_17_1_R1;
import org.bukkit.*;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

// @TODO:

/* @CHANGELOG (1.8.1-SNAPSHOT):
 * Fixes #32. Kinda hacky but will work for now
 */
public final class TamableFoxes extends JavaPlugin implements Listener {
    private static TamableFoxes plugin;

    private boolean versionSupported = true;

    public NMSInterface nmsInterface;

    @Override
    public void onLoad() {
        plugin = this;

        LanguageConfig.getConfig().saveDefault();

        // Verify server version
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        String specificVersion = Bukkit.getVersion();
        specificVersion = specificVersion.substring(specificVersion.indexOf("(MC: ") + 5, specificVersion.indexOf(')'));

        double versionDouble = Double.parseDouble(specificVersion.substring(2));

        System.out.println("MC Version: " + versionDouble);
        if (versionDouble == 17.1D) {
            nmsInterface = new NMSInterface_1_17_1_R1();
        } else {
            Bukkit.getServer().getConsoleSender().sendMessage(Config.getPrefix() + ChatColor.RED + LanguageConfig.getUnsupportedMCVersionRegister());
            Bukkit.getServer().getConsoleSender().sendMessage(Config.getPrefix() + ChatColor.RED + "You're trying to run MC version " + specificVersion + " which is not supported!");
            versionSupported = false;
        }


        // Display starting message then register entity.
        Bukkit.getServer().getConsoleSender().sendMessage(Utils.getPrefix() + ChatColor.YELLOW + LanguageConfig.getMCVersionLoading(version));
        nmsInterface.registerCustomFoxEntity();
    }

    @Override
    public void onEnable() {
        if (!versionSupported) {
            Bukkit.getServer().getConsoleSender().sendMessage(Utils.getPrefix() + ChatColor.RED + LanguageConfig.getUnsupportedMCVersionDisable());
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        getServer().getPluginManager().registerEvents(this, this);
        this.getCommand("spawntamablefox").setExecutor(new CommandSpawnTamableFox(this));

        this.saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(Utils.getPrefix() + ChatColor.YELLOW + LanguageConfig.getSavingFoxMessage());
    }

    public static TamableFoxes getPlugin() {
        return plugin;
    }
}
