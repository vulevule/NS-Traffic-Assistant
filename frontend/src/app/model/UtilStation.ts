import { StationDTO } from './StationDTO';

export class UtilStation {
    arrival: Number;
    station: StationDTO;

    constructor(station: StationDTO) {
        this.station = station;
        this.arrival = 0;
    }
}