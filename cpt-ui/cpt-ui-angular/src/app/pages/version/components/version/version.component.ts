import {Component, OnInit} from '@angular/core';
import { Component } from '@angular/core';
import {VersionService} from "../../services/version.service";

@Component({
  selector: 'app-version',
  templateUrl: './version.component.html',
  styleUrls: ['./version.component.scss']
})
export class VersionComponent implements OnInit{

  appName: string;
  appVersion: string;

  constructor(private versionService: VersionService) {
  }

  ngOnInit(): void {
    this.versionService.getVersion().subscribe(response => {
      this.appVersion = response.appVersion;
      this.appName = response.appName;
    });
  }


}
