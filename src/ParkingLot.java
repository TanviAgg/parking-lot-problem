import java.util.HashSet;

// Represents a space for keeping vehicles temporarily
class ParkingLot {
    private final int totalSlots;
    private int emptySlots;
    private HashSet<Vehicle> parkedCars;

    ParkingLot(int size){
        this.totalSlots = size;
        this.emptySlots = size;
        this.parkedCars = new HashSet<Vehicle>();
    }

    void park(Vehicle vehicle) throws ParkingLotFullException, AlreadyParkedException {
        if(this.parkedCars.contains(vehicle)){
            throw new AlreadyParkedException("Vehicle already parked.");
        }
        if(emptySlots == 0){
            throw new ParkingLotFullException("No parking slot available.");
        }
        this.emptySlots--;
        this.parkedCars.add(vehicle);
    }

    void unpark(Vehicle vehicle) throws ParkingLotEmptyException, VehicleNotParkedException {
        if(!this.parkedCars.contains(vehicle)){
            throw new VehicleNotParkedException("This vehicle was not parked.");
        }
        this.parkedCars.remove(vehicle);
        this.emptySlots++;
    }
}
