import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// Represents an entity that can park/unpark vehicles in its parkinglot
class ParkingLotAttendant implements Notifiable{
    private List<ParkingLot> parkingLots;
    private HashMap<ParkingLot, Boolean> availabityOfParkingLot;

    ParkingLotAttendant(List<ParkingLot> parkingLots) {
        this.parkingLots = parkingLots;
        this.availabityOfParkingLot = new HashMap<>();
        for(ParkingLot parkingLot: parkingLots){
            availabityOfParkingLot.put(parkingLot, true);
        }
    }

    void valetPark(Vehicle vehicle) throws UnableToParkException{
        for (ParkingLot parkingLot : parkingLots) {
            if (parkingLot.isParked(vehicle)) {
                throw new UnableToParkException();
            }
            if (!this.availabityOfParkingLot.get(parkingLot)) {
                continue;
            }
            try {
                parkingLot.park(vehicle);
                return;
            }
            catch(Exception e) {
                throw new UnableToParkException();
            }
        }
        throw new UnableToParkException();
    }

    void valetUnpark(Vehicle vehicle) throws UnableToUnparkException {
        for (ParkingLot parkingLot : parkingLots) {
            if (!parkingLot.isParked(vehicle)) {
                continue;
            }
            try {
                parkingLot.unpark(vehicle);
                return;
            }
            catch(Exception e) {
                throw new UnableToUnparkException();
            }
        }
        throw new UnableToUnparkException();
    }

    @Override
    public void notifyFull(ParkingLot parkingLot) {
        this.availabityOfParkingLot.replace(parkingLot, false);
    }

    @Override
    public void notifyEmpty(ParkingLot parkingLot) {
        this.availabityOfParkingLot.replace(parkingLot, true);
    }
}
