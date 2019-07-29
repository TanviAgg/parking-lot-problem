// Represents a space for parking vehicles
class ParkingLot {
    private final int totalSlots;
    private int emptySlots;

    ParkingLot(int size){
        this.totalSlots = size;
        this.emptySlots = size;
    }

    void park(Vehicle vehicle) throws ParkingLotFullException {
        if(emptySlots == 0){
            throw new ParkingLotFullException("No parking slot available.");
        }
        this.emptySlots--;
    }

    void unpark(Vehicle vehicle) throws ParkingLotEmptyException {
        if(emptySlots == totalSlots){
            throw new ParkingLotEmptyException("No parking slot filled.");
        }
        this.emptySlots++;
    }
}
