import { Item } from "./PriceItem";
import { NumberValueAccessor } from '@angular/forms/src/directives';


//imacemo 2 klase, jednu za citanje podataka sa back-a i drugu za slanje na back 
export class PriceList implements PriceListInterface{
    //za slanje podataka na back
    items : Item[];

    constructor(priceListCfg : PriceListInterface){
        this.items = priceListCfg.items;
    }
}


export class PriceListReaderDto implements PriceListInterface{
    id : number;
    issueDate : Date;
    items : Item[];

    constructor(pricelistCfg : PriceListInterface){
        this.id = pricelistCfg.id;
        this.issueDate = pricelistCfg.issueDate;
        this.items = pricelistCfg.items;
    }
}


interface PriceListInterface{
    id? : number;
    issueDate? : Date;
    items : Item[]; 
}