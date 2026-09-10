import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { ConsultaFestivosComponent } from './componentes/consulta-festivos/consulta-festivos.component';
@NgModule({ declarations: [AppComponent, ConsultaFestivosComponent], imports: [BrowserModule, HttpClientModule], providers: [], bootstrap: [AppComponent] })
export class AppModule {}