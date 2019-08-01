import java.util.List;

class DistributedParkingLotAttendant extends ParkingLotAttendant {
    DistributedParkingLotAttendant(List<ParkingLot> parkingLots) {
        super(parkingLots);
    }

    @Override
    ParkingLot getNextParkingLot() {
        ParkingLot lot = availableParkingLots.iterator().next();
        for(ParkingLot parkingLot: this.availableParkingLots){
            if(parkingLot.compareTo(lot) > 0){
                lot = parkingLot;
            }
        }
        return lot;
    }
}