package ow.SkillSystem.skills;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class SkillUtil {
	/**
	 *  ����Ŀ�����ﵽ����Ҵ�
	 * @param user  Ŀ�����
	 * @param target  ������������
	 */
	public void pull(Player user , LivingEntity target , double arg) { 
		Location locuser = user.getEyeLocation();
		Location loctarget = target.getEyeLocation();
		Vector vec = locuser.subtract(loctarget).toVector().normalize();
		target.setVelocity(vec.multiply(arg));
	}
	
	/**
	 *   ����Ŀ��
	 * @param user  Ŀ�����
	 * @param target �����˵�����
	 */
	public void pushBack(Player user , LivingEntity target , double arg) {
		Location locuser = user.getEyeLocation();
		Location loctarget = target.getEyeLocation();
		Vector vec = loctarget.subtract(locuser).toVector().normalize();
		target.setVelocity(vec.multiply(arg));
	}
	
	/**
	 * ʹĿ�����
	 * @param target Ŀ��
	 * @param arg
	 */
	public void jump(LivingEntity target , double arg) {
		Location loca = target.getEyeLocation().clone();
		Location locb = loca.clone();
		loca.setY(loca.getY() + 1);
		Vector vec = loca.subtract(locb).toVector().normalize();
		target.setVelocity(vec.multiply(arg));
	}
	
	/**
	 * ��ĳ����ָ��ķ����Ծһ�ξ���
	 * @param entity ʩ������
	 * @param length ��Ծ����
	 */
	public void charge(LivingEntity entity , double length) {
		Vector sight = entity.getEyeLocation().getDirection().clone();
		entity.setVelocity(sight.multiply(length));
	}
	
	
	/**
	 * ��ȡ���ָ�������
	 * @param p ���
	 * @return �����򷵻ظ�������޷���null
	 */
	public LivingEntity getTargetEntity(Player p) { 
		List<Entity> entities = p.getNearbyEntities(3.0, 3.0, 3.0);
		for(Entity en : entities) {
			if(en instanceof LivingEntity) {
				LivingEntity len = (LivingEntity) en ;
				Location end = len.getEyeLocation().clone();
				Location start = p.getEyeLocation().clone();
				Vector v = end.subtract(start).toVector();
				Vector sight = p.getEyeLocation().getDirection();
				
				double length = Math.cos(v.angle(sight));
				if(length >0.91) {
					return len;
				}
			}
		}
		return null;
	}
	
}
