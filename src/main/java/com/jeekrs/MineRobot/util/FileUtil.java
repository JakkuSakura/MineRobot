package com.jeekrs.MineRobot.util;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FileUtil {
	public static String lastWorldFolder = "";
    
	public static NBTTagCompound getExtraWorldNBT(String fileName) {
		NBTTagCompound data = new NBTTagCompound();
		//try load
		
		String saveFolder = getWorldSaveFolderPath() + getWorldFolderName();
		
		if ((new File(saveFolder + fileName)).exists()) {
			try {
				data = CompressedStreamTools.readCompressed(new FileInputStream(saveFolder + fileName));
			} catch (Exception ex) {
				System.out.println("FileUtil: getExtraWorldNBT: Error loading " + saveFolder + fileName);
			}
			
			//NBTTagList var14 = gameData.getTagList("playerData");
		}
		
		return data;
	}
	
	public static void setExtraWorldNBT(String fileName, NBTTagCompound data) {
		try {
    		
    		String saveFolder = getWorldSaveFolderPath() + getWorldFolderName();
    		
    		//Write out to file
    		FileOutputStream fos = new FileOutputStream(saveFolder + fileName);
	    	CompressedStreamTools.writeCompressed(data, fos);
	    	fos.close();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	//this must be used while server is active
    public static String getWorldFolderName() {
		World world = DimensionManager.getWorld(0);
		
		if (world != null) {
			lastWorldFolder = ((WorldServer)world).getChunkSaveLocation().getName();
			return lastWorldFolder + File.separator;
		}
		
		return lastWorldFolder + File.separator;
	}
	
	public static String getSaveFolderPath() {
    	if (FMLCommonHandler.instance().getMinecraftServerInstance() == null || FMLCommonHandler.instance().getMinecraftServerInstance().isSinglePlayer()) {
    		return getClientSidePath() + File.separator;
    	} else {
    		return new File(".").getAbsolutePath() + File.separator;
    	}
    	
    }
	
	public static String getMinecraftSaveFolderPath() {
    	if (FMLCommonHandler.instance().getMinecraftServerInstance() == null || FMLCommonHandler.instance().getMinecraftServerInstance().isSinglePlayer()) {
    		return getClientSidePath() + File.separator + "config" + File.separator;
    	} else {
    		return new File(".").getAbsolutePath() + File.separator + "config" + File.separator;
    	}
    }
	
	public static String getWorldSaveFolderPath() {
    	if (FMLCommonHandler.instance().getMinecraftServerInstance() == null || FMLCommonHandler.instance().getMinecraftServerInstance().isSinglePlayer()) {
    		return getClientSidePath() + File.separator + "saves" + File.separator;
    	} else {
    		return new File(".").getAbsolutePath() + File.separator;
    	}
    }
    
    @SideOnly(Side.CLIENT)
	public static String getClientSidePath() {
		return FMLClientHandler.instance().getClient().mcDataDir/*getAppDir("minecraft")*/.getPath();
	}
    
    public static void writeCoords(String name, BlockPos coords, NBTTagCompound nbt) {
    	nbt.setInteger(name + "X", coords.getX());
    	nbt.setInteger(name + "Y", coords.getY());
    	nbt.setInteger(name + "Z", coords.getZ());
    }
    
    public static BlockPos readCoords(String name, NBTTagCompound nbt) {
    	if (nbt.hasKey(name + "X")) {
    		return new BlockPos(nbt.getInteger(name + "X"), nbt.getInteger(name + "Y"), nbt.getInteger(name + "Z"));
    	} else {
    		return null;
    	}
    }
}
