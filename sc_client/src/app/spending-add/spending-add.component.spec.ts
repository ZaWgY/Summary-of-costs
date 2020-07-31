import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SpendingAddComponent } from './spending-add.component';

describe('SpendingAddComponent', () => {
  let component: SpendingAddComponent;
  let fixture: ComponentFixture<SpendingAddComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SpendingAddComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SpendingAddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
