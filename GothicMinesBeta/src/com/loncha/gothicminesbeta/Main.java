package com.loncha.gothicminesbeta;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Cauldron;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldguard.bukkit.BukkitUtil;
import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin implements Listener{
	
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		Block b = e.getBlockPlaced();
		
		if (inRegion(p.getLocation(),"mina1") || inRegion(p.getLocation(),"mina2") || inRegion(p.getLocation(),"mina3") || inRegion(p.getLocation(),"mina4")) {
			if (!b.getType().toString().equals("LOG") && !b.getType().toString().equals("LOG_2") && !b.getType().toString().equals("TORCH")) {
				if (!p.isOp()) {
					e.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		e.setExpToDrop(0);
		Block b = e.getBlock();
		Player p = e.getPlayer();
		
		ItemStack itemInHand = p.getInventory().getItemInMainHand();
		
		if (inRegion(p.getLocation(),"mina1") || inRegion(p.getLocation(),"mina2") || inRegion(p.getLocation(),"mina3") || inRegion(p.getLocation(),"mina4")) {
			if (inRegion(b.getLocation(),"mina1") || inRegion(b.getLocation(),"mina2") || inRegion(b.getLocation(),"mina3") || inRegion(b.getLocation(),"mina4")) {
				if (b.getType() == Material.STONE) {
					if (itemInHand.getType() == Material.IRON_PICKAXE || itemInHand.getType() == Material.STONE_PICKAXE) {
		
						switch((int)b.getData()) {
							case 0:
								b.setData((byte) 1);
								e.setCancelled(true);
								break;
								
							case 1:
								b.setData((byte) 3);
								e.setCancelled(true);
								break;
								
							case 3:
								b.setData((byte) 5);
								e.setCancelled(true);
								break;
								
							case 5:
								int rand = (int)(Math.random() * 1000) + 1; 
		
								if (rand > 0 && rand < 150) {
									e.setCancelled(true);	
									b.setType(Material.LAPIS_ORE);
									
								} else if (rand > 149 && rand < 240) {
									e.setCancelled(true);
									b.setType(Material.COAL_ORE);
									
								} else if (rand > 239 && rand <= 250) {
									e.setCancelled(true);
									b.setType(Material.IRON_ORE);
									
								} else {
									e.setCancelled(true);
									b.setType(Material.AIR);
									
									//NÚMERO ALEATORIO PARA QUE SALGAN LOS 4 TIPOS DE PIEDRA
									int randPiedra = (int)(Math.random() * 1000) + 1;
	
									if (randPiedra > 0 && randPiedra < 700) {
										b.getWorld().dropItem(b.getLocation(), new ItemStack(Material.STONE,1));
										
									} else if (randPiedra > 699 && randPiedra < 800) {
										b.getWorld().dropItem(b.getLocation(), new ItemStack(Material.STONE,1,(short)1));
										
									} else if (randPiedra > 799 && randPiedra < 900) {
										b.getWorld().dropItem(b.getLocation(), new ItemStack(Material.STONE,1,(short)3));
										
									} else if (randPiedra > 899 && randPiedra <= 1000) {
										b.getWorld().dropItem(b.getLocation(), new ItemStack(Material.STONE,1,(short)5));
									}
									
									if (itemInHand.getDurability() < itemInHand.getType().getMaxDurability()) {
										itemInHand.setDurability((short) (itemInHand.getDurability()+1));
									} else {
										p.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
									}
								}
								
								break;
						}
					} else {
						e.setCancelled(true);
						p.sendMessage(ChatColor.DARK_RED+"Estás en la mina, utiliza un pico.");
					}
					
				} else if (b.getType() == Material.LAPIS_ORE) {
					e.setCancelled(true);
					b.setType(Material.AIR);
					
					ItemStack drop = new ItemStack(Material.LAPIS_ORE);
					ItemMeta meta = drop.getItemMeta();
					
					meta.setDisplayName("§fMena de mineral magico");
					drop.setItemMeta(meta);
					b.getWorld().dropItem(b.getLocation(), drop);
					
					itemInHand.setDurability((short) (itemInHand.getDurability()+1));	
				} else if (b.getType() == Material.IRON_ORE) {
					e.setCancelled(true);
					b.setType(Material.AIR);
					
					ItemStack drop = new ItemStack(Material.IRON_ORE);
	
					b.getWorld().dropItem(b.getLocation(), drop);
					
					if (itemInHand.getDurability() < itemInHand.getType().getMaxDurability()) {
						itemInHand.setDurability((short) (itemInHand.getDurability()+1));
					} else {
						p.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
					}
				} else if (b.getType() == Material.COAL_ORE) {
					e.setCancelled(true);
					b.setType(Material.AIR);
					
					ItemStack drop = new ItemStack(Material.COAL);
	
					b.getWorld().dropItem(b.getLocation(), drop);
					
					if (itemInHand.getDurability() < itemInHand.getType().getMaxDurability()) {
						itemInHand.setDurability((short) (itemInHand.getDurability()+1));
					} else {
						p.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
					}	
				} else if (b.getType() == Material.LOG) {
					e.setCancelled(true);
					b.setType(Material.AIR);
					
					ItemStack drop = new ItemStack(Material.LOG);
	
					b.getWorld().dropItem(b.getLocation(), drop);
					
					if (itemInHand.getDurability() < itemInHand.getType().getMaxDurability()) {
						itemInHand.setDurability((short) (itemInHand.getDurability()+1));
					} else {
						p.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
					}	
				} else if (b.getType() == Material.LOG_2) {
					e.setCancelled(true);
					b.setType(Material.AIR);
					
					ItemStack drop = new ItemStack(Material.LOG_2);
	
					b.getWorld().dropItem(b.getLocation(), drop);
					
					if (itemInHand.getDurability() < itemInHand.getType().getMaxDurability()) {
						itemInHand.setDurability((short) (itemInHand.getDurability()+1));
					} else {
						p.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
					}	
				} else if (b.getType() == Material.TORCH) {
					e.setCancelled(true);
					b.setType(Material.AIR);
					
					ItemStack drop = new ItemStack(Material.TORCH);
	
					b.getWorld().dropItem(b.getLocation(), drop);
					
					if (itemInHand.getDurability() < itemInHand.getType().getMaxDurability()) {
						itemInHand.setDurability((short) (itemInHand.getDurability()+1));
					} else {
						p.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
					}	
				} else {
					e.setCancelled(true);
				}
			}

		} else if (inRegion(b.getLocation(),"mina1") || inRegion(b.getLocation(),"mina2") || inRegion(b.getLocation(),"mina3") || inRegion(b.getLocation(),"mina4")) {
			p.sendMessage(ChatColor.DARK_RED+"Entra en la mina para picar.");
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onFurnaceBurn(FurnaceBurnEvent e) {
		ItemStack combustible = e.getFuel();
		
		if (combustible.getType() == Material.BLAZE_ROD) {
			e.setCancelled(true);
		}
	}
	
	public static boolean inRegion(Location loc, String region) {

	    Vector v = BukkitUtil.toVector(loc);
	    RegionManager manager = WGBukkit.getRegionManager(loc.getWorld());
	    ProtectedRegion rg = manager.getRegion(region);
	    ApplicableRegionSet set = manager.getApplicableRegions(v);
	    
	    if (set.getRegions().contains(rg)) {
	    	return true;
	    	
	    } else {
	    	return false;
	    }

	}
}
