import { Station } from './Station';
import { User } from './User';

export class Address {
    id: Number;
    street: String;
    city: String;
    zip: Number;
    users: User[];
    station: Station[]
}