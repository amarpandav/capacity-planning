import {Component, DestroyRef, OnInit, output} from '@angular/core';
import {UserDto} from "../scheduler/models/user/user.model";
import {DatePipe} from "@angular/common";
import {UserService} from "../user/user.service";
import {Subscription} from "rxjs";
import {UserComponent} from "../user/user.component";
import {UserSearchParameters} from "../user/user.search.parameters";

@Component({
  selector: 'app-user-list',
  standalone: true,
  imports: [
    UserComponent
  ],
  templateUrl: './user-list.component.html',
  styleUrl: './user-list.component.scss'
})
export class UserListComponent implements OnInit {

  //protected readonly users: UserDto[] = USER_TEST_DATA;
  protected users?: UserDto[];
  //@Input() selectedUser!: UserDto | undefined;
 // selectedUser = input.required<UserDto>();
  selectedUser?: UserDto;

  //@Output() selectUserAsOutputEvent = new EventEmitter<UserDto>();
  selectUserAsOutputEvent = output<UserDto>(); //

  constructor(private datePipe: DatePipe,
              private destroyRef: DestroyRef,
              private userService: UserService) {
  }

  ngOnInit(): void {
    //this.isLoadingPlaces.set(true);

    this.findUsers();
  }

  onSelectedUserEventListener(selectedUser: UserDto){
    console.log("I am (SchedulerComponent) consuming emitted user as an Object: " + JSON.stringify(selectedUser));
    this.selectedUser = selectedUser;
    this.selectUserAsOutputEvent.emit(this.selectedUser);
  }

  private findUsers() {
    const subscription1 = this.userService.findUsers(new UserSearchParameters())
        .subscribe({
              next: (users) => {
                this.users = users;
              }
            }
        );

    //Destroy is optional
    this.destroySubscription(subscription1);
  }

  private destroySubscription(subscription: Subscription) {
    this.destroyRef.onDestroy(() => {
      subscription.unsubscribe(); //technical this is not required but its a good idea
    });
  }
}
