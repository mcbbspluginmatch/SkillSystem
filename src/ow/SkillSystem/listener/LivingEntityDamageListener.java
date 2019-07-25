package ow.SkillSystem.listener;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import ow.SkillSystem.data.OnlineData;

public class LivingEntityDamageListener implements Listener{
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event) {
		
		//���ݹ����߽����˺�����
		if(event.getDamager() instanceof LivingEntity) {
			
			LivingEntity entity = (LivingEntity) event.getDamager();
			double damageset = event.getDamage()+OnlineData.getDamage(entity);
			
			event.setDamage(damageset < 0 ? 0 : damageset);
			
		}else if(event.getDamager() instanceof Arrow){
			
			Arrow arrow = (Arrow) event.getDamager();
			
			if(arrow.getShooter() instanceof LivingEntity) {
				LivingEntity entity = (LivingEntity) arrow.getShooter();
				double damageset = event.getDamage()+OnlineData.getDamage(entity);
				
				event.setDamage(damageset < 0 ? 0 : damageset);
		    }  
			
		}
		
		//���������˺�
		if(event.getEntity() instanceof LivingEntity) {
			
			LivingEntity entity = (LivingEntity) event.getEntity();
			String damage = OnlineData.getDamaged(entity);
			
			if(damage != null) {
				double d = Double.parseDouble(damage);
				
				event.setDamage(event.getDamage()+d < 0 ? 0 : event.getDamage()+d);
			}
			
		}
		
		event.getDamager().sendMessage("�������"+event.getDamage()+"���˺�");
		
	}
	
	@EventHandler
	public void onAllDamagae(EntityDamageEvent event) {
		//�޵��趨
		if(event.getEntity() instanceof LivingEntity) {
			
			LivingEntity entity = (LivingEntity) event.getEntity();
			String damage = OnlineData.getDamaged(entity);
			
			if(damage != null) {
				double d = Double.parseDouble(damage);
				if(d == 0) {
					event.setCancelled(true);
				}
			}
			
		}
	}

}
