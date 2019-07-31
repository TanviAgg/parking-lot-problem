class ParkingLotAttendant {
    private ParkingLot parkingLot;

    ParkingLotAttendant(ParkingLot parkingLot){
        this.parkingLot = parkingLot;
    }

    void valetPark(Vehicle vehicle) throws AlreadyParkedException, ParkingLotFullException {
        parkingLot.park(vehicle);
    }

    void valetUnpark(Vehicle vehicle) throws VehicleNotParkedException {
        parkingLot.unpark(vehicle);
    }
}
