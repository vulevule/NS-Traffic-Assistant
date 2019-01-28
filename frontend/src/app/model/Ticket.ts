export interface TicketInterface {
    id?: number;
    trafficType: string;
    trafficZone: string;
    timeType: string;
    price?: number;
    expirationDate?: Date;
    issueDate?: Date;
    userType?: string;
    serialNo?: string;
}

export interface TicketReaderInterface {
    tickets: TicketInterface[];
    numOfTickets: number;
}