package autotp.autotp.commands;

import autotp.autotp.AutoTP;
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

import java.util.HashMap;

import static java.awt.SystemColor.text;

public class tpdeny implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            if (args.length == 0) {
                Player[] OnlinePlayers = Search((Player) sender);
                Inventory inv = Bukkit.createInventory(null, 54, ChatColor.GREEN + "deny tp request from");
                ItemStack[] Heads = new ItemStack[OnlinePlayers.length];
                int index = 0;
                for (int i = 0; i < OnlinePlayers.length; i++) {
                    if (OnlinePlayers[i] != sender) {
                        ItemMeta headData = new ItemStack(Material.PLAYER_HEAD, 1).getItemMeta();
                        Player p = OnlinePlayers[i];
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
            else {
                Player ClickedPlayer = (Player) sender;
                int index = 0;
                Player Target = AutoTP.getPlugin().getServer().getPlayer(args[0]);
                if (AutoTP.requestData.containsKey(Target)) {
                    if (AutoTP.requestData.get(Target) == ClickedPlayer) {
                        AutoTP.requestData.remove(Target);
                        AutoTP.ReversedRequestData.remove(ClickedPlayer);
                        AutoTP.BackUpRequestData.remove(Target);
                        AutoTP.rangeOfHashMaps--;
                        Target.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Teleport request denied!"));
                        ClickedPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6You denied the request from &7" + Target.getName()));
                    }
                }
            }
        }
        else{
            sender.sendMessage(ChatColor.RED + "This command can't execute from console or CommandBlock");
        }
        return false;
    }
    private Player[] Search (Player text) {
        Player[] x = new Player[AutoTP.rangeOfHashMaps];
        HashMap<Player, Player> AutoSaveHashMap1 = AutoTP.requestData;
        HashMap<Player, Player> AutoSaveHashMap2 = AutoTP.ReversedRequestData;
        HashMap<Player, Player> newHash2 = AutoTP.ReversedRequestData;
        HashMap<Player, Player> newHash = AutoTP.requestData;
        for(int i = 0; i < x.length; i++){
            if(newHash.containsValue(text)){
                Player key = newHash2.get(text);
                x[i] = key;
                newHash.remove(key);
                newHash2.remove(text);
            }
        }
        AutoTP.requestData = AutoSaveHashMap1;
        AutoTP.ReversedRequestData = AutoSaveHashMap2;
        return x;
    }
}
