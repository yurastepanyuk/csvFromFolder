import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';

import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { SimpleHttpComponent } from './simple-http/simple-http.component';
import {SimpleHttpServiceService} from './simple-http-service.service';

@NgModule({
  declarations: [
    AppComponent,
    SimpleHttpComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule // <-- right here
  ],
  providers: [SimpleHttpServiceService],
  bootstrap: [AppComponent]
})
export class AppModule { }
