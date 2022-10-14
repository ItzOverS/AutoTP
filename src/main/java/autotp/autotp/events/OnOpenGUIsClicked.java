package autotp.autotp.events;

import autotp.autotp.AutoTP;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Objects;

public class OnOpenGUIsClicked implements Listener {
    @EventHandler
    public void On_TPA_GUI_CLICK(InventoryClickEvent e){
        if(e.getView().getTitle().equalsIgnoreCase(ChatColor.GREEN + "send a teleport request to ")){
            if(Objects.requireNonNull(e.getCurrentItem()).getType() == Material.PLAYER_HEAD) {
                Object[] OnlinePlayer = AutoTP.getPlugin().getServer().getOnlinePlayers().toArray();
                Object[] OnlinePlayers = new Object[OnlinePlayer.length];
                Player ClickedPlayer = (Player) e.getWhoClicked();
                int index = 0;
                for(Object TargetPlayer: OnlinePlayer){
                    if((Player)TargetPlayer != ClickedPlayer){
                        OnlinePlayers[index] = TargetPlayer;
                        index++;
                    }
                }
                Player Target = (Player) OnlinePlayers[e.getSlot()];
                e.setCancelled(true);
                if(AutoTP.requestData.containsKey(ClickedPlayer)) {
                    if (AutoTP.requestData.get(ClickedPlayer) == Target) {
                        ClickedPlayer.sendMessage(ChatColor.RED + "You already sended a request to " + ChatColor.GREEN + Target.getName());
                        ClickedPlayer.closeInventory();
                        return;
                    }
                }
                ClickedPlayer.closeInventory();
                assert Target != null;
                ClickedPlayer.sendMessage(ChatColor.GREEN + "sended a request to " + Target.getName());
                Target.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4" + ClickedPlayer.getName() + " &6sended you a teleport request!"));
                Target.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9 To accept use: &6 /tpaccept"));
                Target.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9 To decline use : &6 /tpdeny"));
                AutoTP.requestData.put(ClickedPlayer, Target);
                AutoTP.ReversedRequestData.put(Target, ClickedPlayer);
                AutoTP.BackUpRequestData.put(ClickedPlayer, Target);
                AutoTP.rangeOfHashMaps++;
            }
        }
        else if (e.getView().getTitle().equalsIgnoreCase(ChatColor.GREEN + "accept tp request from")){
            if(Objects.requireNonNull(e.getCurrentItem()).getType() == Material.PLAYER_HEAD) {
                Object[] OnlinePlayer = AutoTP.getPlugin().getServer().getOnlinePlayers().toArray();
                Object[] OnlinePlayers = new Object[OnlinePlayer.length];
                Player ClickedPlayer = (Player) e.getWhoClicked();
                int index = 0;
                for(Object TargetPlayer: OnlinePlayer){
                    if((Player)TargetPlayer != ClickedPlayer){
                        OnlinePlayers[index] = TargetPlayer;
                        index++;
                    }
                }
                Player Target = (Player) OnlinePlayers[e.getSlot()];
                e.setCancelled(true);
                AutoTP.requestData.remove(Target);
                AutoTP.ReversedRequestData.remove(ClickedPlayer);
                AutoTP.rangeOfHashMaps--;
                Target.teleport(ClickedPlayer.getLocation());
                Target.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Teleport request accepted! Teleporting..."));
                ClickedPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6You accept the request... &7" + Target.getName() + "&6 teleported!"));
                ClickedPlayer.closeInventory();
            }
        }
        else if (e.getView().getTitle().equalsIgnoreCase(ChatColor.GREEN + "deny tp request from")){
            if(Objects.requireNonNull(e.getCurrentItem()).getType() == Material.PLAYER_HEAD) {
                Object[] OnlinePlayer = AutoTP.getPlugin().getServer().getOnlinePlayers().toArray();
                Object[] OnlinePlayers = new Object[OnlinePlayer.length];
                Player ClickedPlayer = (Player) e.getWhoClicked();
                int index = 0;
                for(Object TargetPlayer: OnlinePlayer){
                    if((Player)TargetPlayer != ClickedPlayer){
                        OnlinePlayers[index] = TargetPlayer;
                        index++;
                    }
                }
                Player Target = (Player) OnlinePlayers[e.getSlot()];
                e.setCancelled(true);
                AutoTP.requestData.remove(Target);
                AutoTP.ReversedRequestData.remove(ClickedPlayer);
                AutoTP.rangeOfHashMaps--;
                Target.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Teleport request denied!"));
                ClickedPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6You denied the request from &7" + Target.getName()));
                ClickedPlayer.closeInventory();
            }
        }
    }
}
