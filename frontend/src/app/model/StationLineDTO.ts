export class StationLineDTO {
    id: Number;
    stationNum: Number;
    arrival: Number;
    stationId: Number;
    lineId: Number;
    stationName: String;
    lineName: String;

    constructor() {
        this.id = 0;
        this.stationNum = 0;
        this.arrival = 0;
        this.stationId = 0;
        this.lineId = 0;
        this.stationName = "";
        this.lineName = "";
    }
}