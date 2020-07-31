export class SpendingDto {
  amount: string;
  groupId: string;
  categoryId: string;
  userId: string;

  constructor(amount: string, groupId: string, categoryId: string, userId: string) {
    this.amount = amount;
    this.groupId = groupId;
    this.categoryId = categoryId;
    this.userId = userId;
  }
}
