import java.util.HashSet;
import java.util.List;

// Represents a space for keeping vehicles temporarily
class ParkingLot {
    private final int totalSlots;
    private HashSet<Vehicle> parkedVehicles;
    private List<Notifiable> notifiables;

    ParkingLot(int size){
        this.totalSlots = size;
        this.parkedVehicles = new HashSet<Vehicle>();
    }

    ParkingLot(int size, List<Notifiable> notifiables){
        this.totalSlots = size;
        this.parkedVehicles = new HashSet<Vehicle>();
        this.notifiables = notifiables;
    }

    void park(Vehicle vehicle) throws ParkingLotFullException, AlreadyParkedException {
        if(this.parkedVehicles.contains(vehicle)){
            throw new AlreadyParkedException("Vehicle already parked.");
        }
        if(this.parkedVehicles.size() == this.totalSlots){
            throw new ParkingLotFullException("No parking slot available.");
        }
        this.parkedVehicles.add(vehicle);
        if(this.parkedVehicles.size() == this.totalSlots && this.notifiables != null){
            for(Notifiable notifiable: notifiables){
                notifiable.notifyFull();
            }
        }
    }

    void unpark(Vehicle vehicle) throws VehicleNotParkedException {
        if(!this.parkedVehicles.contains(vehicle)){
            throw new VehicleNotParkedException("This vehicle was not parked.");
        }
        this.parkedVehicles.remove(vehicle);
        if(this.parkedVehicles.size() == this.totalSlots - 1 && this.notifiables != null){
            for(Notifiable notifiable: notifiables){
                notifiable.notifyEmpty();
            }
        }
    }

    boolean isParked(Vehicle vehicle) {
        return this.parkedVehicles.contains(vehicle);
    }
}
