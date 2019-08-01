import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

// Represents an entity that can park/unpark vehicles in its parkinglot
abstract class ParkingLotAttendant implements Notifiable {
    protected List<ParkingLot> parkingLots;
    protected HashSet<ParkingLot> availableParkingLots;

    ParkingLotAttendant(List<ParkingLot> parkingLots) {
        this.parkingLots = parkingLots;
        this.availableParkingLots = new HashSet<>();
        for (ParkingLot parkingLot : parkingLots) {
            parkingLot.addNotifiable(this);
            availableParkingLots.add(parkingLot);
        }
    }

    void assignParkingLot(ParkingLot parkingLot) {
        this.parkingLots.add(parkingLot);
        parkingLot.addNotifiable(this);
        this.availableParkingLots.add(parkingLot);
    }

    void valetPark(Vehicle vehicle) throws UnableToParkException {
        for (ParkingLot parkingLot : parkingLots) {
            if (parkingLot.isParked(vehicle)) {
                throw new UnableToParkException();
            }
        }
        try {
            this.getNextParkingLot().park(vehicle);
            return;
        } catch (Exception e) {
            throw new UnableToParkException();
        }
    }

    void valetUnpark(Vehicle vehicle) throws UnableToUnparkException {
        for (ParkingLot parkingLot : parkingLots) {
            if (parkingLot.isParked(vehicle)) {
                try {
                    parkingLot.unpark(vehicle);
                    return;
                } catch (Exception e) {
                    throw new UnableToUnparkException();
                }
            }
        }
        throw new UnableToUnparkException();
    }

    abstract ParkingLot getNextParkingLot();

    @Override
    public void notifyFull(ParkingLot parkingLot) {
        this.availableParkingLots.remove(parkingLot);
    }

    @Override
    public void notifyEmpty(ParkingLot parkingLot) {
        this.availableParkingLots.add(parkingLot);
    }
}
