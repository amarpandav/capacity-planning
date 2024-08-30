import {Component, computed, input, output} from '@angular/core';
import {UserDto} from "../scheduler/models/user/user.model";

@Component({
  selector: 'app-user',
  standalone: true,
  imports: [],
  templateUrl: './user.component.html',
  styleUrl: './user.component.scss'
})
export class UserComponent {

  //@Input({required: true}) user !: User;

  user = input.required<UserDto>();
  selected = input.required<boolean>();

  //selectUserIdAsOutput = output<string>(); // you can also emmit object as shown below
  selectUserAsOutputEvent = output<UserDto>(); //

  /*get selectedUserAvatar() {
    return 'assets/users/' + this.user().avatar;
  }*/

  selectedUserAvatar= computed(() => {
    //return 'assets/users/' + this.user().avatar;
    return 'assets/users/user-' + Math.floor( Math.random() * 7 ) +".jpg";
  });

  onSelectUser() {
    console.log("app-object-as-input-sample4.onSelectUser, emitting user as an Object: " + JSON.stringify(this.user()));
    //this.selectUserIdAsOutput.emit(this.user().id);
    this.selectUserAsOutputEvent.emit(this.user());
  }
}
