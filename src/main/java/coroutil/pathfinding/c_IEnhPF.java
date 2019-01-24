package CoroUtil.pathfinding;




public interface c_IEnhPF
{

	void setPathExToEntity(PathEntityEx pathentity);
	//public void setPathToEntity(PathEntity pathentity);
	
	PathEntityEx getPath();
	boolean hasPath();
	void faceCoord(int x, int y, int z, float f, float f1);
	void noMoveTriggerCallback();
	
	
}