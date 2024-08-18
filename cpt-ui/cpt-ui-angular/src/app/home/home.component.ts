import {Component} from '@angular/core';
import {SchedulerComponent} from "../scheduler/scheduler.component";
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    SchedulerComponent,
    FormsModule
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {



}
