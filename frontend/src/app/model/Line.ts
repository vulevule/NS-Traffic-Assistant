import { StationDTO } from './StationDTO';

export class Line {
    id: Number;
    mark: String;
    name: String;
    type: String;
    zone: String;
    route: Location[];
    stations: StationDTO[];
}