import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Player
{
    private String name;
    private List<Ship> ships;
    private int[][] guessBoard;

    public Player(String name)
    {
        this.name = name;
        ships = new ArrayList<>();
        guessBoard = new int[10][10];
    }

    public String getName()
    {
        return name;
    }

    /**
     * Returns how many ships are currently in this
     *   Player's ships List.
     *
     * @return
     */
    public int getNumberOfShips()
    {
        return ships.size();
    }

    /**
     * Returns the ship that occupies the specified
     *   Location loc.  Returns null if this Player
     *   does not control a ship at Location loc.
     *
     * @param loc
     * @return
     */
    public Ship getShip(Location loc)
    {
        for (int i = 0; i < ships.size(); i++)              // loops through the ships
        {
            if(ships.get(i).getLocations().contains(loc))   // if the current ship in the iteration shares a same location as loc
                return ships.get(i);
        }

        return null;
    }

    public void addShip(Ship ship)
    {
        ships.add(ship);
    }

    public void removeShip(Ship ship)
    {
        ships.remove(ship);
    }

    /**
     * Returns true if this Player controls a ship
     *   at the specified Location loc; false
     *   otherwise.
     *
     * @param loc
     * @return
     */
    public boolean hasShipAtLocation(Location loc)
    {
        for (int i = 0; i < ships.size(); i++)
        {
            if(ships.get(i).getLocations().contains(loc))   // if the current ship in the iteration shares a same location as loc
                return true;
        }

        return false;
    }

    public int[][] getGuessBoard()
    {
        return guessBoard;
    }

    public List<Ship> getShips()
    {
        return ships;
    }

    /**
     * Returns true if obj is an instanceof Player and
     *   instance variables are equivalent.
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj)
    {
        if(obj instanceof Player)                               // if obj is an instanceof Player
            return name.equals(((Player) obj).name) &&
                    ships.equals(((Player) obj).ships) &&
                    Arrays.deepEquals(guessBoard, ((Player) obj).guessBoard);

        return false;
    }

    /**
     * Attack the specified Player at the specified Location.
     *
     * Return true if the attack resulted in a ship sinking;
     *   false otherwise.
     *
     * @param enemy
     * @param loc
     * @return
     */
    public abstract boolean attack(Player enemy, Location loc);

    /**
     * Construct an instance of
     *
     *   AircraftCarrier,
     *   Destroyer,
     *   Submarine,
     *   Cruiser, and
     *   PatrolBoat
     *
     * and add them to this Player's list of ships.
     */
    public abstract void populateShips();
}
