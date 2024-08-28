import {Component, OnInit} from '@angular/core';
import {AppService} from "./help-me.service";

@Component({
  selector: 'app-help-me',
  standalone: true,
  imports: [
  ],
  templateUrl: './help-me.component.html',
  styleUrl: './help-me.component.scss'
})
export class HelpMeComponent implements OnInit {

  appVersion: string;

  constructor(private service: AppService) {
  }


  ngOnInit(): void {
    this.service.getVersion().subscribe(response => {
      this.appVersion = response.version;
    })
  }


}
