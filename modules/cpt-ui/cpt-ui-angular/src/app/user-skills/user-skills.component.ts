import {Component, effect, ElementRef, input, output, viewChild} from '@angular/core';
import {UserDto} from "../scheduler/models/user/user.model";
import {UserComponent} from "../user/user.component";
import {UserSkillsLevel} from "./user-skills-level.enum";
import {UserSkillsDto} from "./user-skills.model";

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

    userSkillsLevel: UserSkillsLevel[] = [];

    coreEngineeringSkills: UserSkillsDto[] = [];

    securityDomainSkills: UserSkillsDto[] = [];

    protected readonly JSON = JSON;

    private selectedUserChanged = effect(() => {
        if (this.selectedUser()) {
            this.showModal();
        }
    });

    ngOnInit(): void {
        this.userSkillsLevel.push(UserSkillsLevel.NONE);
        this.userSkillsLevel.push(UserSkillsLevel.BEGINNER);
        this.userSkillsLevel.push(UserSkillsLevel.INTERMEDIATE_WORKING);
        this.userSkillsLevel.push(UserSkillsLevel.ADVANCED_PRACTITIONER);
        this.userSkillsLevel.push(UserSkillsLevel.EXPERT_SME);

        this.coreEngineeringSkills.push(new UserSkillsDto("Java", UserSkillsLevel.ADVANCED_PRACTITIONER));
        this.coreEngineeringSkills.push(new UserSkillsDto("Angular", UserSkillsLevel.ADVANCED_PRACTITIONER));
        this.coreEngineeringSkills.push(new UserSkillsDto("React", UserSkillsLevel.BEGINNER));
        this.coreEngineeringSkills.push(new UserSkillsDto(".NET Framework", UserSkillsLevel.NONE));

        this.securityDomainSkills.push(new UserSkillsDto("Authentication/Authorization/SSo/Federation", UserSkillsLevel.INTERMEDIATE_WORKING));
    }


    showModal() {
        this.userSkillsDialogElemRef().nativeElement.showModal();
    }

    closeModal() {
        this.userSkillsDialogElemRef().nativeElement.close();
        this.closeUserSkillsDialogOutput.emit(this.selectedUser());
    }

    onClickOutsideCloseDialog(event: any) {
        if (event.target instanceof HTMLDialogElement) {
            this.closeModal()
        }
    }

    protected readonly onkeyup = onkeyup;

    keydown(event: KeyboardEvent) {
        if (event.key === "Escape") {
            this.closeModal();
        }
    }
}

