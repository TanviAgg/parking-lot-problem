class DistributedParkingStrategy implements ParkingStrategy {
    public ParkingLot getNextParkingLot(ParkingLotAttendant attendant) {
        ParkingLot lot = attendant.availableParkingLots().iterator().next();
        for(ParkingLot parkingLot: attendant.availableParkingLots()){
            if(parkingLot.compareTo(lot) > 0){
                lot = parkingLot;
            }
        }
        return lot;
    }
}