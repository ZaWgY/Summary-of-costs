export class GroupCreationForm {
    name: string;
    description: string;
    creatorId: number;
    maxAmount: string;

    constructor(name: string, description: string, id: number, maxAmount: string) {
        this.name = name;
        this.description = description;
        this.creatorId = id;
        this.maxAmount = maxAmount;
    }
}
