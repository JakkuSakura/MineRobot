package CoroUtil.util;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;

public class ChunkCoordinatesBlock extends BlockPos {

	public Block block = null;
	public int meta = 0;
	
	public ChunkCoordinatesBlock(int par1, int par2, int par3, Block parBlockID)
	{
		this(par1, par2, par3, parBlockID, 0);
	}
	
	public ChunkCoordinatesBlock(int par1, int par2, int par3, Block parBlockID, int parMeta)
    {
        super(par1, par2, par3);
        block = parBlockID;
        meta = parMeta;
    }
	
	public ChunkCoordinatesBlock(BlockPos par1BlockPos, Block parBlockID)
	{
		this(par1BlockPos, parBlockID, 0);
	}

    public ChunkCoordinatesBlock(BlockPos par1BlockPos, Block parBlockID, int parMeta)
    {
        super(par1BlockPos);
        block = parBlockID;
        meta = parMeta;
    }

	
}
