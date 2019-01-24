package CoroUtil.world.location;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ISimulationTickable {

	public void init();
	public void initPost();
	public void tickUpdate();
	public void tickUpdateThreaded();
	public void readFromNBT(NBTTagCompound parData);
	public NBTTagCompound writeToNBT(NBTTagCompound parData);
	public void cleanup();
	public void setWorldID(int ID);
	public World getWorld();
	public BlockPos getOrigin();
	public void setOrigin(BlockPos coord);
	public boolean isThreaded();
	public String getSharedSimulationName();
}
