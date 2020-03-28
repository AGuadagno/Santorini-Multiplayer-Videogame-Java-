package it.polimi.ingsw.PSP25;

public class Player {
    private String name;
    private String ID;
    private Worker worker1;
    private Worker worker2;
    private GodPower godPower;

    public Player(String name, String ID) {
        this.name = name;
        this.ID = ID;
    }

    public void initializeWorkers(Space space1, Space space2) {
        this.worker1 = new Worker(space1, this);
        space1.setWorker(worker1);
        this.worker2 = new Worker(space2, this);
        space2.setWorker(worker2);
    }

    public Worker getWorker1() {
        return worker1;
    }

    public Worker getWorker2() {
        return worker2;
    }

    public void initializeGodPower(GodPower godPower) {
        this.godPower = godPower;
    }

    public GodPower getGodPower() {
        return this.godPower;
    }

    public String getName() {
        return this.name;
    }

    public String getID() {
        return ID;
    }
}
