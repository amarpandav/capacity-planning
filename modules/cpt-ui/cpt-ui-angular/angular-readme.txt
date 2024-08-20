1. install node (nodejs-readme.txt)
because angular needs nodejs, npm and to install nodejs and npm you need nvm
2. run in terminal anywhere > npm install -g @angular/cli
3. check installed version > ng --version or ng version
ngular CLI: 18.0.1
Node: 20.12.2
Package Manager: npm 10.7.0
OS: darwin arm64

4. amarpandav@Amars-MBP ~ % cd /Users/amarpandav/Code/AWS

5. ng new hello-angular
ng command is from @angular/cli
select saas and server side rendering as n

6. if you are using visual code studio, install following extensions
Angular Language Service
Angular Essentials (Version 17)


https://git-codecommit.eu-central-1.amazonaws.com/v1/repos/hello-angular


7. main.ts file: Is the main file which runs when the angular code runs firstime in the client's browser. Main.ts bootstraps AppComponent.ts

8. create a new component > naviate to the directory where you wanna create a folder
ng generate component xxx
ng generate component user
ng g c user
ng g c user --skip-tests

9. to create directives
ng g directive
ng g d
ng g d --skip-tests

10 to create pipe
ng g pipe
ng g p
ng g p --skip-tests
