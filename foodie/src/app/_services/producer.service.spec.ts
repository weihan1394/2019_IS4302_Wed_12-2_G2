import { TestBed } from '@angular/core/testing';

import { ProducerService } from './producer.service';

describe('ProducerService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ProducerService = TestBed.get(ProducerService);
    expect(service).toBeTruthy();
  });
});
