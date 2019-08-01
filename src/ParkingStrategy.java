import java.util.HashSet;

interface ParkingStrategy {
    ParkingLot chooseFrom(HashSet<ParkingLot> parkingLots);
}
