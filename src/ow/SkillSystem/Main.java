package ow.SkillSystem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import ow.SkillSystem.Thread.*;
import ow.SkillSystem.data.ConfigHandle;
import ow.SkillSystem.data.OnlineData;
import ow.SkillSystem.data.SPlayer;
import ow.SkillSystem.listener.*;
import ow.SkillSystem.skills.Skill;
import ow.SkillSystem.skilluse.*;

public class Main extends JavaPlugin{
	
	public static Main plugin;
	public static Util util;
	public static ConfigHandle handle;
	
	public static HashMap<String,Skill> skillsdata = new HashMap<>();
	public static List<Skill> skills = new ArrayList<>();
	
	public static HashMap<String,ItemStack> items = new HashMap<>();
	
	//�Ƿ񿪷ż������ּ���������
	public static boolean isKeyBoard = true;
	
	public void onEnable() {
		plugin = this;
		util = new Util();
		
		try {
			handle = new ConfigHandle();
			handle.loadItems();
			handle.loadSkills();
			getLogger().info("�����ļ����سɹ���");
		} catch (IOException e) {
			e.printStackTrace();
			getLogger().info("������������ļ�ʱ���ִ�����������ļ���");
		}
		
		//��ʼ���������
		initPlayer();
		getLogger().info("��ʼ�����������ң�");
		
		runThread();
		
		//���ؼ�����
		Bukkit.getPluginManager().registerEvents(new ItemUse() , this);
		Bukkit.getPluginManager().registerEvents(new KeyboardUse(), this);
		Bukkit.getPluginManager().registerEvents(new LivingEntityDamageListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
		
		getLogger().info("����ϵͳ������ɣ�");
	}
	
	private void initPlayer() {
		Iterator<? extends Player> itn = Bukkit.getServer().getOnlinePlayers().iterator();
		
		while(itn.hasNext()) {
			Player p = itn.next();
			SPlayer player = new SPlayer(p);
			
			try {
				handle.loadPlayerYML(player);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			OnlineData.players.put(p, player);
		}
		
	}

	public void onDisable() {
		getLogger().info("����ϵͳ���ڱ�������...");
		
		Iterator<? extends Player> itn = Bukkit.getServer().getOnlinePlayers().iterator();
		
		while(itn.hasNext()) {
			Player p = itn.next();
			SPlayer player = OnlineData.players.get(p);
			
			player.saveKeyBoard();
		}
		
	}
	
	//�����߳�
	public void runThread() {
		
		Thread pthread = new SkillThread();
		Thread dthread = new DamageThread();
		Thread ddthread = new DamagedThread();
		
		pthread.start();
		dthread.start();
		ddthread.start();
	}
	
	public boolean onCommand(CommandSender sender,Command cmd,String Label,String[] args){
		if(cmd.getName().equalsIgnoreCase("skillsystem")) {
			
			if(args.length == 3 && args[0].equalsIgnoreCase("give")) {
				Player p = getServer().getPlayer(args[1]);
				ItemStack item = items.get(args[2]);
				
				if(p != null) {
					p.getInventory().addItem(item);
					sender.sendMessage("�ɹ�����");
				}else {
					sender.sendMessage("����Ҳ�����");
				}
				
			}else if(sender instanceof Player && args.length == 0) {
				Player player = (Player) sender;
				util.createInventory(player);
			}
			
		}else if(cmd.getName().equalsIgnoreCase("skill")) {
			
			if(args.length == 1 && sender instanceof Player) {
				Player p = (Player) sender;
				SPlayer player = OnlineData.getSPlayer(p);
				Skill skill = Main.skillsdata.get(args[0]);
				
				player.setSkill(skill);
			}
			
		}
		return true;
	}

}
