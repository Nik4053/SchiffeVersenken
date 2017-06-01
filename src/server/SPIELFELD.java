package server;

import java.util.List;

public class SPIELFELD {

	/**
	 * Array mit true für getroffen and false für leer oder nicht probiert
	 */
	private boolean[] getroffen;
	/**
	 * array mit true für geschossen
	 */
	private boolean[] geschossen;
	private List<SCHIFF> schiffe;
	
	private int size;
	private int userID;
	
	public SPIELFELD(int Size, int UserID){
		//TODO
		this.size=Size;
		this.userID=UserID;
	}
	
	/**
	 * Shoots at the given location 
	 * @param x 
	 * @param y
	 * @return true if it was a hit
	 */
	public boolean shoot(int x, int y){
		//TODO
		return false;
	}
	
	public int getUser(){
		return userID;
	}
	
	public List<SCHIFF> getShips()
	{
		return schiffe;
	}
	
	public boolean[] getShots()
	{
		return geschossen;
	}
	
	public boolean[] getHits(){
		return getroffen;
	}
	
	public boolean addShip(SCHIFF Schiff){
		
	}
}
