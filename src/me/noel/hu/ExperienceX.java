package me.noel.hu;

import java.util.HashMap;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;
import org.bukkit.util.Vector;



public class ExperienceX extends JavaPlugin implements Listener, CommandExecutor{

    HashMap<Player,Entity> target = new HashMap<Player, Entity>();

    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[" + ChatColor.DARK_GREEN + "ExperienceX" + ChatColor.GREEN + "] " + ChatColor.DARK_GREEN + "----------------------------------------------");
        getServer().getConsoleSender().sendMessage("§a[§2ExperienceX§a]     §2+===============+");
        getServer().getConsoleSender().sendMessage("§a[§2ExperienceX§a]     §2|  ExperienceX  |");
        getServer().getConsoleSender().sendMessage("§a[§2ExperienceX§a]     §2|---------------|");
        getServer().getConsoleSender().sendMessage("§a[§2ExperienceX§a]     §2|     Plugin    |");
        getServer().getConsoleSender().sendMessage("§a[§2ExperienceX§a]     §2+===============+");
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[" + ChatColor.DARK_GREEN + "ExperienceX" + ChatColor.GREEN + "] " + ChatColor.DARK_GREEN + "----------------------------------------------");
        getServer().getConsoleSender().sendMessage("§a[§2ExperienceX§a]     §2Current version: 1.3");
        getServer().getConsoleSender().sendMessage("§a[§2ExperienceX§a]     §2This is the latest version!");
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[" + ChatColor.DARK_GREEN + "ExperienceX" + ChatColor.GREEN + "] " + ChatColor.DARK_GREEN + "----------------------------------------------");
    }
	/*
	private void getKozeliBlockok(Location loc) {
		Location temploc = loc;
		temploc.add(1, 0, 0).getBlock().getType() == Material.leave
	}

	public void BrekoBrekoGyerekekittNosika(BlockBreakEvent e) {
		if(e.getPlayer().getItemInHand() != null && e.getPlayer().getItemInHand().getAmount() > 0) {
			if(e.getPlayer().getInventory().getItemInMainHand() == new ItemStack(Material.DIAMOND_AXE)) {
				Location loc = e.getBlock().getLocation();
			}
		}
	}
	*/

    @EventHandler
    public void onPlayerAttak(EntityDamageByEntityEvent event) {
        if(event.getDamager() instanceof Player) {
            Player user = (Player) event.getDamager();
            if(user.getItemInHand() != null) {
                if(user.getItemInHand().getType() != Material.AIR) {
                    if(user.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§6MobStick")) {
                        if(target.get(user) != null) {
                            event.setCancelled(true);
                            event.getEntity().setPassenger(target.get(user));
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent event) {
        Player user = event.getPlayer();
        if(user.getItemInHand() != null) {
            if(user.getItemInHand().getType() != Material.AIR) {
                if(user.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§6MobStick")) {
                    target.put(event.getPlayer(), event.getRightClicked());
                    user.sendMessage("§aMob selected!");
                }
            }
        }
    }

    @EventHandler
    public void Pdeath(PlayerDeathEvent e) {
        ItemStack bot = new ItemStack(Material.BLAZE_ROD);
        ItemMeta meta = bot.getItemMeta();
        meta.setDisplayName("§6Liferod");
        bot.setItemMeta(meta);
        for(ItemStack stack : e.getEntity().getInventory().getContents()) {
            if(stack.getItemMeta().getDisplayName().equalsIgnoreCase("§6Liferod")) {
                e.getDrops().clear();
                e.setKeepInventory(true);
                stack.setAmount(stack.getAmount()-1);
            }
        }
    }



    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(label.equalsIgnoreCase("exgm")){
            Player p = (Player) sender;
            if(p.hasPermission("experiencex.gamemode")){
                if(args.length == 1){
                    if(args[0].equalsIgnoreCase("0") || args[0].equalsIgnoreCase("survival")){

                        p.sendMessage(p.getDisplayName() + "§3 game mode switched to this: §bsurvival§3.");
                        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                        p.setGameMode(GameMode.SURVIVAL);
                    }else if(args[0].equalsIgnoreCase("1") || args[0].equalsIgnoreCase("creative")){

                        p.sendMessage(p.getDisplayName() + "§3 game mode switched to this: §bcreative§3.");
                        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                        p.setGameMode(GameMode.CREATIVE);
                    }else if(args[0].equalsIgnoreCase("2") || args[0].equalsIgnoreCase("adventure")){

                        p.sendMessage(p.getDisplayName() + "§3 game mode switched to this: §badventure§3.");
                        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                        p.setGameMode(GameMode.ADVENTURE);
                    }else if(args[0].equalsIgnoreCase("3") || args[0].equalsIgnoreCase("spectator")) {

                        p.sendMessage(p.getDisplayName() + "§3 game mode switched to this: §bspectator§3.");
                        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                        p.setGameMode(GameMode.SPECTATOR);
                    }
                }else {
                    p.sendMessage("§b------------------\nGamemodes: \n/exgm 0\n/exgm 1\n/exgm 2\n/exgm 3\n------------------");
                }
            }
        }
        if(label.equalsIgnoreCase("exdifficulty")){
            Player p = (Player) sender;
            if(p.hasPermission("experiencex.difficulty")){
                if(args.length == 1){
                    if(args[0].equalsIgnoreCase("peaceful") || args[0].equalsIgnoreCase("0")){

                        p.sendMessage("§3Difficulty is changed to this: §bpeaceful§3.");
                        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                        p.getWorld().setDifficulty(Difficulty.PEACEFUL);
                    }else if(args[0].equalsIgnoreCase("easy") || args[0].equalsIgnoreCase("1")){

                        p.sendMessage("§3Difficulty is changed to this: §beasy§3.");
                        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                        p.getWorld().setDifficulty(Difficulty.EASY);
                    }else if(args[0].equalsIgnoreCase("normal") || args[0].equalsIgnoreCase("2")){

                        p.sendMessage("§3Difficulty is changed to this: §bnormal§3.");
                        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                        p.getWorld().setDifficulty(Difficulty.NORMAL);
                    }else if(args[0].equalsIgnoreCase("hard") || args[0].equalsIgnoreCase("3")){

                        p.sendMessage("§3Difficulty is changed to this: §bhard§3.");
                        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                        p.getWorld().setDifficulty(Difficulty.HARD);
                    }
                }else {
                    p.sendMessage("§b------------------\nDifficulty: \n/exdifficulty 0\n/exdifficulty 1\n/exdifficulty 2\n/exdifficulty 3\n------------------");
                }
            }
        }
        if(label.equalsIgnoreCase("extime")){
            Player p = (Player) sender;
            if(p.hasPermission("experiencex.timeset")){
                if(args.length == 1){
                    if(args[0].equalsIgnoreCase("0") || args[0].equalsIgnoreCase("day")){

                        p.sendMessage("§3Game time is switched to: §bday§3.");
                        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                        p.getWorld().setTime(1000);
                    }else if(args[0].equalsIgnoreCase("1") || args[0].equalsIgnoreCase("night")){

                        p.sendMessage("§3Game time is switched to: §bnight§3.");
                        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                        p.getWorld().setTime(18000);
                    }
                }else {
                    p.sendMessage("§b------------------\nTimes: \n/extime day\n/extime night\n------------------");
                }
            }
        }
        if(label.equalsIgnoreCase("exhelp")) {
            sender.sendMessage(ChatColor.GREEN + "----------- | " + ChatColor.DARK_GREEN + "[ExperienceX] " + ChatColor.GREEN + "| -----------");
            sender.sendMessage(ChatColor.DARK_GREEN + "/exhelp: " + ChatColor.GREEN + "Shows this help list");
            sender.sendMessage(ChatColor.DARK_GREEN + "/extroll: " + ChatColor.GREEN + "Troll the players");
            sender.sendMessage(ChatColor.DARK_GREEN + "/mobstick: " + ChatColor.GREEN + "Gives you a stick");
            sender.sendMessage(ChatColor.DARK_GREEN + "/liferod: " + ChatColor.GREEN + "Gives you a liferod");
            sender.sendMessage(ChatColor.DARK_GREEN + "/playerinfo: " + ChatColor.GREEN + "Playerinfo of you");
            sender.sendMessage(ChatColor.DARK_GREEN + "/exgm: " + ChatColor.GREEN + "Changes your gamemode");
            sender.sendMessage(ChatColor.DARK_GREEN + "/exdifficulty: " + ChatColor.GREEN + "Changes game difficulty");
            sender.sendMessage(ChatColor.DARK_GREEN + "/extime: " + ChatColor.GREEN + "Changes game time");
            sender.sendMessage(ChatColor.GREEN + "-------------------------------------");
        }
        if(label.equalsIgnoreCase("playerinfo")) {
            sender.sendMessage(ChatColor.DARK_AQUA + "----------------------------------------");
            sender.sendMessage(ChatColor.AQUA + "Name: " + ((Player) sender).getDisplayName());
            sender.sendMessage(ChatColor.AQUA + "Health: " + ((Player) sender).getHealth());
            sender.sendMessage(ChatColor.AQUA + "Food Level: " + ((Player) sender).getFoodLevel());
            sender.sendMessage(ChatColor.DARK_AQUA + "----------------------------------------");
        }
        if(label.equalsIgnoreCase("liferod")) {
            ItemStack bot = new ItemStack(Material.BLAZE_ROD,1);
            ItemMeta meta = bot.getItemMeta();
            meta.setDisplayName("§6Liferod");
            bot.setItemMeta(meta);
            ((Player) sender).getInventory().addItem(bot);
            sender.sendMessage("§aDone :)");
        }
        if(label.equalsIgnoreCase("mobstick")) {

            ItemStack bot = new ItemStack(Material.STICK,1);
            ItemMeta meta = bot.getItemMeta();
            meta.setDisplayName("§6MOBStick");
            bot.setItemMeta(meta);
            ((Player) sender).getInventory().addItem(bot);
            sender.sendMessage("§aDone :)");
        }
        if(label.equalsIgnoreCase("extroll")) {
            if(args.length == 2) {
                if(args[0].equalsIgnoreCase("tnt")) {
                    Player target = Bukkit.getPlayer(args[1]);
                    target.getWorld().spawn(target.getLocation(), TNTPrimed.class);
                    sender.sendMessage("§cDone :) "+target.getDisplayName());
                }else if(args[0].equalsIgnoreCase("lava")) {
                    Player target = Bukkit.getPlayer(args[1]);
                    Location loc = target.getLocation();
                    int x = -1;
                    int z = -1;
                    for(int i = 0;i<9; i++) {
                        if(i%3 == 0) {
                            x++;
                            z=-1;
                        }else {
                            z++;
                        }
                        loc.setX(target.getLocation().getBlockX()+x);
                        loc.setZ(target.getLocation().getBlockZ()+z);
                        loc.getBlock().setType(Material.LAVA);
                        sender.sendMessage("§cDone :) "+target.getDisplayName());
                    }
                }
            }else {
                sender.sendMessage("§c------------------\nCommands: \n/extroll lava <name>\n/extroll tnt <name>\n------------------");
            }
        }
        return false;
    }

}
