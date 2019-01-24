import { ItemInterface } from "./PriceItem";

export interface PriceListInterface {
    id?: number;
    issueDate?: Date;
    items: ItemInterface[];

}