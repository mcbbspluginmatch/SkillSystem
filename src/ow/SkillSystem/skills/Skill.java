package ow.SkillSystem.skills;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

public class Skill {
  private String name;         //��������
  private int cooldown;        //��ȴʱ��
  private boolean needPermission = true;
  private String message;
  private boolean canKey = false;    //�ܷ��ð�������
  
  private List<SkillSingleExecution> executions = new ArrayList<>();

  public Skill(String name , int cooldown , boolean np , String msg ,boolean cank) {
	  this.name = name;
	  this.cooldown = cooldown;
	  needPermission = np;
	  message = msg;
	  canKey = cank;
  }
  
  //���뵥������ִ��
  public void setExecution(List<String> lists) {
	  for(String arg : lists) {
		  SkillSingleExecution execution = new SkillSingleExecution(arg);
		  executions.add(execution);
	  }
  }
  
  public int getCooldown() {
	  return cooldown;
  }
  
  //��ȡδ��ȴ��ϵ���ʾ
  public String getMessage() {
	  return message;
  }
  
  public String getName() {
	  return name;
  }
  
  //�Ƿ���ü��̴���
  public boolean canUseKeyBoard() {
	  return canKey;
  }
  
  public boolean getIsNeedPermission() {
	  return needPermission;
  }
  
  /**
   * ����ִ��
   * @param user ����ʹ����
   */
  public void run(Player user) {
	  
	  for(SkillSingleExecution execution : executions) {
		  execution.run(user);
	  }

  }
}
