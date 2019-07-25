package ow.SkillSystem.skills;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import ow.SkillSystem.Main;
import ow.SkillSystem.Util;

public class SkillCondition { 
	//�������ܵ�����
    private String[] conditions = {"SelfHealth","EveryAttacking",
    "EveryKilling","TargetHealth","ItemConsuming","OnAir",
    "ItemHas","NextAtttacking","NextKilling","Run","None"};
    private String condition;
    //�й���ֵ
    private int amount;
    //�й���Ʒ
    private ItemStack item;
    //����0������2�����ڵ���1��С�ڵ���-1��С��-2
    private int sign;
    //�Ƿ�������
    private boolean isNone = false;
    
    public SkillCondition(String part) {
    	if(part.contains("Health")) {
    		setAboutHealth(part);
    	}else if(part.contains("Item")) {
    		setAboutItem(part);
    	}else {
    		setSingleCondition(part);
    	}
    }
    
    //�����������ֵ������
    private void setAboutHealth(String part){
    	Util util = new Util();
    	if(part.contains("SelHealth")) {
    		condition = conditions[0];
    	}else{
    		condition = conditions[3];
    	}
		sign = util.getSign(part);
		amount = util.getIntNumber(part);
    }
    
    //���������Ʒ������
    private void setAboutItem(String part) {
    	String[] parts = part.split(":");
    	condition = parts[0];
    	item = Main.items.get(parts[1]);
    	amount = Integer.parseInt(parts[2]);
    }
    
    //��������޸������Ե�����
    private void setSingleCondition(String part) {
    	if(part.equalsIgnoreCase("None")) {
    		isNone = true;
    		condition = "None";
    	}else {
    		condition = part;
    	}
    }
    
    public String getCondition() {
    	return condition;
    }
    
    //�Ƿ���Ҫ���
    public boolean isNeedSign() {
    	return getCondition().contains("Attack")||
  			  getCondition().contains("Kill");
    }
    
    //����Ƿ���������
    public boolean check(Player self,LivingEntity target) {
    	//���Ѫ��
    	if(condition.contains("Health")) {
        	double health = condition.contains("Self")?self.getHealth():target.getHealth();
        	switch(sign) {
        	case -2:{
        		return health < amount;
        	}
        	case -1:{
        		return health <= amount;
        	}
        	case 1:{
        		return health >= amount;
        	}
        	case 2:{
        		return health > amount;
        	}
        	default:{
        		return health == amount;
        	}
        	}
    	}//����Ƿ��ڿ���
    	else if(condition.equalsIgnoreCase("OnAir")) {
    		return !self.isOnGround();
    	}//�����Ʒ����
    	else if(condition.equalsIgnoreCase("ItemConsuming")) {

    		PlayerInventory inv = self.getInventory();
    		if(hasItem(item , inv , amount)) {
    			
    			removeItem(item , inv , amount);
    			
    			return true;
    		}
    		return false;
    	}//�����Ʒӵ��
    	else if(condition.equalsIgnoreCase("ItemHas")) {

    		return hasItem(item , self.getInventory() , amount);
    	}//��ⱼ��
    	else if(condition.equalsIgnoreCase("Run")) {
    		return self.isSprinting();
    	}
    	//������
    	else if(isNone) {
    		return true;
    	}
		return false;
    }
    
    //�Ƴ�������ָ����������Ʒ
    private void removeItem(ItemStack item , PlayerInventory inv , int arg) {
    	
    	for(int i  = 0 ; i < inv.getSize() ; i++) {
    		ItemStack it = inv.getItem(i);
    		//Ѱ�ұ���������Ҫ����Ʒ
    		if(it != null && item.isSimilar(it)) {
    			
    			if(it.getAmount() > arg) {
    				it.setAmount(it.getAmount() - arg);
    				return;
    			}else if(it.getAmount() == arg){
    				inv.setItem(i, null);
    				return;
    			}else {
    				arg -= it.getAmount();
    				inv.setItem(i, null);
    			}
    			
    		}
    	}
    	
    }
    
    //��ⱳ���Ƿ���ָ����������Ʒ
    private boolean hasItem(ItemStack item , PlayerInventory inv , int arg) {
    	
    	for(int i  = 0 ; i < inv.getSize() ; i++) {

    		ItemStack it = inv.getItem(i);
    		if(it != null && item.isSimilar(it)) {
    			
    			if(it.getAmount() >= arg) {
    				return true;
    			}else {
    				arg -= it.getAmount();
    			}
    			
    		}
    		
    		if(arg <= 0) return true; 
    	}
    	
    	return false;
    }
    
}
