package it.polimi.ingsw.PSP25.Client;

import java.util.List;

public interface ViewObserver {
    void updateIPAddress(String ip);

    void updateNumOfPlayers(int numOfPlayers);

    void updateName(String name);

    void updateAllGodPower(List<Integer> selectedIndexes);
}
