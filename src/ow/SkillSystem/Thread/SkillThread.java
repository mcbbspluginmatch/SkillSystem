package ow.SkillSystem.Thread;

import java.util.Iterator;

import org.bukkit.entity.Player;

import ow.SkillSystem.data.OnlineData;
import ow.SkillSystem.data.SPlayer;

public class SkillThread extends Thread{
	
	//�й���ҵļ�����ȴ�򵹼�ʱ
	public void run() {
		
		while(true) {
			
			Iterator<Player> it = OnlineData.players.keySet().iterator();
			while(it.hasNext()) {
				SPlayer player =  OnlineData.players.get(it.next());
				player.handleSkillTime();
				player.handleExecutionTime();
			}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
	}

}
