package autotp.autotp.events;

import autotp.autotp.AutoTP;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

public class OnOpenGUIsDrag implements Listener {
    @EventHandler
    private void OnOpenGUIsDragged(InventoryDragEvent e){
        if(e.getView().getTitle().equalsIgnoreCase(ChatColor.GREEN + "send a teleport request to ") || e.getView().getTitle().equalsIgnoreCase(ChatColor.GREEN + "accept tp request from") || e.getView().getTitle().equalsIgnoreCase(ChatColor.GREEN + "deny tp request from")){
            e.setCancelled(true);
        }
    }
    @EventHandler
    private void OnOpenGUIClosed(InventoryCloseEvent e){
        if(AutoTP.requestData != AutoTP.BackUpRequestData){
            AutoTP.requestData = AutoTP.BackUpRequestData;
        }
    }
}
