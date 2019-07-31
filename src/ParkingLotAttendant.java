// Represents an entity that can park/unpark vehicles in its parkinglot
class ParkingLotAttendant {
    private ParkingLot parkingLot;

    ParkingLotAttendant(ParkingLot parkingLot){
        this.parkingLot = parkingLot;
    }

    void valetPark(Vehicle vehicle) throws UnableToParkException {
        try {
            parkingLot.park(vehicle);
        }
        catch(Exception e){
            throw new UnableToParkException();
        }
    }

    void valetUnpark(Vehicle vehicle) throws UnableToUnparkException {
        try {
            parkingLot.unpark(vehicle);
        }
        catch(Exception e){
            throw new UnableToUnparkException();
        }
    }
}
