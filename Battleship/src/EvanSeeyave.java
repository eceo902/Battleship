import java.util.ArrayList;
import java.util.Collections;

public class EvanSeeyave extends Player
{
    private boolean continueHitting;
    private int direction;
    private int countRounds;
    private Location originalLocation;
    private Location previousLocation;
    private ArrayList<ArrayList> heatMap;

    public EvanSeeyave(String name)
    {
        super(name);
        continueHitting = false;                                    // if we are attacking a ship
        direction = 1;                                              // direction denoted with an int: 1->Left; 2->Right; 3->Up; 4->Down
        countRounds = 0;
        populateShips();                                            // calls populateShips() method

        originalLocation = new Location(0,0);             // arbitrary location to initialize the first time
        previousLocation = new Location(0, 0);            // arbitrary location to initialize the first time
        buildHeatMap();                                             // calls
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
        if(continueHitting && direction == 1)
        {
            try
            {
                loc = attackLeft();
            }
            catch(ArrayIndexOutOfBoundsException e)
            {
                direction = 2;
                previousLocation = new Location(originalLocation.getRow(), originalLocation.getCol());
            }
        }

        if(continueHitting && direction == 2)
        {
            try
            {
                loc = attackRight();
            }
            catch(ArrayIndexOutOfBoundsException e)
            {
                direction = 3;
                previousLocation = new Location(originalLocation.getRow(), originalLocation.getCol());
            }
        }

        if(continueHitting && direction == 3)
        {
            try
            {
                loc = attackUp();
            }
            catch(ArrayIndexOutOfBoundsException e)
            {
                direction = 4;
                previousLocation = new Location(originalLocation.getRow(), originalLocation.getCol());
            }
        }

        if(continueHitting && direction == 4)
        {
            try
            {
                loc = attackDown();
            }
            catch(ArrayIndexOutOfBoundsException e)
            {
                continueHitting = false;
                previousLocation = new Location(originalLocation.getRow(), originalLocation.getCol());
            }
        }

        if(!continueHitting)
        {
            loc = attackHeatMap();
            originalLocation = new Location(loc.getRow(), loc.getCol());        // because of pointers and pass-by reference
            direction = 1;
        }

        if(enemy.hasShipAtLocation(loc))                    // if the enemy has a ship at loc
        {
            enemy.getShip(loc).takeHit(loc);                // make the ship at loc take a hit
            getGuessBoard()[loc.getRow()][loc.getCol()] = 1;

            previousLocation = new Location(loc.getRow(), loc.getCol());        // sets up previousLocation for future attacks

            if(enemy.getShip(loc).isSunk())                 // if the enemy's ship at loc just got sunk
            {
                continueHitting = false;                            // resets justHit

                enemy.removeShip(enemy.getShip(loc));       // removes the enemy ship that was just sunk from enemy's ships
                return true;
            }
            else
            {
                continueHitting = true;                             // if the ship is not sunk yet, then justHit is set to true to continue attacking the area
            }
        }
        else
        {
            getGuessBoard()[loc.getRow()][loc.getCol()] = -1;

            if(continueHitting)
            {
                if (direction == 1)
                {
                    direction = 2;
                    previousLocation = new Location(originalLocation.getRow(), originalLocation.getCol());
                }
                else if (direction == 2)
                {
                    direction = 3;
                    previousLocation = new Location(originalLocation.getRow(), originalLocation.getCol());
                }
                else if (direction == 3)
                {
                    direction = 4;
                    previousLocation = new Location(originalLocation.getRow(), originalLocation.getCol());
                }
                else if (direction == 4)
                {
                    continueHitting = false;
                }
            }
        }

        countRounds++;
        return false;
    }

    private void buildHeatMap()
    {
        heatMap = new ArrayList<>();

        ArrayList<Location> section1 = new ArrayList<>();
        Collections.addAll(section1, new Location(4, 4), new Location(4, 5), new Location(5, 4), new Location(5, 5));

        ArrayList<Location> section2 = new ArrayList<>();
        Collections.addAll(section2, new Location(3, 4), new Location(3, 5), new Location(4, 3), new Location(4, 6),
                                    new Location(5, 3), new Location(5, 6), new Location(6, 4), new Location(6, 5));

        ArrayList<Location> section3 = new ArrayList<>();
        Collections.addAll(section3, new Location(2, 4), new Location(2, 5), new Location(3, 3), new Location(3, 6),
                                    new Location(4, 2), new Location(4, 7), new Location(5, 2), new Location(5, 7),
                                    new Location(6, 3), new Location(6, 6), new Location(7, 4), new Location(7, 5));

        ArrayList<Location> section4 = new ArrayList<>();
        Collections.addAll(section4, new Location(1, 3), new Location(1, 4), new Location(1, 5), new Location(1, 6),
                                    new Location(2, 2), new Location(2, 3), new Location(2, 6), new Location(2, 7),
                                    new Location(3, 1), new Location(3, 2), new Location(3, 7), new Location(3, 8),
                                    new Location(4, 1), new Location(4, 8), new Location(5, 1), new Location(5, 8),
                                    new Location(6, 1), new Location(6, 2), new Location(6, 7), new Location(6, 8),
                                    new Location(7, 2), new Location(7, 3), new Location(7, 6), new Location(7, 7),
                                    new Location(8, 3), new Location(8, 4), new Location(8, 5), new Location(8, 6));

        ArrayList<Location> section5 = new ArrayList<>();
        Collections.addAll(section5, new Location(0, 2), new Location(0, 3), new Location(0, 4), new Location(0, 5),
                                    new Location(0, 6), new Location(0, 7), new Location(1, 1), new Location(1, 2),
                                    new Location(1, 7), new Location(1, 8), new Location(2, 0), new Location(2, 1),
                                    new Location(2, 8), new Location(2, 9), new Location(3, 0), new Location(3, 9),
                                    new Location(4, 0), new Location(4, 9), new Location(5, 0), new Location(5, 9),
                                    new Location(6, 0), new Location(6, 9), new Location(7, 0), new Location(7, 1),
                                    new Location(7, 8), new Location(7, 9), new Location(8, 1), new Location(8, 2),
                                    new Location(8, 7), new Location(8, 8), new Location(9, 2), new Location(9, 3),
                                    new Location(9, 4), new Location(9, 5), new Location(9, 6), new Location(9, 7));

        ArrayList<Location> section6 = new ArrayList<>();
        Collections.addAll(section6, new Location(0, 0), new Location(0, 1), new Location(0, 8), new Location(0, 9),
                                    new Location(1, 0), new Location(1, 9), new Location(8, 0), new Location(8, 9),
                                    new Location(9, 0), new Location(9, 1), new Location(9, 8), new Location(9, 9));

        Collections.addAll(heatMap, section1, section2, section3, section4, section5, section6);        // adds all the sections to the heatMap
    }

    private Location attackHeatMap()
    {
        int randomLocation = 0;
        ArrayList<Location> selectedLocations;

        do
        {
            double random = Math.random();                  // random variable to determine which section will be chosen

            if (random < 0.08)
                selectedLocations = heatMap.get(0);
            else if (random < 0.16)
                selectedLocations = heatMap.get(1);
            else if (random < 0.28)
                selectedLocations = heatMap.get(2);
            else if (random < 0.52)
                selectedLocations = heatMap.get(3);
            else if (random < 0.88)
                selectedLocations = heatMap.get(4);
            else
                selectedLocations = heatMap.get(5);

            randomLocation = (int) (Math.random() * selectedLocations.size());

            if(countRounds < 28 && random < 0.75)
            {
                try
                {
                    if (getGuessBoard()[selectedLocations.get(randomLocation).getRow()][selectedLocations.get(randomLocation).getCol()] != 0 ||
                            getGuessBoard()[selectedLocations.get(randomLocation).getRow() - 1][selectedLocations.get(randomLocation).getCol()] == -1 ||
                            getGuessBoard()[selectedLocations.get(randomLocation).getRow()][selectedLocations.get(randomLocation).getCol() - 1] == -1 ||
                            getGuessBoard()[selectedLocations.get(randomLocation).getRow()][selectedLocations.get(randomLocation).getCol() + 1] == -1 ||
                            getGuessBoard()[selectedLocations.get(randomLocation).getRow() + 1][selectedLocations.get(randomLocation).getCol()] == -1)
                        continue;
                    else
                        break;
                } catch (ArrayIndexOutOfBoundsException e)
                {
                    if (getGuessBoard()[selectedLocations.get(randomLocation).getRow()][selectedLocations.get(randomLocation).getCol()] != 0 &&
                            random < 0.50)
                        continue;
                    else
                        break;
                }
            }
            else
            {
                if (getGuessBoard()[selectedLocations.get(randomLocation).getRow()][selectedLocations.get(randomLocation).getCol()] != 0)
                    continue;
                else
                    break;
            }
        }
        while(true);

        return selectedLocations.get(randomLocation);
    }

//    private Location attackCluster()
//    {
//        int bestQuadrant = 1;
//        int countBestQuadrant = 0;
//        int countCurrentSpots = 0;
//
//        for (int r = 0; r < 5; r++)
//        {
//            for (int c = 0; c < 5; c++)
//            {
//                if(getGuessBoard()[r][c] == 0)
//                    countBestQuadrant++;
//            }
//        }
//
//        for (int r = 0; r < 5; r++)
//        {
//            for (int c = 5; c < 10; c++)
//            {
//                if(getGuessBoard()[r][c] == 0)
//                    countCurrentSpots++;
//            }
//        }
//        if(countCurrentSpots > countBestQuadrant)
//        {
//            countBestQuadrant = countCurrentSpots;
//            bestQuadrant = 2;
//        }
//
//        countCurrentSpots = 0;
//        for (int r = 5; r < 10; r++)
//        {
//            for (int c = 0; c < 5; c++)
//            {
//                if(getGuessBoard()[r][c] == 0)
//                    countCurrentSpots++;
//            }
//        }
//        if(countCurrentSpots > countBestQuadrant)
//        {
//            countBestQuadrant = countCurrentSpots;
//            bestQuadrant = 3;
//        }
//
//        countCurrentSpots = 0;
//        for (int r = 5; r < 10; r++)
//        {
//            for (int c = 5; c < 10; c++)
//            {
//                if(getGuessBoard()[r][c] == 0)
//                    countCurrentSpots++;
//            }
//        }
//        if(countCurrentSpots > countBestQuadrant)
//        {
//            bestQuadrant = 4;
//        }
//
//        int rowAttack = (int) (Math.random() * 5);         // random row position
//        int colAttack = (int) (Math.random() * 5);         // random column position
//        switch(bestQuadrant)
//        {
//            case 1:
//                while(getGuessBoard()[rowAttack][colAttack] != 0)       // makes sure the location has not already been guessed
//                {
//                    rowAttack = (int) (Math.random() * 5);
//                    colAttack = (int) (Math.random() * 5);
//                }
//
//                return new Location(rowAttack, colAttack);              // creates new loc variable with corresponding location
//
//            case 2:
//                while(getGuessBoard()[rowAttack][colAttack + 5] != 0)
//                {
//                    rowAttack = (int) (Math.random() * 5);
//                    colAttack = (int) (Math.random() * 5);
//                }
//
//                return new Location(rowAttack, colAttack + 5);
//
//            case 3:
//                while(getGuessBoard()[rowAttack + 5][colAttack] != 0)
//                {
//                    rowAttack = (int) (Math.random() * 5);
//                    colAttack = (int) (Math.random() * 5);
//                }
//
//                return new Location(rowAttack + 5, colAttack);
//
//            default:
//                while(getGuessBoard()[rowAttack + 5][colAttack + 5] != 0)
//                {
//                    rowAttack = (int) (Math.random() * 5);
//                    colAttack = (int) (Math.random() * 5);
//                }
//
//                return new Location(rowAttack + 5, colAttack + 5);
//        }
//    }


    private Location attackLeft()
    {
        if(getGuessBoard()[previousLocation.getRow()][previousLocation.getCol() - 1] != 0)
            throw new ArrayIndexOutOfBoundsException();         // throws this specific exception because the attack method uses this exception

        return new Location(previousLocation.getRow(), previousLocation.getCol() - 1);
    }

    private Location attackRight()
    {
        if(getGuessBoard()[previousLocation.getRow()][previousLocation.getCol() + 1] != 0)
            throw new ArrayIndexOutOfBoundsException();

        return new Location(previousLocation.getRow(), previousLocation.getCol() + 1);
    }

    private Location attackUp()
    {
        if(getGuessBoard()[previousLocation.getRow() - 1][previousLocation.getCol()] != 0)
            throw new ArrayIndexOutOfBoundsException();

        return new Location(previousLocation.getRow() - 1, previousLocation.getCol());
    }

    private Location attackDown()
    {
        if(getGuessBoard()[previousLocation.getRow() + 1][previousLocation.getCol()] != 0)
            throw new ArrayIndexOutOfBoundsException();

        return new Location(previousLocation.getRow() + 1, previousLocation.getCol());
    }

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
                    if(hasShipAtLocation(new Location(nonChangingPosition, i - 1)) ||
                            hasShipAtLocation(new Location(nonChangingPosition, i + 1)) ||
                            hasShipAtLocation(new Location(nonChangingPosition - 1, i)) ||
                            hasShipAtLocation(new Location(nonChangingPosition + 1, i)))
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
                    if(hasShipAtLocation(new Location(i - 1, nonChangingPosition)) ||
                            hasShipAtLocation(new Location(i + 1, nonChangingPosition)) ||
                            hasShipAtLocation(new Location(i, nonChangingPosition - 1)) ||
                            hasShipAtLocation(new Location(i, nonChangingPosition + 1)))
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
