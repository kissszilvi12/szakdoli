import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RegistrationComponent } from './registration/registration.component';
import { AboutComponent } from './about/about.component';
import { LeadersComponent } from './leaders/leaders.component';
import { YoungLeadersComponent } from './young-leaders/young-leaders.component';
import { ActiveCampsComponent } from './active-camps/active-camps.component';
import { OldCampsComponent } from './old-camps/old-camps.component';
import { ContactComponent } from './contact/contact.component';
import { HomeComponent } from './home/home.component';
import { HeaderComponent } from './header/header.component';
import { Routes } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { ConfirmSubmitComponent } from './confirm-submit/confirm-submit.component';

const routes: Routes = []

@NgModule({
  declarations: [
    AppComponent,
    RegistrationComponent,
    AboutComponent,
    LeadersComponent,
    YoungLeadersComponent,
    ActiveCampsComponent,
    OldCampsComponent,
    ContactComponent,
    HomeComponent,
    HeaderComponent,
    ConfirmSubmitComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
