import { Item } from "./PriceItem";


//imacemo 2 klase, jednu za citanje podataka sa back-a i drugu za slanje na back 
export class PriceList implements PriceListInterface{
    //za slanje podataka na back
    items : Item[];

    constructor(priceListCfg : PriceListInterface){
        this.items = priceListCfg.items;
    }
}

export interface PriceListInterface {
    id?: number;
    issueDate?: Date;
    items: Item[];

}