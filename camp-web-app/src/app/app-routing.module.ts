import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { HomeComponent } from './home/home.component';
import { AboutComponent } from './about/about.component';
import { LeadersComponent } from './leaders/leaders.component';
import { YoungLeadersComponent } from './young-leaders/young-leaders.component';
import { RegistrationComponent } from './registration/registration.component';
import { ActiveCampsComponent } from './active-camps/active-camps.component';
import { OldCampsComponent } from './old-camps/old-camps.component';
import { ContactComponent } from './contact/contact.component';


const routes: Routes = [
  {path: "", pathMatch: "full", redirectTo: "/"},
  {path: "", component: HomeComponent},
  {path: "rolunk", component: AboutComponent},
  {path: "vezetok", component: LeadersComponent},
  {path: "ifi-vezetok", component: YoungLeadersComponent},
  {path: "jelentkezes", component: RegistrationComponent},
  {path: "taborok", component: ActiveCampsComponent},
  {path: "regi-taborok", component: OldCampsComponent},
  {path: "kapcsolat", component: ContactComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes), FormsModule],
  exports: [RouterModule]
})
export class AppRoutingModule { }
