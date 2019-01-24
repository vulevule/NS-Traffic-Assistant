import { LocationDTO } from './LocationDTO';
import { StationLineDTO } from './StationLineDTO';

export class LineDTO {
    id: Number;
    mark: String;
    name: String;
    type: String;
    zone: String;
    route: LocationDTO[];
    stations: StationLineDTO[];

    constructor() {
        this.id = 0;
        this.mark = "";
        this.name = "";
        this.type = "BUS";
        this.zone = "FIRST";
        this.route = [];
        this.stations = []
    }
}