import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { SignupComponent } from '../signup/signup.component';
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  constructor(private dialog:MatDialog) { }

  ngOnInit(): void {
  }
  

handleSignUpAction(){
  const dialogConfig = new MatDialogConfig();
  dialogConfig.width="1550px";
  this.dialog.open(SignupComponent,dialogConfig);
}

}
