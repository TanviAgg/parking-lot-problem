// Represents a space for parking vehicles
class ParkingLot {
    private final int totalSlots;
    private int emptySlots;

    ParkingLot(int size){
        this.totalSlots = size;
        this.emptySlots = size;
    }

    void parkCar() throws ParkingLotFullException {
        if(emptySlots == 0){
            throw new ParkingLotFullException("No parking slot available.");
        }
        this.emptySlots--;
    }
}
