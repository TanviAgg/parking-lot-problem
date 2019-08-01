import java.util.List;

class SequentialParkingLotAttendant extends ParkingLotAttendant {
    SequentialParkingLotAttendant(List<ParkingLot> parkingLots) {
        super(parkingLots);
    }

    @Override
    ParkingLot getNextParkingLot() {
        return this.availableParkingLots.iterator().next();
    }
}
