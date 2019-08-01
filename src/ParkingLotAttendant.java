import java.util.HashSet;
import java.util.List;

// Represents an entity that can park/unpark vehicles in its parkinglot
class ParkingLotAttendant implements Notifiable {
    private List<ParkingLot> parkingLots;
    private HashSet<ParkingLot> availableParkingLots;
    private ParkingStrategy parkingStrategy;

    ParkingLotAttendant(List<ParkingLot> parkingLots, ParkingStrategy strategy) {
        this.parkingLots = parkingLots;
        this.availableParkingLots = new HashSet<>();
        for (ParkingLot parkingLot : parkingLots) {
            parkingLot.addNotifiable(this);
            availableParkingLots.add(parkingLot);
        }
        this.parkingStrategy = strategy;
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
            this.parkingStrategy.chooseFrom(this.availableParkingLots).park(vehicle);
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

    @Override
    public void notifyFull(ParkingLot parkingLot) {
        this.availableParkingLots.remove(parkingLot);
    }

    @Override
    public void notifyEmpty(ParkingLot parkingLot) {
        this.availableParkingLots.add(parkingLot);
    }
}
