import {Component, input, OnInit, output} from '@angular/core';
import {UserDto} from "../scheduler/models/user/user.model";
import {UserService} from "../user/user.service";
import {UserSearchParameters} from "../user/user.search.parameters";
import {UserKey} from "../scheduler/models/user/userKey.model";
import {PodMemberRole} from "../scheduler/models/pod/pom-member-role.enum";
import {UserComponent} from "../user/user.component";

@Component({
    selector: 'app-user-viewing-box',
    standalone: true,
    imports: [
        UserComponent
    ],
    templateUrl: './user-viewing-box.component.html',
    styleUrl: './user-viewing-box.component.scss'
})
export class UserViewingBoxComponent implements OnInit {

    //Original logged in user before switching. We need that inorder to switch back
    loggedInUserKey = new UserKey("49008491");//Amar Pandav

    selectedUserKey = new UserKey("49008491");//Amar Pandav

    selectedUser?: UserDto;
    selectedUserPodMemberRole =  input<PodMemberRole>();


    selectedUserOutput = output<UserDto>(); //

    constructor(private userService: UserService) {
    }

    ngOnInit(): void {
        let userSearchParameters = new UserSearchParameters();
        userSearchParameters.userKey = this.selectedUserKey;

        //console.log(JSON.stringify(userSearchParameters));

        let observable = this.userService.findUsers(userSearchParameters).subscribe({
            next: (users) => {
                if (users && users.length > 0) {
                    this.selectedUser = users[0];
                    this.selectedUserOutput.emit(this.selectedUser);
                    //console.log("Emitting selectedUserDto");
                }
            }
        });
    }


    /*onSelectedUserPodMemberRoleInput(selectedUserPodMemberRole: PodMemberRole){
        //console.log("I am (SchedulerComponent) consuming emitted user as an Object: " + JSON.stringify(selectedUser));
        this.selectedUserPodMemberRole = selectedUserPodMemberRole;
    }*/

}
