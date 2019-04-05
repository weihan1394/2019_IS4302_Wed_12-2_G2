import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewBatchDetailsComponent } from './view-batch-details.component';

describe('ViewBatchDetailsComponent', () => {
  let component: ViewBatchDetailsComponent;
  let fixture: ComponentFixture<ViewBatchDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewBatchDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewBatchDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
