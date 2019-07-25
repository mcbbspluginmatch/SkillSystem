package ow.SkillSystem.data;

import java.util.HashMap;
import java.util.Iterator;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import ow.SkillSystem.Main;

public class OnlineData {
	
	//����������˺�    ��ʽ��   ����/�˺�����/����ʱ��  �����������˺��������Ǽ���
	public static HashMap<LivingEntity,String> damageset = new HashMap<>();
	
	//�������ܵ��˺�   ��ʽ��   ����/�˺�����/����ʱ��      
	//��Ϊ0�����޵У����������������˺��������Ǽ��������˺�
	public static HashMap<LivingEntity,String> damagedset = new HashMap<>();
	
	//����б�
	public static HashMap<Player,SPlayer> players = new HashMap<>();
	
	//��Ұ󶨼��ܵı��
	public static HashMap<Player , String> playersetkey = new HashMap<>();
	
	//����ĳ��������˺��趨
	public static double getDamage(LivingEntity entity) {
		if(damageset.get(entity)==null) return 0;
		
		String[] parts = damageset.get(entity).split("/");
		return Main.util.getDoubleNumber(parts[0]);
	}
	
	//�����������˺��趨
	public static void addDamageSet(LivingEntity entity , double ds , int time) {
		damageset.put(entity, ds+"/"+time);
	}
	
	//�޸��������˺��趨����ԭ���������ӣ�
	public static void setDamageSet(LivingEntity entity , double dsplus) {
		String[] parts = damageset.get(entity).split("/");
		int time = Main.util.getIntNumber(parts[1]);
		double ds = Main.util.getDoubleNumber(parts[0]) + dsplus;
		damageset.put(entity, ds+"/"+time);
	}
	
	/*===============================================================*/
	
	//���������˺��ĵ���
	public static String getDamaged(LivingEntity entity) {
		if(damagedset.get(entity) == null) return null;
		
		return damagedset.get(entity).split("/")[0];
	}
	
	//�������������˺��趨
	public static void addDamagedSet(LivingEntity entity , double ds , int time) {
		damagedset.put(entity, ds+"/"+time);
	}
	
	//�޸����������˺��趨����ԭ���������ӣ�
	public static void setDamagedSet(LivingEntity entity , double dsplus) {
		String[] parts = damagedset.get(entity).split("/");
		int time = Main.util.getIntNumber(parts[1]);
		double ds = Main.util.getDoubleNumber(parts[0]) + dsplus;
		damagedset.put(entity, ds+"/"+time);
	}
	
	/*===============================================================*/
	
	//ʱ������
	public static void setTime1() {
		
		Iterator<LivingEntity> it =  damageset.keySet().iterator();
		
		while(it.hasNext()) {
			LivingEntity entity = (LivingEntity) it.next();
			String[] parts = damageset.get(entity).split("/");
			double ds = Main.util.getDoubleNumber(parts[0]);
			int time = Main.util.getIntNumber(parts[1]) - 1;
			
			if(time == 0) {
				damageset.remove(entity);
			}else {
				damageset.put(entity, ds+"/"+time);
			}
		}
		
	}
	
	public static void setTime2() {
		
		Iterator<LivingEntity> it =  damagedset.keySet().iterator();
		
		while(it.hasNext()) {
			LivingEntity entity = (LivingEntity) it.next();
			String[] parts = damagedset.get(entity).split("/");
			double ds = Main.util.getDoubleNumber(parts[0]);
			int time = Main.util.getIntNumber(parts[1]) - 1;
			
			if(time == 0) {
				damagedset.remove(entity);
			}else {
				damagedset.put(entity, ds+"/"+time);
			}
		}
		
	}
	
	//��ȡsplayer
	public static SPlayer getSPlayer(Player p) {
		return players.get(p);
	}
	
}
