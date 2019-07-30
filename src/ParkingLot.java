import java.util.HashSet;

// Represents a space for keeping vehicles temporarily
class ParkingLot {
    private final int totalSlots;
    private HashSet<Vehicle> parkedVehicles;
    private Notifiable owner;

    ParkingLot(int size){
        this.totalSlots = size;
        this.parkedVehicles = new HashSet<Vehicle>();
    }

    ParkingLot(int size, Notifiable owner){
        this.totalSlots = size;
        this.parkedVehicles = new HashSet<Vehicle>();
        this.owner = owner;
    }

    void park(Vehicle vehicle) throws ParkingLotFullException, AlreadyParkedException {
        if(this.parkedVehicles.contains(vehicle)){
            throw new AlreadyParkedException("Vehicle already parked.");
        }
        if(this.parkedVehicles.size() == this.totalSlots){
            throw new ParkingLotFullException("No parking slot available.");
        }
        this.parkedVehicles.add(vehicle);
        if(this.parkedVehicles.size() == this.totalSlots && this.owner != null){
            owner.notifyFull();
        }
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
