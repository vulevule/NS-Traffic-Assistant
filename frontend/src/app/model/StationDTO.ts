import { StationLineDTO } from './StationLineDTO';

export class StationDTO {
    id: Number;
    name: String;
    type: String;
    xCoordinate: Number;
    yCoordinate: Number;
    lines: StationLineDTO[];

    constructor() {
        this.id = 0;
        this.name = "";
        this.type = "BUS";
        this.xCoordinate = 0.0;
        this.yCoordinate = 0.0;
        this.lines = [];
        
    }
}