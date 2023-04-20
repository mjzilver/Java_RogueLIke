package engine;

import javax.swing.JComponent;

import graphics.LocalMapView;
import graphics.WorldMapView;

public class ViewController {

	ActiveWindow activeWindow;
	WorldMapView worldMapView;
	LocalMapView localMapView;
	
	public ViewController(MapController mapController) {
		activeWindow = ActiveWindow.WORLDMAP;
		
		worldMapView = new WorldMapView(mapController);
		localMapView = new LocalMapView(mapController);
	}
	
	public LocalMapView getLocalMapView(){
		return localMapView;
	}
	
	public JComponent getActiveView(){
		switch (activeWindow) {
		case LOCALMAP:
			return localMapView;
		case WORLDMAP:
			return worldMapView;
		case MENU:
			return localMapView;
		case INVENTORY:
			return localMapView;
		}
		return null;
	}
	
	public ActiveWindow getActiveWindow(){
		return activeWindow;
	}

	public void setActiveWindow(ActiveWindow activeWindow) {
		this.activeWindow = activeWindow;
	}
}
