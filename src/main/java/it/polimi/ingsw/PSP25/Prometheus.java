package it.polimi.ingsw.PSP25;

import it.polimi.ingsw.PSP25.Model.BroadcastInterface;
import it.polimi.ingsw.PSP25.Model.GodPower;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Prometheus class
 */
public class Prometheus extends GodPower {

    /**
     * Prometheus constructor
     *
     * @param activeEffects list of opponent GodPower effects active in our turn that could limit movement,
     *                      building action or winning conditions of our player
     */
    public Prometheus(ActiveEffects activeEffects, BroadcastInterface broadcastInterface) {
        super(activeEffects, broadcastInterface);
    }

    /**
     * Override of "turnSequence" according to Prometheus' effect:
     * "If your Worker does not move up, it may build both before and after moving",
     * the turn sequence is modified and the player is asked if he wants to build before moving.
     * @param player playing the round
     * @param activeEffects array containing opponent god power effects that may influence this turn
     * @return TurnResult.LOSE if the player has lost during this turn
     *         TurnResult.WIN if the player has won during this turn
     *         TurnResult.CONTINUE if the player hasn't lost or won during this turn
     */
    @Override
    public TurnResult turnSequence(Player player, ActiveEffects activeEffects) {
        List<Space> validMovementSpacesW1;
        List<Space> validMovementSpacesW2;
        List<Space> validBuildSpaces;
        Worker selectedWorker;
        Scanner scanner = new Scanner(System.in);
        int workerchoice;
        Space selectedMovementSpace = null;
        Space selectedBuildingSpace = null;
        boolean cantMoveUp = false;

        validMovementSpacesW1 = getValidMovementSpaces(player.getWorker1());
        validMovementSpacesW2 = getValidMovementSpaces(player.getWorker2());

        //DEBUG
        activeEffects.debugPrint();
        //END DEBUG

        if (verifyLoseByMovement(validMovementSpacesW1, validMovementSpacesW2)) {
            return TurnResult.LOSE;
        }

        // Player selects a Worker
        // selectedWorker = ...
        // Player moves selected Worker in a valid space
        // TEMP
        if (validMovementSpacesW1.size() == 0) {
            System.out.println("Worker 1 can't move! Worker 2 is automatically selected");
            workerchoice = 2;
        } else if (validMovementSpacesW2.size() == 0) {
            System.out.println("Worker 2 can't move! Worker 1 is automatically selected");
            workerchoice = 1;
        } else {
            System.out.println(player.getName() + "(" + player.getID() + ")" + ": Choose a worker");
            workerchoice = scanner.nextInt();
            while (workerchoice < 1 || workerchoice > 2) {
                System.out.println("Worker number is not valid. Choose 1 or 2");
                workerchoice = scanner.nextInt();
            }
        }

        if (workerchoice == 1) {
            selectedWorker = player.getWorker1();
            validBuildSpaces = getValidBuildSpaces(selectedWorker);
            if(validBuildSpaces.size() > 0){
                System.out.println("Do you want to build before move? y|n");
                String answer = scanner.next();
                if(answer.equals("y")){
                    cantMoveUp = true;
                    System.out.println(validBuildSpaces);
                    System.out.println(player.getName() + "(" + player.getID() + ")" + ": Choose building space");
                    int chosenBuildingSpace = scanner.nextInt();
                    while (!(validBuildSpaces.stream().map(Space::getNumber).collect(Collectors.toList())).
                            contains(chosenBuildingSpace)) {
                        System.out.println(chosenBuildingSpace + " is not in the valid building spaces list");
                        chosenBuildingSpace = scanner.nextInt();
                    }
                    int x = chosenBuildingSpace % 5;
                    int y = chosenBuildingSpace / 5;
                    for (Space space : validBuildSpaces) {
                        if (space.getX() == x && space.getY() == y)
                            selectedBuildingSpace = space;
                    }
                    // END TEMP
                    buildBlock(selectedBuildingSpace);
                }
            }
            validMovementSpacesW1 = getValidMovementSpaces(selectedWorker, cantMoveUp);
            if(validMovementSpacesW1.size() == 0)
                return TurnResult.LOSE;
            System.out.println(validMovementSpacesW1.toString());
            System.out.println(player.getName() + "(" + player.getID() + ")" + ": Choose movement space");
            int chosenMovementSpace = scanner.nextInt();
            while (!(validMovementSpacesW1.stream().map(Space::getNumber).collect(Collectors.toList())).
                    contains(chosenMovementSpace)) {
                System.out.println(chosenMovementSpace + " is not in the valid movement spaces list");
                chosenMovementSpace = scanner.nextInt();
            }
            int x = chosenMovementSpace % 5;
            int y = chosenMovementSpace / 5;
            for (Space space : validMovementSpacesW1) {
                if (space.getX() == x && space.getY() == y)
                    selectedMovementSpace = space;
            }

        } else {
            selectedWorker = player.getWorker2();
            validBuildSpaces = getValidBuildSpaces(selectedWorker);
            if(validBuildSpaces.size() > 0){
                System.out.println("Do you want to build before move? y|n");
                String answer = scanner.next();
                if(answer.equals("y")){
                    cantMoveUp = true;
                    System.out.println(validBuildSpaces);
                    System.out.println(player.getName() + "(" + player.getID() + ")" + ": Choose building space");
                    int chosenBuildingSpace = scanner.nextInt();
                    while (!(validBuildSpaces.stream().map(Space::getNumber).collect(Collectors.toList())).
                            contains(chosenBuildingSpace)) {
                        System.out.println(chosenBuildingSpace + " is not in the valid building spaces list");
                        chosenBuildingSpace = scanner.nextInt();
                    }
                    int x = chosenBuildingSpace % 5;
                    int y = chosenBuildingSpace / 5;
                    for (Space space : validBuildSpaces) {
                        if (space.getX() == x && space.getY() == y)
                            selectedBuildingSpace = space;
                    }
                    // END TEMP
                    buildBlock(selectedBuildingSpace);
                }
            }
            validMovementSpacesW2 = getValidMovementSpaces(selectedWorker, cantMoveUp);
            if(validMovementSpacesW2.size() == 0)
                return TurnResult.LOSE;
            System.out.println(validMovementSpacesW2.toString());
            System.out.println(player.getName() + "(" + player.getID() + ")" + ": Choose movement space");
            int chosenmovementspace = scanner.nextInt();
            while (!(validMovementSpacesW2.stream().map(Space::getNumber).collect(Collectors.toList())).
                    contains(chosenmovementspace)) {
                System.out.println(chosenmovementspace + " is not in the valid movement spaces list");
                chosenmovementspace = scanner.nextInt();
            }
            int x = chosenmovementspace % 5;
            int y = chosenmovementspace / 5;
            for (Space space : validMovementSpacesW2) {
                if (space.getX() == x && space.getY() == y)
                    selectedMovementSpace = space;
            }
        }
        // END TEMP

        moveWorker(selectedWorker, selectedMovementSpace);

        if (activeEffects.canWin(selectedWorker, selectedMovementSpace) && verifyWin(selectedWorker) == true) {
            return TurnResult.WIN;
        }

        validBuildSpaces = getValidBuildSpaces(selectedWorker);
        // Player builds in a valid space
        // TEMP
        if(verifyLoseByBuilding(validBuildSpaces)){
            return TurnResult.LOSE;
        }
        System.out.println(validBuildSpaces.toString());
        System.out.println(player.getName() + "(" + player.getID() + ")" + ": Choose building space");
        int chosenBuildingSpace = scanner.nextInt();
        while (!(validBuildSpaces.stream().map(Space::getNumber).collect(Collectors.toList())).
                contains(chosenBuildingSpace)) {
            System.out.println(chosenBuildingSpace + " is not in the valid building spaces list");
            chosenBuildingSpace = scanner.nextInt();
        }
        int x = chosenBuildingSpace % 5;
        int y = chosenBuildingSpace / 5;
        for (Space space : validBuildSpaces) {
            if (space.getX() == x && space.getY() == y)
                selectedBuildingSpace = space;
        }
        // END TEMP
        buildBlock(selectedBuildingSpace);

        addActiveEffects(activeEffects, player.getWorker1(), player.getWorker2(), selectedWorker);

        return TurnResult.CONTINUE;
    }

    private List<Space> getValidMovementSpaces(Worker worker, boolean cantMoveUp) {
        if(!cantMoveUp)
            return super.getValidBuildSpaces(worker);
        else{
            ArrayList<Space> validMovementSpaces = new ArrayList<Space>();

            for (Space space : worker.getSpace().getAdjacentSpaces()) {

                if (space.getWorker() == null && (space.getTowerHeight() <= worker.getSpace().getTowerHeight())
                        && !space.hasDome() && activeEffects.canMove(worker, space)) {
                    validMovementSpaces.add(space);
                }
            }
            return validMovementSpaces;
        }
    }
}