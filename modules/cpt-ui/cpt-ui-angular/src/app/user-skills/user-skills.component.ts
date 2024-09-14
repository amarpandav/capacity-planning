import {Component, ElementRef, input, OnInit, viewChild} from '@angular/core';
import {UserDto} from "../scheduler/models/user/user.model";

@Component({
  selector: 'app-user-skills',
  standalone: true,
  imports: [],
  templateUrl: './user-skills.component.html',
  styleUrl: './user-skills.component.scss'
})
export class UserSkillsComponent implements OnInit{


  selectedUserForDetails =  input<UserDto>();

  private userSkillsDialogElemRef = viewChild.required<ElementRef<HTMLDialogElement>>('userSkillsDialog');

  protected readonly JSON = JSON;

  ngOnInit(): void {
    this.showModal();
  }

  showModal() {
        this.userSkillsDialogElemRef().nativeElement.showModal();
  }

  onCloseModal() {
    this.userSkillsDialogElemRef().nativeElement.close();
  }
}
