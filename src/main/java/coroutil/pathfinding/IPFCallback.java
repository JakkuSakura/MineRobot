package CoroUtil.pathfinding;

import java.util.ArrayList;


public interface IPFCallback {

	void pfComplete(PFCallbackItem ci);
	void manageCallbackQueue();
	ArrayList<PFCallbackItem> getQueue();
}
