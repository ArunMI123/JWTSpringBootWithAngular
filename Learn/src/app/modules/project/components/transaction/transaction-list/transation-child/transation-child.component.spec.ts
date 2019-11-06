import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TransationChildComponent } from './transation-child.component';

describe('TransationChildComponent', () => {
  let component: TransationChildComponent;
  let fixture: ComponentFixture<TransationChildComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TransationChildComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TransationChildComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
