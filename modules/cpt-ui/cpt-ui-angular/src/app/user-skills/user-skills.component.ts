import {Component, effect, ElementRef, input, output, viewChild} from '@angular/core';
import {UserDto} from "../scheduler/models/user/user.model";
import {UserComponent} from "../user/user.component";

@Component({
    selector: 'app-user-skills',
    standalone: true,
    imports: [
        UserComponent
    ],
    templateUrl: './user-skills.component.html',
    styleUrl: './user-skills.component.scss'
})
export class UserSkillsComponent {

    selectedUser = input<UserDto>();

    closeUserSkillsDialogOutput = output<UserDto | undefined>();

    private userSkillsDialogElemRef = viewChild.required<ElementRef<HTMLDialogElement>>('userSkillsDialog');

    protected readonly JSON = JSON;

    private selectedUserChanged = effect(() => {
        if (this.selectedUser()) {
            this.showModal();
        }
   });

    ngOnInit(): void {

    }


    showModal() {
        this.userSkillsDialogElemRef().nativeElement.showModal();
    }

    closeModal() {
        this.userSkillsDialogElemRef().nativeElement.close();
        this.closeUserSkillsDialogOutput.emit(this.selectedUser());
    }

    protected readonly onkeyup = onkeyup;

    keydown(event: KeyboardEvent) {
        if (event.key === "Escape") {
            this.closeModal();
        }
    }
}
