import { Address } from './Address';

export class User {
    id?: Number;
    name: string;
    personalNo: string;
    username: string;
    password: string;
    email: string;
    role?: string;
    imagePath?: string;
    address: Address;
    authority?: string;


}