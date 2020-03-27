package it.polimi.ingsw.PSP25;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class GodPower {

    public GodPower() {
    }

    private List<Space> getValidMovementSpaces(Worker worker) {
        ArrayList<Space> validMovementSpaces = new ArrayList<Space>();

        for (Space space : worker.getSpace().getAdjacentSpaces()) {

            if (space.getWorker() == null && space.getTowerHeight() - worker.getSpace().getTowerHeight() <= 1
                    && !space.hasDome()) {
                validMovementSpaces.add(space);
            }
        }
        return validMovementSpaces;
    }

    public List<Space> getValidBuildSpaces(Worker worker) {
        ArrayList<Space> validBuildSpaces = new ArrayList<Space>();

        for (Space space : worker.getSpace().getAdjacentSpaces()) {

            if (space.getWorker() == null && !space.hasDome() && space.getTowerHeight() <= 3) {
                validBuildSpaces.add(space);
            }
        }
        return validBuildSpaces;
    }

    private void moveWorker(Worker worker, Space space) {
        worker.moveTo(space);
    }

    private void buildBlock(Space space) {
        if (space.getTowerHeight() == 3) {
            space.addDome();
        } else {
            space.increaseTowerHeight();
        }
    }

    private boolean verifyWin(Worker worker) {

        //TEMP
        if (worker.getHeightBeforeMove() == 2 && worker.getSpace().getTowerHeight() == 3) {
            return true;
        } else {
            return false;
        }
        //END TEMP

    }

    // Return true is someone wins
    public boolean turnSequence(Player player) {
        List<Space> validMovementSpacesW1;
        List<Space> validMovementSpacesW2;
        List<Space> validBuildSpaces;
        Worker selectedWorker;
        Space selectedMovementSpace = null;
        Space selectedBuildingSpace = null;

        validMovementSpacesW1 = getValidMovementSpaces(player.getWorker1());
        validMovementSpacesW2 = getValidMovementSpaces(player.getWorker2());

        // Player selects a Worker
        // selectedWorker = ...
        // Player moves selected Worker in a valid space
        // TEMP
        System.out.println(player.getName() + ": Choose a worker");
        Scanner scanner = new Scanner(System.in);
        int workerchoice = scanner.nextInt();
        while (workerchoice < 1 || workerchoice > 2) {
            System.out.println("Worker number is not valid. Choose 1 or 2");
            workerchoice = scanner.nextInt();
        }
        if (workerchoice == 1) {
            selectedWorker = player.getWorker1();
            System.out.println(validMovementSpacesW1.toString());
            System.out.println(player.getName() + ": Choose movement space");
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
            System.out.println(validMovementSpacesW2.toString());
            System.out.println(player.getName() + ": Choose movement space");
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

        if (verifyWin(selectedWorker) == true) {
            return true;
        }

        validBuildSpaces = getValidBuildSpaces(selectedWorker);
        // Player builds in a valid space
        // TEMP
        System.out.println(validBuildSpaces.toString());
        System.out.println(player.getName() + ": Choose building space");
        int chosenBuildingSpace = scanner.nextInt();
        while (!(validBuildSpaces.stream().map(Space::getNumber).collect(Collectors.toList())).
                contains(chosenBuildingSpace)) {
            System.out.println(chosenBuildingSpace + " is not in the valid movement spaces list");
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

        return false;

    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
