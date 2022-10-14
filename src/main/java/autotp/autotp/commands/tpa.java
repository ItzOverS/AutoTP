package autotp.autotp.commands;

import autotp.autotp.AutoTP;
import com.sun.source.tree.CatchTree;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class tpa implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            if(args.length == 0){
                Object[] OnlinePlayers = AutoTP.getPlugin().getServer().getOnlinePlayers().toArray();
                Inventory inv = Bukkit.createInventory(null, 54, ChatColor.GREEN + "send a teleport request to ");
                ItemStack[] Heads = new ItemStack[OnlinePlayers.length];
                int index = 0;
                for(int i = 0; i < OnlinePlayers.length; i++){
                    if(OnlinePlayers[i] != sender) {
                        ItemMeta headData = new ItemStack(Material.PLAYER_HEAD, 1).getItemMeta();
                        Player p = (Player) OnlinePlayers[i];
                        Heads[i] = new ItemStack(Material.PLAYER_HEAD, 1);
                        assert headData != null;
                        headData.setDisplayName(p.getName());
                        Heads[i].setItemMeta(headData);
                        inv.setItem(index, Heads[i]);
                        index++;
                    }
                }
                ((Player) sender).openInventory(inv);
            }
            else{
                if((Player)sender != AutoTP.getPlugin().getServer().getPlayer(args[0])){
                    Player Target = AutoTP.getPlugin().getServer().getPlayer(args[0]);
                    Player ClickedPlayer = (Player)sender;
                    if(AutoTP.requestData.containsKey(ClickedPlayer)) {
                        if (AutoTP.requestData.get(ClickedPlayer) == Target) {
                            assert Target != null;
                            ClickedPlayer.sendMessage(ChatColor.RED + "You already sended a request to " + ChatColor.GREEN + Target.getName());
                            ClickedPlayer.closeInventory();
                            return false;
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
                else{
                    sender.sendMessage(ChatColor.RED + "You can't send teleport request to your self");
                }
            }
        }
        else{
            sender.sendMessage(ChatColor.RED + "This command can't execute from console or CommandBlock");
        }
        return false;
    }
}
