import { User } from '../model/User';
import { Injectable } from '@angular/core';

@Injectable()
export class LoggedUserService {
    loggedUser: User

    constructor() {}
}