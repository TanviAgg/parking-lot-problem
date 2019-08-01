import java.util.HashSet;

class SequentialParkingStrategy implements ParkingStrategy {
    public ParkingLot chooseFrom(HashSet<ParkingLot> availableParkingLots) {
        return availableParkingLots.iterator().next();
    }
}
