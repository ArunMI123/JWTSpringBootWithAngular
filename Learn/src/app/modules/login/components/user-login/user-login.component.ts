import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { CommonService } from '../../../../services/common.service';
import { MessageService } from 'primeng/api';
import { Router } from '@angular/router';
import { UserModel } from 'src/app/Model/user.model';

@Component({
  selector: 'app-user-login',
  templateUrl: './user-login.component.html',
  styleUrls: ['./user-login.component.scss']
})
export class UserLoginComponent implements OnInit {

  public blankSpacePattern = '[a-zA-Z0-9_@./#&*`~$%^&?|,;:!<>\\[\\]\\(\\)\\{\\}\\\\+-]+.*$';
  public userNameFlag: boolean = false;
  //@ViewChild('LoginId') private elementRef: ElementRef;
  constructor(private commonService: CommonService, private messageService: MessageService,
    private router: Router, private user: UserModel) {
  }
  ngOnInit() {
    // this.elementRef.nativeElement.focus();
    localStorage.removeItem('Authorization');
  }
  loginValidation() {
    const requestURL = 'db/login';
    this.commonService.postData(requestURL, this.user).subscribe((res: any) => {
      // if (res.status.toString() === 'Error') {
      //   this.showConfirm(res.statusText);
      // }
      console.log('res', res);
      localStorage.setItem('Authorization', res.jwt);
      this.router.navigate(['project/employee']);
    });
  }

  showConfirm(detailMsg) {
    this.messageService.clear();
    this.messageService.add({ key: 'c', sticky: true, severity: 'warn', summary: 'Invalid Credentials', detail: detailMsg });
  }
  onReject() {
    this.messageService.clear('c');
  }
  loginPattern(formData) {
    this.userNameFlag = formData.controls.userName.invalid || formData.controls.userName.errors != null ? true : false;
  }
}
