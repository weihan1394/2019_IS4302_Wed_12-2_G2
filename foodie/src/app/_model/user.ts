export class User {
    email: string;
    firstName: string;
    lastName: string;
    userRoleEnum: UserRoleEnum;
    token: string;

    constructor(email: string, firstName: string, lastName: string, userRoleEnum: UserRoleEnum) { 
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userRoleEnum = userRoleEnum;
    }
}

export enum UserRoleEnum {
    FARMER,
    PRODUCER,
    DISTRIBUTOR,
    RETAILER
}
