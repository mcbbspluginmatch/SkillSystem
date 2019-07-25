package ow.SkillSystem.skills;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import ow.SkillSystem.Main;
import ow.SkillSystem.data.OnlineData;
import ow.SkillSystem.data.SPlayer;

public class SkillSingleExecution {     
	/*
	 * ���ܵ���ִ��ʾ��
	 * ��ʽ��  ����/Ŀ��/Ч��/ִ�г���ʱ��
	 * None/Self/PotionEffect:Speed:20:1/0
	 * ���������Լ������ٶ�ҩˮЧ���ȼ�1����20��
	 */
  private SkillCondition condition;
  private SkillTarget target;
  private double radius;
  private SkillEffect effect;
  private int duration = -1;
  
  public SkillSingleExecution(String arg) {
	  String[] args = arg.split("/");
	  condition = new SkillCondition(args[0]);
	  setTarget(args[1]);
	  effect = new SkillEffect(args[2]);
	  duration = Main.util.getIntNumber(args[3]);
	  
	  checkDuration();        //����Ƿ��ǿɳ����ļ���ִ��
  }
  
  private void checkDuration() {
	  if(!Main.util.canHasDuration(condition, target, effect)) {
		  duration = -1;
	  }
  }
  
  //���ü�������Ŀ��   ����  RaduisEntity:3.0
  private void setTarget(String part) {
	  if(part.contains(":")) {
		  radius = Main.util.getDoubleNumber(part);
		  target = SkillTarget.valueOf(part.split(":")[0]);
	  }else {
		  target = SkillTarget.valueOf(part);
	  }
  }
  
  public SkillCondition getCondition() {
	  return condition;
  }
  
  //��ȡ�������ͷŵ�Ŀ��
  public List<LivingEntity> getTarget(Player self) {
	  List<LivingEntity> entities = new ArrayList<>();
	  
	  if(target.equals(SkillTarget.Self)) {
		  entities.add(self);
	  }else if(target.equals(SkillTarget.RaduisEntity)) {
		  
		  for(Entity entity : self.getNearbyEntities(radius, radius, radius)) {
			  if(entity instanceof LivingEntity) {
				  entities.add((LivingEntity) entity);
			  }
		  }
		  
	  }else {
		  SkillUtil sutil = new SkillUtil();
		  LivingEntity entity = sutil.getTargetEntity(self);
		  if(entity != null) entities.add(entity);
	  }
	  
	  return entities;
  }
  
  /**
   * ��ʼִ�м���
   * @param self ִ�м����������
   */
  public void run(Player self) {
	  //�������е�Ŀ��
	  List<LivingEntity> entities = getTarget(self);
	  //׼����ָĿ��
	  LivingEntity entity = new SkillUtil().getTargetEntity(self);
	  
	  //�ж��Ƿ���������ִ������
	  if( entities.size() > 0 && condition.getCondition().contains("Target") && entity != null && condition.check(self, entity)) {
		  //����Ŀ����ִ��Ч��
		  effect.run(entities , self , duration);
		  
	  }else if(entities.size() > 0 && condition.check(self, self)) {
		  effect.run(entities , self , duration);
		  
	  }//����ǹ������ɱ�����ģ�Ӧ�ñ��
	  else if(condition.isNeedSign()) {
		  SPlayer player = OnlineData.getSPlayer(self);

		  player.addExecution(this, duration);
		  OnlineData.players.put(self, player);
	  }
  }
  
  //ǿ��ִ�У���������
  public void runWithoutCondition(Player self) {
	  List<LivingEntity> entities = getTarget(self);
	  
	  effect.run(entities , self ,duration);
  }
  
}
