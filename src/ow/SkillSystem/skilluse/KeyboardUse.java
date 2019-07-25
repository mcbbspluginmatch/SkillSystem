package ow.SkillSystem.skilluse;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import lk.vexview.event.KeyBoardPressEvent;
import ow.SkillSystem.Main;
import ow.SkillSystem.data.OnlineData;
import ow.SkillSystem.data.SPlayer;
import ow.SkillSystem.skills.Skill;

public class KeyboardUse implements Listener{

	@EventHandler
	public void onKeyBoardPress(KeyBoardPressEvent event) {
		
		if(event.getEventKeyState()) return;
		
		Player p = event.getPlayer();
		SPlayer player = OnlineData.getSPlayer(p);
		int key = event.getKey();
		Skill skill = player.getKeyBoardSkill(key);
		
		//�ͷż���
		if(skill!=null) {
			player.setSkill(skill);
		}
		
		//���ܰ�����
		if(OnlineData.playersetkey.get(p) != null) {
			
			if(key == 57) {  //��Ϊ�ո��˳���
				p.sendMessage("��0��l�ɹ��˳��󶨣�");
			}else {
				player.addKeyBoardSetting(key, Main.skillsdata.get( OnlineData.playersetkey.get(p) ) );
				p.sendMessage("��0��l�ɹ��󶨣�");
			}

			OnlineData.playersetkey.remove(p);
		}
		
	}
	
	//�������ܰ󶨷���İ���
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		Inventory inv = event.getClickedInventory();
		InventoryView view = event.getView();
		
		if(view.getTitle().contains("[����ϵͳ]������") && event.getWhoClicked() instanceof Player) {
			
			ItemStack item = inv.getItem(event.getSlot());
			if(item == null || !item.getItemMeta().hasLore()) return;
			
			String name = item.getItemMeta().getDisplayName();
			Player player = (Player) event.getWhoClicked();
			
			if(item.getItemMeta().getLore().get(1).contains("δ��") && event.getClick().equals(ClickType.LEFT)) {
				//�����ʼ��
				OnlineData.playersetkey.put(player, name);
				player.sendMessage("��0��l�밴������Ҫ�󶨵İ���");
				
			}else if(item.getItemMeta().getLore().get(1).contains("�󶨰���") && event.getClick().equals(ClickType.RIGHT)) {
				//�Ҽ����Խ����
				OnlineData.getSPlayer(player).removeKeyBoardSkill( Main.skillsdata.get(name) );
				player.sendMessage("��0��l�ɹ������");
				
			}
			
			player.closeInventory();
			event.setCancelled(true);
		}
	}
	
}
