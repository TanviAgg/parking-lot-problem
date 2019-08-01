class SequentialParkingStrategy implements ParkingStrategy {
    public ParkingLot getNextParkingLot(ParkingLotAttendant attendant) {
        return attendant.availableParkingLots().iterator().next();
    }
}
