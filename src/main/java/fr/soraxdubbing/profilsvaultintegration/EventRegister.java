package fr.soraxdubbing.profilsvaultintegration;

import fr.soraxdubbing.profilsmanagercore.event.AddonRegisterEvent;
import fr.soraxdubbing.profilsmanagercore.event.ProfilLoadedEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EventRegister implements Listener {

    @EventHandler
    public void registerAddon(AddonRegisterEvent event){
        if(!event.getData().containsClass(VaultData.class)){
            event.registerAddon(VaultData.class);
        }
    }

    @EventHandler
    public void onProfilLoad(ProfilLoadedEvent e){
        if(!e.getProfil().hasAddon("vaultdata")){
            e.getProfil().addAddon(new VaultData());
        }
    }
}
