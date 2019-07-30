import java.util.HashSet;

// Represents a space for keeping vehicles temporarily
class ParkingLot {
    private final int totalSlots;
    private int emptySlots;
    private HashSet<Vehicle> parkedVehicles;

    ParkingLot(int size){
        this.totalSlots = size;
        this.emptySlots = size;
        this.parkedVehicles = new HashSet<Vehicle>();
    }

    void park(Vehicle vehicle) throws ParkingLotFullException, AlreadyParkedException {
        if(this.parkedVehicles.contains(vehicle)){
            throw new AlreadyParkedException("Vehicle already parked.");
        }
        if(emptySlots == 0){
            throw new ParkingLotFullException("No parking slot available.");
        }
        this.emptySlots--;
        this.parkedVehicles.add(vehicle);
    }

    void unpark(Vehicle vehicle) throws VehicleNotParkedException {
        if(!this.parkedVehicles.contains(vehicle)){
            throw new VehicleNotParkedException("This vehicle was not parked.");
        }
        this.parkedVehicles.remove(vehicle);
        this.emptySlots++;
    }
}
