import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root'
})
export class UserModel {
    userId?;
    userName?;
    password?;
}
