import java.util.HashMap;
import java.util.List;

// Represents an entity that can park/unpark vehicles in its parkinglot
class ParkingLotAttendant {
    private List<ParkingLot> parkingLots;

    ParkingLotAttendant(List<ParkingLot> parkingLots) {
        this.parkingLots = parkingLots;
    }

    void valetPark(Vehicle vehicle) throws UnableToParkException{
        for (ParkingLot parkingLot : parkingLots) {
            if (parkingLot.isParked(vehicle)) {
                throw new UnableToParkException();
            }
            if (parkingLot.isFull()) {
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
}
