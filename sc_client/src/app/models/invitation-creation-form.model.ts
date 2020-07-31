export class InvitationCreationForm {
    groupId: number;
    userId: number;

  constructor(groupId: number, userId: number) {
    this.groupId = groupId;
    this.userId = userId;
  }
}
