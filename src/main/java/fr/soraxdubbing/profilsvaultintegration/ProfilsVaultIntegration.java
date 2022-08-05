package fr.soraxdubbing.profilsvaultintegration;

import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;
import fr.soraxdubbing.profilsmanagercore.ProfilsManagerCore;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class ProfilsVaultIntegration extends JavaPlugin {

    private Economy economy;
    private Permission permission;
    private Chat chat;

    private static ProfilsVaultIntegration INSTANCE;

    @Override
    public void onEnable() {
        // Plugin startup logic
        if (!setupEconomy() ) {
            this.getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        setupPermissions();
        setupChat();

        INSTANCE = this;

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return economy != null;
    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        permission = rsp.getProvider();
        return permission != null;
    }

    public Economy getEconomy() {
        return economy;
    }
    public Permission getPermission() {
        return permission;
    }
    public Chat getChat() {
        return chat;
    }

    public static ProfilsVaultIntegration getInstance() {
        return ProfilsVaultIntegration.INSTANCE;
    }
}
