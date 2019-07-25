package ow.SkillSystem.data;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import org.bukkit.entity.Player;

import ow.SkillSystem.Main;
import ow.SkillSystem.skills.Skill;
import ow.SkillSystem.skills.SkillSingleExecution;

public class SPlayer {
   private Player player;
   
   //�������ļ��ܣ���Ӧ��ȴʱ��
   private HashMap<Skill,Integer> skills = new HashMap<>();
   
   /*�ɳ����ı�ǵ�������ִ�У���Ӧ������ʱ��
    * ������0Ϊ������
    * ��С��0Ϊ�´���
    */
   private HashMap<SkillSingleExecution,Integer>  executions = new HashMap<>();
   
   //��������Ӧ�ļ���
   private HashMap<Integer , Skill> keyBoard = new HashMap<>();
   
   public SPlayer(Player p) {
	   player = p;
   }
   
   public Player getPlayer() {
	   return player;
   }
   
   //�����ܶ�Ӧ�İ���
   public void addKeyBoardSetting(int key , Skill skill) {
	   keyBoard.put(key, skill);
   }
   
   //��ȡ��������Ӧ�ļ���
   public Skill getKeyBoardSkill(int key) {
	   return keyBoard.get(key);
   }
   
   //��ȡ���ܶ�Ӧ�İ���
   public int getKeyBoardSkill(Skill skill) {
	   Iterator<Integer> keys = keyBoard.keySet().iterator();
	   
	   while(keys.hasNext()) {
		   int key = keys.next();
		   Skill sk = keyBoard.get(key);
		   
		   if(skill.equals(sk)) {
			   return key;
		   }
	   }
	   
	   return -1;
   }
   
   //�Ƴ����ܵİ�
   public void removeKeyBoardSkill(Skill skill) {
	   Iterator<Integer> keys = keyBoard.keySet().iterator();
	   
	   while(keys.hasNext()) {
		   int key = keys.next();
		   Skill sk = keyBoard.get(key);
		   
		   if(skill.equals(sk)) {
			   keys.remove();
		   }
	   }
   }
   
   //����������
   public void saveKeyBoard(){
	   
	   try {
		Main.handle.savePlayerYML(keyBoard , player);
	} catch (IOException e) {
		e.printStackTrace();
	}
	   
   }
   
   //�����ִ�м���
   public void setSkill(Skill skill) {
	   
	   if(skill.getIsNeedPermission() && !player.hasPermission("SkillSystem."+skill.getName())) {
		   //do
		   return;
	   }
	   
	   if(!isSkillCooldown(skill)) {
		   skills.put(skill, skill.getCooldown());
		   
		   Main.util.runSkill(this, skill);
	   }else {
		   player.sendMessage(skill.getMessage().replace("%cooldown%", ""+skills.get(skill)));
	   }
	   
   }
   
   //�����Ƿ�����ȴ
   public boolean isSkillCooldown (Skill skill) {
	   return skills.keySet().contains(skill);
   }
   
   //�����ǵļ�����
   public void addExecution(SkillSingleExecution execution , int time) {
	   executions.put(execution, time);
   }
   
   //ִ����ʱ���ڵļ�����
   public void runExecution(String type) {
	   Iterator<SkillSingleExecution> it = executions.keySet().iterator();

	   while(it.hasNext()) {

		   SkillSingleExecution arg =  it.next();
		   String condition = arg.getCondition().getCondition();
		   
		   if((type.equals("KILL") && condition.contains("Kill"))||
				   condition.contains("Attack")) {

			   arg.runWithoutCondition(player);
			   
			   //������´��Եļ�����
			   if(condition.contains("Next")) {
				   it.remove();
			   }

		   }
	   }
	   
   }
   
   //������ִ�еĵ���ʱ����
   public void handleExecutionTime() {
	   Iterator<SkillSingleExecution> it = executions.keySet().iterator();
	   
	   while(it.hasNext()) {
		   SkillSingleExecution arg = (SkillSingleExecution) it.next();
		   int time = executions.get(arg);
		   
		   if(arg.getCondition().getCondition().contains("Next")) {
			   continue;
		   }
		   
		   time--;
		   if(time > 0) {
			   executions.put(arg, time);
		   }else {
			   executions.remove(arg);
		   }
		   
	   }
	   
   }
   
   //�����ܵĵ���ʱ����
   public void handleSkillTime() {
	   Iterator<Skill> it = skills.keySet().iterator();
	   
	   while(it.hasNext()) {
		   Skill arg = (Skill) it.next();
		   int time = skills.get(arg);
		   time--;
		   if(time > 0) {
			   skills.put(arg, time);
		   }else {
			   skills.remove(arg);
		   }
	   }
   }
   
}
