import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CombosComponent } from './combos';

describe('Combos', () => {
  let component: CombosComponent;
  let fixture: ComponentFixture<CombosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CombosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CombosComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
