import { LineDTO } from './LineDTO';
import { Address } from './Address';

export class Station {
    id: Number;
    name: String;
    type: String;
    xCoordinate: Number;
    yCoordinate: Number;
    lines: LineDTO[];
}