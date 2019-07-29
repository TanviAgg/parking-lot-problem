// Represents a space for parking vehicles
class ParkingLot {
    private final int totalSlots;
    private int emptySlots;

    ParkingLot(int size){
        this.totalSlots = size;
        this.emptySlots = size;
    }

    boolean parkCar() {
        if(emptySlots == 0){
            return false;
        }
        this.emptySlots--;
        return true;
    }
}
