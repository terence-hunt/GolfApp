package PlayRound;

import java.util.ArrayList;
import java.util.Iterator;

public class PlayRoundMaster {
	
	private ArrayList<PlayRoundListenerInterface> listeners;
	
	public PlayRoundMaster(){
		listeners = new ArrayList<PlayRoundListenerInterface>();
	}
	public void registerListener(PlayRoundListenerInterface listener){
		listeners.add(listener);
	}
	
	public void updateListeners(int holeNumber){
		Iterator<PlayRoundListenerInterface> iterator = listeners.iterator();
		
		while(iterator.hasNext()){
			iterator.next().update(holeNumber);
		}
	}

}
