import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

// Represents an entity that can park/unpark vehicles in its parkinglot
class ParkingLotAttendant implements Notifiable {
    private List<ParkingLot> parkingLots;
    protected HashSet<ParkingLot> availableParkingLots;
    private ParkingStrategy parkingStrategy;

    enum Strategy{
        SEQUENTIAL,
        DISTRIBUTED
    }

    ParkingLotAttendant(List<ParkingLot> parkingLots, Strategy strategy) {
        this.parkingLots = parkingLots;
        this.availableParkingLots = new HashSet<>();
        for (ParkingLot parkingLot : parkingLots) {
            parkingLot.addNotifiable(this);
            availableParkingLots.add(parkingLot);
        }
        this.setParkingStrategy(strategy);
    }

    private void setParkingStrategy(Strategy strategy){
        if(strategy == Strategy.SEQUENTIAL){
            this.parkingStrategy = new SequentialParkingStrategy();
        }
        if(strategy == Strategy.DISTRIBUTED){
            this.parkingStrategy = new DistributedParkingStrategy();
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
            this.parkingStrategy.getNextParkingLot(this).park(vehicle);
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
