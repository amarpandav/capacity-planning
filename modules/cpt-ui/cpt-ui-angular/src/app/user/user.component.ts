import {Component, ElementRef, input, viewChild} from '@angular/core';
import {UserDto} from "../scheduler/models/user/user.model";

@Component({
    selector: 'app-user',
    standalone: true,
    imports: [],
    templateUrl: './user.component.html',
    styleUrl: './user.component.scss'
})
export class UserComponent {
    user = input<UserDto>();
    userRole = input<string>();

    private avatarElemRef = viewChild.required<ElementRef<HTMLImageElement>>('avatar');


    //Profile pic not found, so load default profile picture.
    defaultAvatar() {
        this.avatarElemRef().nativeElement.src = "./assets/users/default-profile-pic.jpg";
        //return this.defaultAvatar;
    }

    /*get selectedUserAvatar() {
      return 'assets/users/' + this.user().avatar;
    }*/

    /*selectedUserAvatar= computed(() => {
      //return 'assets/users/' + this.user().avatar;
      return 'assets/users/user-' + Math.floor( Math.random() * 7 ) +".jpg";
    });*/


}
