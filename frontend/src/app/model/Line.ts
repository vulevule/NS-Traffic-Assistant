import { StationDTO } from './StationDTO';

export class Line {
    id: Number;
    name: String;
    type: String;
    zone: String;
    route: Location[];
    timeTable: Number;
    stations: StationDTO[];
}