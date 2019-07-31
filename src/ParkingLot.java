import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

// Represents a space for keeping vehicles temporarily
class ParkingLot implements Comparable<ParkingLot>{
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

    void addNotifiable(Notifiable notifiable){
        this.notifiables.add(notifiable);
    }

    void park(Vehicle vehicle) throws ParkingLotFullException, AlreadyParkedException {
        if(this.parkedVehicles.contains(vehicle)){
            throw new AlreadyParkedException("Vehicle already parked.");
        }
        if(this.parkedVehicles.size() == this.totalSlots){
            throw new ParkingLotFullException("No parking slot available.");
        }
        this.parkedVehicles.add(vehicle);
        if(isFull() && this.notifiables != null){
            for(Notifiable notifiable: notifiables){
                notifiable.notifyFull(this);
            }
        }
    }

    private boolean isFull(){
        return this.parkedVehicles.size() == this.totalSlots;
    }

    void unpark(Vehicle vehicle) throws VehicleNotParkedException {
        if(!this.parkedVehicles.contains(vehicle)){
            throw new VehicleNotParkedException("This vehicle was not parked.");
        }
        this.parkedVehicles.remove(vehicle);
        if(this.parkedVehicles.size() == this.totalSlots - 1 && this.notifiables != null){
            for(Notifiable notifiable: notifiables){
                notifiable.notifyEmpty(this);
            }
        }
    }

    boolean isParked(Vehicle vehicle) {
        return this.parkedVehicles.contains(vehicle);
    }


    @Override
    public int compareTo(ParkingLot other) {
        return (this.totalSlots - this.parkedVehicles.size()) - (other.totalSlots - other.parkedVehicles.size());
    }
}
