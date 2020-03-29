package com.loncha.gothicminesbeta;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldguard.bukkit.BukkitUtil;
import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class Main extends JavaPlugin implements Listener{
	
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		Block b = e.getBlock();
		Player p = e.getPlayer();
		
		if (inRegion(p.getLocation(),"mina1") || inRegion(p.getLocation(),"mina2") || inRegion(p.getLocation(),"mina3") || inRegion(p.getLocation(),"mina4")) {
			if (b.getType() == Material.STONE) {

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

						if (rand > 0 && rand < 100) {
							e.setCancelled(true);	
							b.setType(Material.LAPIS_ORE);
							
						} else if (rand > 100 && rand < 130) {
							e.setCancelled(true);
							b.setType(Material.COAL_ORE);
							
						} else if (rand > 130 && rand < 140) {
							e.setCancelled(true);
							b.setType(Material.IRON_ORE);
							
						} else {
							e.setCancelled(true);
							b.setType(Material.AIR);
							
							b.getWorld().dropItem(b.getLocation(), new ItemStack(Material.STONE));
						}
						
						break;
				}
			} else if (b.getType() == Material.LAPIS_ORE) {
				ItemStack itemInHand = p.getInventory().getItemInMainHand();
				
				if (itemInHand.getType() == Material.IRON_PICKAXE || itemInHand.getType() == Material.STONE_PICKAXE) {
					e.setCancelled(true);
					b.setType(Material.AIR);
					
					ItemStack drop = new ItemStack(Material.LAPIS_ORE);
					ItemMeta meta = drop.getItemMeta();
					
					meta.setDisplayName("§fMena de mineral magico");
					drop.setItemMeta(meta);
					b.getWorld().dropItem(b.getLocation(), drop);
				}		
			}
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
