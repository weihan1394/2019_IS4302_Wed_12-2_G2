export class User {
    id: string;
    email: string;
    firstName: string;
    lastName: string;
    userRoleEnum: UserRoleEnum;
    token: string;

    constructor(id:string, email: string, firstName: string, lastName: string, userRoleEnum: UserRoleEnum) {
        this.id = id;
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
