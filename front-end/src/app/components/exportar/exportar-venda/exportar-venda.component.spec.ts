import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExportarVendaComponent } from './exportar-venda.component';

describe('ExportarVendaComponent', () => {
  let component: ExportarVendaComponent;
  let fixture: ComponentFixture<ExportarVendaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ExportarVendaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ExportarVendaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
