import java.util.Random;

public class ComputerPlayer extends Player
{
    //boolean justHit;

    public ComputerPlayer(String name)
    {
        super(name);
        //justHit = true;
        populateShips();
    }

    /**
     * Randomly chooses a Location that has not been
     *   attacked (Location loc is ignored).  Marks
     *   the attacked Location on the guess board
     *   with a positive number if the enemy Player
     *   controls a ship at the Location attacked;
     *   otherwise, if the enemy Player does not
     *   control a ship at the attacked Location,
     *   guess board is marked with a negative number.
     *
     * If the enemy Player controls a ship at the attacked
     *   Location, the ship must add the Location to its
     *   hits taken.  Then, if the ship has been sunk, it
     *   is removed from the enemy Player's list of ships.
     *
     * Return true if the attack resulted in a ship sinking;
     *   false otherwise.
     *
     * @param enemy The Player to attack.
     * @param loc The Location to attack.
     * @return
     */
    @Override
    public boolean attack(Player enemy, Location loc)
    {
        int rowAttack = (int) (Math.random() * 10);         // random row position
        int colAttack = (int) (Math.random() * 10);         // random column position

        while(getGuessBoard()[rowAttack][colAttack] != 0)   // makes sure the location has not already been guessed
        {
            rowAttack = (int) (Math.random() * 10);
            colAttack = (int) (Math.random() * 10);
        }

        loc = new Location(rowAttack, colAttack);           // creates new loc variable with corresponding location

        if(enemy.hasShipAtLocation(loc))                    // if the enemy has a ship at loc
        {
            enemy.getShip(loc).takeHit(loc);                // make the ship at loc take a hit
            getGuessBoard()[loc.getRow()][loc.getCol()] = 1;

            if(enemy.getShip(loc).isSunk())                 // if the enemy's ship at loc just got sunk
            {
                //justHit = false;
                enemy.removeShip(enemy.getShip(loc));       // removes the enemy ship from enemy's ships
                return true;
            }
        }
        else
        {
            getGuessBoard()[loc.getRow()][loc.getCol()] = -1;
        }

        return false;
    }

    /**
     * Method to attack around a spot that has recently been hit
     * @param loc
     * @return
     */
    /*private Location attackAround(Location loc)
    {

    }*/

    /**
     * Construct an instance of
     *
     *   AircraftCarrier,
     *   Destroyer,
     *   Submarine,
     *   Cruiser, and
     *   PatrolBoat
     *
     * with random locations and add them to
     *  this Player's list of ships.
     */
    @Override
    public void populateShips()
    {
        outerloop:
        while(true)
        {
            int nonChangingPosition = (int) (Math.random() * 10);           // random position that doesn't change from 0-9
            int changingPosition = (int) (Math.random() * 6);               // random position that changes from 0-5

            if(Math.random() < 0.50)
            {
                for (int i = changingPosition; i < changingPosition + 5; i++)
                {
                    if(hasShipAtLocation(new Location(nonChangingPosition, i)))
                        continue outerloop;
                }

                addShip(new AircraftCarrier(new Location(nonChangingPosition, changingPosition),
                        new Location(nonChangingPosition, changingPosition + 1),
                        new Location(nonChangingPosition, changingPosition + 2),
                        new Location(nonChangingPosition, changingPosition + 3),
                        new Location(nonChangingPosition, changingPosition + 4)));
                break;
            }
            else
            {
                for (int i = changingPosition; i < changingPosition + 5; i++)
                {
                    if(hasShipAtLocation(new Location(i, nonChangingPosition)))
                        continue outerloop;
                }

                addShip(new AircraftCarrier(new Location(changingPosition, nonChangingPosition),
                        new Location(changingPosition + 1, nonChangingPosition),
                        new Location(changingPosition + 2, nonChangingPosition),
                        new Location(changingPosition + 3, nonChangingPosition),
                        new Location(changingPosition + 4, nonChangingPosition)));
                break;
            }
        }

        // repeats code before, except with a ship of length 4
        outerloop:
        while(true)
        {
            int nonChangingPosition = (int) (Math.random() * 10);
            int changingPosition = (int) (Math.random() * 7);

            if(Math.random() < 0.50)
            {
                for (int i = changingPosition; i < changingPosition + 4; i++)
                {
                    if(hasShipAtLocation(new Location(nonChangingPosition, i)))
                        continue outerloop;
                }

                addShip(new Destroyer(new Location(nonChangingPosition, changingPosition),
                        new Location(nonChangingPosition, changingPosition + 1),
                        new Location(nonChangingPosition, changingPosition + 2),
                        new Location(nonChangingPosition, changingPosition + 3)));
                break;
            }
            else
            {
                for (int i = changingPosition; i < changingPosition + 4; i++)
                {
                    if(hasShipAtLocation(new Location(i, nonChangingPosition)))
                        continue outerloop;
                }

                addShip(new Destroyer(new Location(changingPosition, nonChangingPosition),
                        new Location(changingPosition + 1, nonChangingPosition),
                        new Location(changingPosition + 2, nonChangingPosition),
                        new Location(changingPosition + 3, nonChangingPosition)));
                break;
            }
        }

        // repeats code before, except with a ship of length 3
        outerloop:
        while(true)
        {
            int nonChangingPosition = (int) (Math.random() * 10);
            int changingPosition = (int) (Math.random() * 8);

            if(Math.random() < 0.50)
            {
                for (int i = changingPosition; i < changingPosition + 3; i++)
                {
                    if(hasShipAtLocation(new Location(nonChangingPosition, i)))
                        continue outerloop;
                }

                addShip(new Submarine(new Location(nonChangingPosition, changingPosition),
                        new Location(nonChangingPosition, changingPosition + 1),
                        new Location(nonChangingPosition, changingPosition + 2)));
                break;
            }
            else
            {
                for (int i = changingPosition; i < changingPosition + 3; i++)
                {
                    if(hasShipAtLocation(new Location(i, nonChangingPosition)))
                        continue outerloop;
                }

                addShip(new Submarine(new Location(changingPosition, nonChangingPosition),
                        new Location(changingPosition + 1, nonChangingPosition),
                        new Location(changingPosition + 2, nonChangingPosition)));
                break;
            }
        }

        // repeats code before, except with a ship of length 3
        outerloop:
        while(true)
        {
            int nonChangingPosition = (int) (Math.random() * 10);
            int changingPosition = (int) (Math.random() * 8);

            if(Math.random() < 0.50)
            {
                for (int i = changingPosition; i < changingPosition + 3; i++)
                {
                    if(hasShipAtLocation(new Location(nonChangingPosition, i)))
                        continue outerloop;
                }

                addShip(new Cruiser(new Location(nonChangingPosition, changingPosition),
                        new Location(nonChangingPosition, changingPosition + 1),
                        new Location(nonChangingPosition, changingPosition + 2)));
                break;
            }
            else
            {
                for (int i = changingPosition; i < changingPosition + 3; i++)
                {
                    if(hasShipAtLocation(new Location(i, nonChangingPosition)))
                        continue outerloop;
                }

                addShip(new Cruiser(new Location(changingPosition, nonChangingPosition),
                        new Location(changingPosition + 1, nonChangingPosition),
                        new Location(changingPosition + 2, nonChangingPosition)));
                break;
            }
        }

        // repeats code before, except with a ship of length 2
        outerloop:
        while(true)
        {
            int nonChangingPosition = (int) (Math.random() * 10);
            int changingPosition = (int) (Math.random() * 9);

            if(Math.random() < 0.50)
            {
                for (int i = changingPosition; i < changingPosition + 2; i++)
                {
                    if(hasShipAtLocation(new Location(nonChangingPosition, i)))
                        continue outerloop;
                }

                addShip(new PatrolBoat(new Location(nonChangingPosition, changingPosition),
                        new Location(nonChangingPosition, changingPosition + 1)));
                break;
            }
            else
            {
                for (int i = changingPosition; i < changingPosition + 2; i++)
                {
                    if(hasShipAtLocation(new Location(i, nonChangingPosition)))
                        continue outerloop;
                }

                addShip(new PatrolBoat(new Location(changingPosition, nonChangingPosition),
                        new Location(changingPosition + 1, nonChangingPosition)));
                break;
            }
        }
    }
}
