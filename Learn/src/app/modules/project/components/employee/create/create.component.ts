import { Component, OnInit } from '@angular/core';
import { CommonService } from '../../../../../services/common.service';
import { MessageService } from 'primeng/api';
import { Router } from '@angular/router';
import { UserModel } from 'src/app/Model/user.model';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-create',
  templateUrl: './create.component.html',
  styleUrls: ['./create.component.scss']
})
export class CreateComponent implements OnInit {

  constructor(private commonService: CommonService, private messageService: MessageService,
              private router: Router, private user: UserModel, private auth: AuthService) {
  }

  ngOnInit() {
  }

  loginValidation() {
    const requestURL = 'saveUser';
    this.commonService.postData(requestURL, this.user).subscribe((res: any) => {
    });
  }

  logout(){
    this.auth.logout();
    this.router.navigate(['login']);
  }

}
