import java.util.HashSet;

class DistributedParkingStrategy implements ParkingStrategy {
    public ParkingLot chooseFrom(HashSet<ParkingLot> availableParkingLots) {
        ParkingLot lot = availableParkingLots.iterator().next();
        for(ParkingLot parkingLot: availableParkingLots){
            if(parkingLot.compareTo(lot) > 0){
                lot = parkingLot;
            }
        }
        return lot;
    }
}