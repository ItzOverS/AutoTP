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
import java.util.Objects;

public class tpaccept implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player[] OnlinePlayers = Search((Player)sender);
            Inventory inv = Bukkit.createInventory(null, 54, ChatColor.GREEN + "accept tp request from");
            ItemStack[] Heads = new ItemStack[OnlinePlayers.length];
            int index = 0;
            for(int i = 0; i < OnlinePlayers.length; i++){
                if(OnlinePlayers[i] != sender) {
                    ItemMeta headData = new ItemStack(Material.PLAYER_HEAD, 1).getItemMeta();
                    Player p = OnlinePlayers[i];
                    Heads[i] = new ItemStack(Material.PLAYER_HEAD, 1);
                    headData.setDisplayName(p.getName());
                    Heads[i].setItemMeta(headData);
                    inv.setItem(index, Heads[i]);
                    index++;
                }
            }
            ((Player) sender).openInventory(inv);
        }
        else{
            sender.sendMessage(ChatColor.RED + "This command can't execute from console or CommandBlock");
        }
        return false;
    }
    public Player[] Search (Player text) {
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
    /*
    public String[] Search (Object[] list, String text){
        String[] output = new String[list.length];
        int index = 0;
        for(Object target: list){
            if(Objects.equals(target.toString(), text)){
                output[index] = target.toString();
                index++;
            }
        }
        return SortNulls(output);
    }
    public String[] SortNulls (String[] list){
        String[] Output;
        int index = 0;
        for(String x: list){
            if(Objects.equals(x, null)){
                index++;
            }
        }
        int count = list.length - index;
        Output = new String[count];
        index = 0;
        for(String x: list){
            if(!Objects.equals(x, null)){
                Output[index] = x;
                index++;
            }
        }
        return Output;
    }
     */
}