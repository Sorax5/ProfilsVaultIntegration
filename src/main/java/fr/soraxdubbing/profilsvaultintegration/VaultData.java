package fr.soraxdubbing.profilsvaultintegration;

import fr.soraxdubbing.profilsmanagercore.Addon.AddonData;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VaultData extends AddonData {

    private double money;
    private List<String> permission;

    public VaultData() {
        super("vaultdata");
        this.money = 0;
        this.permission = new ArrayList<>();
    }

    @Override
    public void updateAddonData(Player player, JavaPlugin javaPlugin) {
        ProfilsVaultIntegration plugin = ProfilsVaultIntegration.getInstance();
        this.setMoney(plugin.getEconomy().getBalance(player));

        List<String> permissions = new ArrayList<>(Arrays.asList(plugin.getPermission().getPlayerGroups(player)));

        for (String permission : permissions) {
            if (!this.getPermission().contains(permission)) {
                this.getPermission().add(permission);
            }
        }

        this.setPermission(permissions);


    }

    @Override
    public void loadAddonData(Player player, JavaPlugin javaPlugin) {
        ProfilsVaultIntegration plugin = ProfilsVaultIntegration.getInstance();
        plugin.getEconomy().withdrawPlayer(player, plugin.getEconomy().getBalance(player));
        plugin.getEconomy().depositPlayer(player, this.getMoney());

        for (String playerGroup : plugin.getPermission().getPlayerGroups(player)) {
            plugin.getLogger().info(playerGroup);
            plugin.getPermission().getPrimaryGroup(player);
        }

        for (String permission : this.getPermission()) {
            plugin.getPermission().playerAdd(player, permission);
        }

    }

    @Override
    public String toString() {
        StringBuilder list = new StringBuilder();
        list.append("money: ").append(this.money).append("€").append("\n");
        String perm = "§7";
        for (String s : getPermission()) {
            list.append(perm).append(" - ").append(s).append('\n');
        }
        return list.toString();
    }

    public void setMoney(double money){
        this.money = money;
    }

    public double getMoney(){
        return this.money;
    }

    public void setPermission(List<String> permission){
        this.permission = permission;
    }

    public List<String> getPermission(){
        return this.permission;
    }
}
