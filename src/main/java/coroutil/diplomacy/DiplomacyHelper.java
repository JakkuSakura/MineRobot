package CoroUtil.diplomacy;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;

public class DiplomacyHelper {

	public String[] noAttackEnts;
	public Class[] noAttackEntsCls;
	
	/*public static boolean shouldTargetEnt(ICoroAI source, Entity target) {
		try {
			if (target instanceof ICoroAI) {
				//use team instance system
				TeamInstance tiSource = source.getAIAgent().dipl_info;
				TeamInstance tiTarget = ((ICoroAI)target).getAIAgent().dipl_info;
				if (tiSource.isEnemy(tiTarget)) return true;
			} else if (target instanceof IBTAgent) {
				//use team instance system
				TeamInstance tiSource = source.getAIAgent().dipl_info;
				TeamInstance tiTarget = ((IBTAgent)target).getAIBTAgent().dipl_info;
				if (tiSource.isEnemy(tiTarget)) return true;
			} else {
				if ((target instanceof EntityMob || target instanceof EntitySlime) && !source.getAIAgent().isThreat(target)) return true;
			}
		} catch (Exception ex) {
			//this will happen when ent death reference happens, cleanup, issue line was 'TeamInstance tiTarget = ((ICoroAI)target).getAIAgent().dipl_info;'
		}
		return false;
	}*/

	
	public static boolean isThreat(Entity ent) {
		if (ent instanceof EntityCreeper || ent instanceof EntityEnderman) {
			return true;
		}
		return false;
	}
	
	/*public static boolean shouldTameTargetEnt(EntityLivingBase source, Entity target, AITamable tamable) {
		//If new ai
		try {
			if (target instanceof ICoroAI) {
				if (tamable.ownerCachedInstance != null) {
					//If the target is an enemy of our owner, attack it
					if (((ICoroAI)target).getAIAgent().jobMan.getPrimaryJob().isEnemy(tamable.ownerCachedInstance)) {
						return true;
					}
				}
			}
			if ((target instanceof EntityMob || target instanceof EntitySlime) && !tamable.job.entInt.getAIAgent().isThreat(target)) return true;
			if (source instanceof ICoroAI) return shouldTargetEnt((ICoroAI)source, target);
		} catch (Exception ex) {
			//this will happen when ent death reference happens, cleanup, issue line was 'TeamInstance tiTarget = ((ICoroAI)target).getAIAgent().dipl_info;'
		}
		return false;
	}*/
	
}
