

interface ReportInterface {
    numOfStudentMonthTicket: number;
    numOfHandycapMonthTicket: number;
    numOfSeniorMonthTicket: number;
    numOfRegularMonthTicket: number;

    // godisnje
    numOfStudentYearTicket: number;
    numOfHandycapYearTicket: number;
    numOfSeniorYearTicket: number;
    numOfRegularYearTicket: number;

    // single
    numOfStudentSingleTicket: number;
    numOfHandycapSingleTicket: number;
    numOfSeniorSingleTicket: number;
    numOfRegularSingleTicket: number;

    // dnevne
    numOfStudentDailyTicket: number;
    numOfHandycapDailyTicket: number;
    numOfSeniorDailyTicket: number;
    numOfRegularDailyTicket: number;

    // broj kupljenih karti za odredjeni prevoz
    numOfBusTicket: number;
    numOfMetroTicket: number;
    numOfTramTicket: number;

    // zarada
    money: number;

}

export class Report implements ReportInterface {
    numOfStudentMonthTicket: number;
    numOfHandycapMonthTicket: number;
    numOfSeniorMonthTicket: number;
    numOfRegularMonthTicket: number;

    // godisnje
    numOfStudentYearTicket: number;
    numOfHandycapYearTicket: number;
    numOfSeniorYearTicket: number;
    numOfRegularYearTicket: number;

    // single
    numOfStudentSingleTicket: number;
    numOfHandycapSingleTicket: number;
    numOfSeniorSingleTicket: number;
    numOfRegularSingleTicket: number;

    // dnevne
    numOfStudentDailyTicket: number;
    numOfHandycapDailyTicket: number;
    numOfSeniorDailyTicket: number;
    numOfRegularDailyTicket: number;

    // broj kupljenih karti za odredjeni prevoz
    numOfBusTicket: number;
    numOfMetroTicket: number;
    numOfTramTicket: number;

    // zarada
    money: number;

    constructor(reportCfg: ReportInterface) {
        this.numOfStudentMonthTicket = reportCfg.numOfStudentMonthTicket;
        this.numOfHandycapMonthTicket = reportCfg.numOfHandycapMonthTicket;
        this.numOfSeniorMonthTicket = reportCfg.numOfSeniorMonthTicket;
        this.numOfRegularMonthTicket = reportCfg.numOfRegularMonthTicket;

        // godisnje
        this.numOfStudentYearTicket = reportCfg.numOfStudentYearTicket;
        this.numOfHandycapYearTicket = reportCfg.numOfHandycapYearTicket;
        this.numOfSeniorYearTicket = reportCfg.numOfSeniorYearTicket;
        this.numOfRegularYearTicket = reportCfg.numOfRegularYearTicket;

        // single
        this.numOfStudentSingleTicket = reportCfg.numOfStudentSingleTicket;
        this.numOfHandycapSingleTicket = reportCfg.numOfHandycapSingleTicket;
        this.numOfSeniorSingleTicket = reportCfg.numOfSeniorSingleTicket;
        this.numOfRegularSingleTicket = reportCfg.numOfRegularSingleTicket;

        // dnevne
        this.numOfStudentDailyTicket = reportCfg.numOfStudentDailyTicket;
        this.numOfHandycapDailyTicket = reportCfg.numOfHandycapDailyTicket;
        this.numOfSeniorDailyTicket = reportCfg.numOfSeniorDailyTicket;
        this.numOfRegularDailyTicket = reportCfg.numOfRegularDailyTicket;

        // broj kupljenih karti za odredjeni prevoz
        this.numOfBusTicket = reportCfg.numOfBusTicket;
        this.numOfMetroTicket = reportCfg.numOfMetroTicket;
        this.numOfTramTicket = reportCfg.numOfTramTicket;

        // zarada
        this.money = reportCfg.money;
    }
}

export class Month {
    id : number; 
    value : string;
}