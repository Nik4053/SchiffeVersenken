package client;

public class SPIELFELD {
    /**
     * will be:
     * <p>
     * 0 if the field is water and has not been shot by the enemy
     * <p>
     * 1 if the field is water and has been shot by the enemy
     * <p>
     * 2 if the field is a ship and has not been shot by the enemy
     * <p>
     * 4 if the field is a ship and has been shot by the enemy
     */
    private int[][] yourField = new int[10][10];

    /**
     * will be:
     * <p>
     * 0 if the field has not been shot by you
     * <p>
     * 1 if the field has been shot, but was only water
     * <p>
     * 2 if the field has been shot and there was a ship
     */
    private int[][] enemyField = new int[10][10];

    public SPIELFELD() {
        // TODO Auto-generated constructor stub
    }

    public int[][] getField(){
        return yourField;
    }

    public int[][] getEnemyField(){
        return enemyField;
    }

    /**
     * Will mark the given field as shot by your enemy
     * 
     * @param x
     *            the x -coordinate
     * @param y
     *            the y - coordinate
     * @return true if the shot was a successful hit
     */
    public boolean setEnemyHit(int x, int y) {
        int field = yourField[x][y];
        if (field == 2) {
            yourField[x][y] = 4;
            return true;
        } else {
            yourField[x][y] = 2;
        }
        return false;
    }

    /**
     * Will mark the given field as shot by you
     * 
     * @param x
     *            the x -coordinate
     * @param y
     *            the y - coordinate
     * @param hit
     *            true if the given coordinates have hit a ship
     */
    public void setYourHit(int x, int y, boolean hit) {
        if(hit){
            enemyField[x][y] = 2;
        }else{
            enemyField[x][y] = 1;
        }
    }

    /**
     * TODO
     * @param x
     *            the x -coordinate
     * @param y
     *            the y - coordinate
     * @return true if the given target at yourFiel[] has already been targeted
     */
    public boolean alreadyBeenTargeted(int x, int y){
        return false;
    }

}

