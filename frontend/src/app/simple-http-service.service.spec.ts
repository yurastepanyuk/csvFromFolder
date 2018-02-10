import { TestBed, inject } from '@angular/core/testing';

import { SimpleHttpServiceService } from './simple-http-service.service';

describe('SimpleHttpServiceService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [SimpleHttpServiceService]
    });
  });

  it('should be created', inject([SimpleHttpServiceService], (service: SimpleHttpServiceService) => {
    expect(service).toBeTruthy();
  }));
});
