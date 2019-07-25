package ow.SkillSystem.skills;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import ow.SkillSystem.Util;
import ow.SkillSystem.SpecialEffects.ParticleEffect;
import ow.SkillSystem.SpecialEffects.SoundEffect;
import ow.SkillSystem.data.OnlineData;

public class SkillEffect {   //����ʵ��Ч��
    @SuppressWarnings("unused")
	private String[] effects = {"Charge","PotionEffect",
    		"DamageSet","Damage","HealthSet",
    		"ShowEntity","ShootArrows","Fire","Lightning",
    "Pull","PushBack","Message","ParticleEffect","SoundEffect",
    "Jump","Explosion","DamagedSet","Stop"};
    private String effect;
    private double amount;
    private String msg;
    private PotionEffect potioneffect;
    private ParticleEffect particleeffect;
    private SoundEffect soundeffect;
    
    //��ʼ���������е�Ч������
    public SkillEffect(String part) {
    	if(part.startsWith("PotionEffect")) {
    		setPotionEffect(part.split(":"));
    	}else if(part.startsWith("Message")) {
    		setMessage(part.split(":"));
    	}else if(part.contains("Effect")) {
    		setEffect(part.split(":"));
    	}else {
    		setAboutNumber(part.split(":"));
    	}
    }
    
    /*����Ч�������������
     * Charge DamageSet Damage HealthIncrease
     * Fire Pull PushBack ShootArrows Jump
     */
    private void setAboutNumber(String[] parts) {
    	Util util = new Util();
    	amount = util.getDoubleNumber(parts[1]);
    	effect = parts[0];
    }
    
    //����ҩˮЧ��
    private void setPotionEffect(String[] parts) {
    	Util util = new Util();
    	potioneffect = new PotionEffect(PotionEffectType.getByName(parts[1]),util.getIntNumber(parts[2]),util.getIntNumber(parts[3]));
    	effect = "PotionEffect";
    }
    
    //������ϢЧ��
    private void setMessage(String[] parts) {
    	msg = parts[1];
    	effect = "Message";
    }
    
    //��������������Ч��
    private void setEffect(String[] parts) {
    	
    	if(parts[0].equalsIgnoreCase("ParticleEffect")) {
    		
        	effect = "ParticleEffect";
        	particleeffect = new ParticleEffect(parts[1] , Integer.parseInt(parts[2]));
        	
    	}else {
    		
    		effect = "SoundEffect";
    		soundeffect = new SoundEffect(parts[1],Float.parseFloat(parts[2]),Float.parseFloat(parts[2]));
    	}
    	
    }
    
    public String getEffect() {
    	return effect;
    }
    
    /**
     * ִ�м���Ч��
     * @param entities ִ�е�Ŀ��
     * @param user ʹ����
     * @param duration ����ʱ�䣨�Ǳ�Ҫ��
     */
    public void run(List<LivingEntity> entities , Player user , int duration) {
    	
    	SkillUtil skillutil = new SkillUtil();
    	
    	//ҩˮЧ��
    	if(effect.equalsIgnoreCase("PotionEffect")) {
    		for(LivingEntity entity : entities) {
    			entity.addPotionEffect(potioneffect);
    		}
    	}//�˺�Ч��
    	else if(effect.equalsIgnoreCase("Damage")) {
    		for(LivingEntity entity : entities) {
    			entity.damage(amount);
    		}
    	}//����
    	else if(effect.equalsIgnoreCase("showEntity")) {
    		for(LivingEntity entity : entities) {
    			entity.setGlowing(true);
    			//do
    		}
    	}//�Ż�
    	else if(effect.equalsIgnoreCase("Fire")) {
    		for(LivingEntity entity : entities) {
    			entity.setFireTicks((int) amount);
    		}
    	}//����
    	else if(effect.equalsIgnoreCase("Lightning")) {
    		for(LivingEntity entity : entities) {
    			
    			for(int i = 0 ; i< amount ; i++) {
        			entity.getWorld().strikeLightning(entity.getLocation());
    			}

    		}
    	}//���
    	else if(effect.equalsIgnoreCase("ShootArrows")) {
    		for(LivingEntity entity : entities) {
    			Location loc = entity.getEyeLocation();
    			
        		entity.getWorld().spawnArrow(loc, loc.getDirection(), (float) amount, 12);

    		}
    	}//Ѫ��ǿ�е���
    	else if(effect.equalsIgnoreCase("HealthSet")) {
    		
    		for(LivingEntity entity : entities) {
    			Double health = entity.getHealth();
    			
    			if(health + amount < 0) {
        			entity.setHealth(0);
    			}else {
        			Double afterh = health + amount > entity.getMaxHealth()? entity.getMaxHealth(): health + amount;
        			entity.setHealth(afterh);
    			}
    			
    		}
    		
    	}//ǿ�е���������
    	else if(effect.equalsIgnoreCase("DamageSet")) {
    		for(LivingEntity entity : entities) {
    			
    			if(OnlineData.damageset.get(entity) == null) {
        			OnlineData.addDamageSet(entity, amount, duration);
    			}else {
        			OnlineData.setDamageSet(entity, amount);
    			}
    			
    		}
    	}//����
    	else if(effect.equalsIgnoreCase("PushBack")) {
    		for(LivingEntity entity : entities) {
    			skillutil.pushBack(user, entity, amount);
    		}
    	}//����
    	else if(effect.equalsIgnoreCase("Pull")) {
    		for(LivingEntity entity : entities) {
    			skillutil.pull(user, entity, amount);
    		}
    	}//���
    	else if(effect.equalsIgnoreCase("Charge")) {
    		for(LivingEntity entity : entities) {
    			skillutil.charge(entity, amount);
    		}
    	}//������Ϣ
    	else if(effect.equalsIgnoreCase("Message")) {
    		for(LivingEntity entity : entities) {
    			entity.sendMessage(msg);
    		}
    	}//����Ч��
    	else if(effect.equalsIgnoreCase("ParticleEffect")) {
    		for(LivingEntity entity : entities) {
    			particleeffect.playNormal(entity.getWorld(), entity.getLocation());
    		}
    	}//����Ч��
    	else if(effect.equalsIgnoreCase("SoundEffect")) {
    		for(LivingEntity entity : entities) {
    			soundeffect.play(entity.getWorld(), entity.getLocation());
    		}
    	}//����
    	else if(effect.equalsIgnoreCase("Jump")) {
    		for(LivingEntity entity : entities) {
    			skillutil.jump(entity, amount);
    		}
    	}//��ը
    	else if(effect.equalsIgnoreCase("Explosion")) {
    		for(LivingEntity entity : entities) {
    			entity.getWorld().createExplosion(entity.getLocation(), (float) amount);
    		}
    	}//ǿ�е���[����]���˺�
    	else if(effect.equalsIgnoreCase("DamagedSet")) {
    		for(LivingEntity entity : entities) {
    			
    			if(OnlineData.damagedset.get(entity) == null) {
        			OnlineData.addDamagedSet(entity, amount, duration);
    			}else {
        			OnlineData.setDamagedSet(entity, amount);
    			}
    			
    		}
    	}
    	
    }
}
