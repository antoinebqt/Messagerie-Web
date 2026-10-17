import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule, Routes } from '@angular/router';

import { AppComponent } from './app.component';
import { HomeComponent } from './components/home/home.component';
import { HeaderComponent } from './components/header/header.component';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import { FormsModule } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';
import {MatSelectModule} from "@angular/material/select";
import {MAT_FORM_FIELD_DEFAULT_OPTIONS} from "@angular/material/form-field";
import {MatButtonModule} from '@angular/material/button';
import {ChatComponent} from "./components/chat/chat.component";
import {MatSlideToggleModule} from "@angular/material/slide-toggle";
import {LoginComponent} from "./components/login/login.component";
import {HttpClientModule} from "@angular/common/http";
import {RequestHelper} from "./services/RequestHelper";
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MapComponent } from './components/chat/map/map.component';
import { LifespanTimePipe } from './pipes/lifespan-time.pipe';



const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: '', redirectTo: '/login', pathMatch: 'full' }, // default page
];

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    HeaderComponent,
    HeaderComponent,
    ChatComponent,
    MapComponent,
    LifespanTimePipe
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot(routes),
    BrowserAnimationsModule,
    FormsModule,
    BrowserAnimationsModule,
    MatIconModule,
    MatSelectModule,
    MatSnackBarModule,
    MatButtonModule,
    MatSlideToggleModule,
    HttpClientModule
  ],
  providers: [
    {provide: MAT_FORM_FIELD_DEFAULT_OPTIONS, useValue: {appearance: 'outline'}},
    RequestHelper
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
