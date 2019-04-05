import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SplitBatchesComponent } from './split-batches.component';

describe('SplitBatchesComponent', () => {
  let component: SplitBatchesComponent;
  let fixture: ComponentFixture<SplitBatchesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SplitBatchesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SplitBatchesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
