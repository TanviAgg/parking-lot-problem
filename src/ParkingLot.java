import java.util.HashSet;

// Represents a space for keeping vehicles temporarily
class ParkingLot {
    private final int totalSlots;
    private HashSet<Vehicle> parkedVehicles;

    ParkingLot(int size){
        this.totalSlots = size;
        this.parkedVehicles = new HashSet<Vehicle>();
    }

    void park(Vehicle vehicle) throws ParkingLotFullException, AlreadyParkedException {
        if(this.parkedVehicles.contains(vehicle)){
            throw new AlreadyParkedException("Vehicle already parked.");
        }
        if(parkedVehicles.size() == totalSlots){
            throw new ParkingLotFullException("No parking slot available.");
        }
        this.parkedVehicles.add(vehicle);
    }

    void unpark(Vehicle vehicle) throws VehicleNotParkedException {
        if(!this.parkedVehicles.contains(vehicle)){
            throw new VehicleNotParkedException("This vehicle was not parked.");
        }
        this.parkedVehicles.remove(vehicle);
    }

    boolean isParked(Vehicle vehicle) {
        return this.parkedVehicles.contains(vehicle);
    }
}
