import { LocationDTO } from './LocationDTO';
import { StationLineDTO } from './StationLineDTO';

export class LineDTO {
    id: Number;
    name: String;
    type: String;
    zone: String;
    route: LocationDTO[];
    timeTable: Number;
    stations: StationLineDTO[];

    constructor() {
        this.id = 0;
        this.name = "";
        this.type = "BUS";
        this.zone = "FIRST";
        this.route = [];
        this.timeTable = 0;
        this.stations = []
    }
}