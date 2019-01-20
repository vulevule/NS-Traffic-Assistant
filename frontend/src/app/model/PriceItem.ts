

//klasa koja predstavlja stavku cenovnika 

export class Item{
    price : number;
    trafficType : string;
    timeType : string;
    zone : string;
    studentDiscount : number; 
    handycapDiscount : number;
    seniorDiscount : number;

    constructor (itemCfg : ItemInterface){
        this.price = itemCfg.price;
        this.trafficType = itemCfg.trafficType;
        this.zone = itemCfg.zone;
        this.studentDiscount = itemCfg.studentDiscount;
        this.seniorDiscount = itemCfg.seniorDiscount;
        this.handycapDiscount = itemCfg.handycapDiscount;
        this.timeType = itemCfg.timeType;
    }
}

interface ItemInterface{
    price : number;
    trafficType : string;
    timeType : string;
    zone : string;
    studentDiscount : number; 
    handycapDiscount : number;
    seniorDiscount : number;
}




