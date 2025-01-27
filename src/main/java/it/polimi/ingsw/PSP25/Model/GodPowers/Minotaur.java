package it.polimi.ingsw.PSP25.Model.GodPowers;

import it.polimi.ingsw.PSP25.Model.ActiveEffects;
import it.polimi.ingsw.PSP25.Model.BroadcastInterface;
import it.polimi.ingsw.PSP25.Model.Space;
import it.polimi.ingsw.PSP25.Model.Worker;
import java.util.ArrayList;
import java.util.List;


/**
 * Minotaur class
 */
public class Minotaur extends GodPower {

    /**
     * Minotaur constructor
     *
     * @param activeEffects      array containing opponents god power effects that may influence this turn
     * @param broadcastInterface Interface used to share information with all the other players
     */
    public Minotaur(ActiveEffects activeEffects, BroadcastInterface broadcastInterface) {
        super(activeEffects, broadcastInterface);
    }

    /**
     * Override of "getValidMovementSpaces" according to Minotaur's effect:
     * "Your Worker may move into an opponent Worker’s space, if their Worker can be forced one space straight backwards to an unoccupied space at any level".
     * Spaces occupied by opponent workers could be added to the list of valid movement spaces if the previous condition is respected.
     *
     * @param worker we want to know Spaces where he can move to
     * @return List of possible Spaces where the Worker passed as argument can move to
     */
    @Override
    protected List<Space> getValidMovementSpaces(Worker worker) {
        ArrayList<Space> validMovementSpaces = new ArrayList<>();

        for (Space space : worker.getSpace().getAdjacentSpaces()) {
            Space spaceSameDir = spaceSameDir(space, worker.getSpace());
            if ((space.getWorker() == null || (space.getWorker().getPlayer() != worker.getPlayer()
                    && spaceSameDir != null && spaceSameDir.getWorker() == null && !spaceSameDir.hasDome()))
                    && space.getTowerHeight() - worker.getSpace().getTowerHeight() <= 1 && !space.hasDome()
                    && activeEffects.canMove(worker, space)) {
                validMovementSpaces.add(space);
            }
        }
        return validMovementSpaces;
    }

    /**
     * Override of "moveWorker" according to Minotaur's effect:
     * "Your Worker may move into an opponent Worker’s space, if their Worker
     * can be forced one space straight backwards to an unoccupied space at any level".
     * This method moves the player's worker and also the opponent's worker.
     *
     * @param worker Worker that the player wants to move
     * @param space Space where the player wants to move the Worker
     */
    @Override
    protected void moveWorker(Worker worker, Space space) {
        if (space.hasWorker()) {
            Space spaceSameDir = spaceSameDir(space, worker.getSpace());
            space.getWorker().moveTo(spaceSameDir);
        }
        worker.moveTo(space);
    }

    /**
     * @param space1 generic space adjacent to space2
     * @param space2 space where the worker controlled by the player who has Minotaur as Godpower is located
     * @return the space that is positioned after space1, along the direction defined by space2 -> space1,
     * if it doesn't exist, returns null
     */
    private Space spaceSameDir(Space space1, Space space2) {
        int dirX = space1.getX() - space2.getX();
        int dirY = space1.getY() - space2.getY();
        List<Space> adjacentSpaces = space1.getAdjacentSpaces();
        Space spaceSameDir = null;

        for (Space space : adjacentSpaces) {
            if (space.getX() == space1.getX() + dirX && space.getY() == space1.getY() + dirY) {
                spaceSameDir = space;
                break;
            }
        }
        return spaceSameDir;
    }
}