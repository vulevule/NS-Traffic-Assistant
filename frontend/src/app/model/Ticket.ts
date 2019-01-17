export class BuyTicket implements TicketInterface{
    trafficType : string;
    trafficZone : string;
    ticketTime : string; 
    price : number;

    constructor(ticketCfg:TicketInterface){
        this.trafficType = ticketCfg.trafficType;
        this.trafficZone = ticketCfg.trafficZone;
        this.ticketTime = ticketCfg.ticketTime;
        this.price = ticketCfg.price;
    }
    
}


export class Ticket implements TicketInterface {
    id : number;
    trafficType : string;
    trafficZone : string;
    ticketTime : string;
    price : number;
    expirationDate : Date;
    issueDate : Date;
    userTicketType : string;
    serialNo : string;

    constructor(ticketCfg:TicketInterface)
    {
        this.id = ticketCfg.id;
        this.trafficType = ticketCfg.trafficType;
        this.trafficZone = ticketCfg.trafficZone;
        this.ticketTime = ticketCfg.ticketTime;
        this.price = ticketCfg.price;
        this.userTicketType = ticketCfg.userTicketType;
        this.expirationDate = ticketCfg.expirationDate;
        this.issueDate = ticketCfg.issueDate;
        this.serialNo = ticketCfg.serialNo;
    }
}

interface TicketInterface{
    id? : number;
    trafficType : string;
    trafficZone : string;
    ticketTime : string;
    price? : number;
    expirationDate? : Date;
    issueDate? : Date;
    userTicketType? : string;
    serialNo? : string;
}