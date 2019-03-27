export class User {
    id: string;
    email: string;
    firstName: string;
    lastName: string;
    role: UserRoleEnum;
    token: string;

    constructor(id:string, email: string, firstName: string, lastName: string, role: UserRoleEnum) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }
}

export enum UserRoleEnum {
    FARMER,
    PRODUCER,
    DISTRIBUTOR,
    RETAILER
}
