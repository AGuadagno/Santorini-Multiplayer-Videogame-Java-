package it.polimi.ingsw.PSP25.Model;

import it.polimi.ingsw.PSP25.Model.GodPowers.GodPower;
import it.polimi.ingsw.PSP25.Server.VirtualView;

/**
 * Player class. 2 or 3 Players per game.
 */
public class Player {

    /**
     * Description of Attributes:
     * name: contains the name of the Player
     * ID: contains the ID of the player. The ID is automatically generated
     * using the name of the player and the player number
     * worker1: contains the first worker controlled by the Player
     * worker2: contains the second worker controlled by the Player
     * godPower: contains the GodPower of the Player
     * playerNumber: contains the number of the Player. Different players have different player numbers
     * clientHandler: contains the clientHandler associated with the player's client
     */

    private String name;
    private String ID;
    private Worker worker1;
    private Worker worker2;
    private GodPower godPower;
    private int playerNumber;
    private VirtualView clientHandler;

    /**
     * Player constructor
     *
     * @param name          contains the name of the Player. The name is chosen by the user
     * @param playerNumber  contains the number of the Player. The number of the player is assigned by the caller
     * @param clientHandler contains the clientHandler associated with the player's client
     */
    public Player(String name, int playerNumber, VirtualView clientHandler) {
        this.name = name;
        this.playerNumber = playerNumber;
        this.ID = name.substring(0, 2).toUpperCase() + playerNumber;
        this.clientHandler = clientHandler;
    }

    /**
     * Returns the name of the player
     *
     * @return the name of the Player
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the ID of the Player
     * @return the ID of the Player
     */
    public String getID() {
        return ID;
    }

    /**
     * Sets the initial position of the two workers of the Player.
     * @param space1 Space where the first Worker of the Player will be positioned
     * @param space2 Space where the second Worker of the Player will be positioned
     */
    public void initializeWorkers(Space space1, Space space2) {
        this.worker1 = new Worker(space1, this);
        space1.setWorker(worker1);
        this.worker2 = new Worker(space2, this);
        space2.setWorker(worker2);
    }

    /**
     * Returns the first worker of the player
     * @return the first Worker of the Player
     */
    public Worker getWorker1() {
        return worker1;
    }

    /**
     * Returns the second worker of the player
     * @return the second Worker of the Player
     */
    public Worker getWorker2() {
        return worker2;
    }

    /**
     * Sets the GodPower of the Player
     * @param godPower GodPower of the Player
     */
    public void initializeGodPower(GodPower godPower) {
        this.godPower = godPower;
    }

    /**
     * Returns the GodPower of the Player
     * @return the GodPower of the Player
     */
    public GodPower getGodPower() {
        return this.godPower;
    }

    /**
     * Returns the clienthandler of the player
     * @return the ClientHandler of the Player
     */
    public VirtualView getClientHandler() {
        return clientHandler;
    }
}