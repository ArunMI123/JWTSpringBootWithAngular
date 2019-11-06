import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
//import 'rxjs/add/operator/map';

@Injectable({
  providedIn: 'root'
})
export class CommonService {

  public header = new HttpHeaders();
  public url: string;
  public baseappurl = '';
  private configUrl = 'assets/Json/config.json';
  constructor(private http: HttpClient) {
    this.getConfigUrl();
  }

  getConfigUrl() {
    this.http.get(this.configUrl).subscribe((ENV) => {
      // tslint:disable-next-line: no-string-literal
      this.baseappurl = ENV['baseappurl'];
      sessionStorage.setItem('baseappurl', this.baseappurl);
    }, error => {
      console.log(error);
    });
    this.header = new HttpHeaders({ 'Content-Type': 'application/json' });
  }

  getData(mappingURL: string) {
    // console.log(  sessionStorage.getItem('baseappurl'));
    this.url = this.baseappurl + mappingURL;
    return this.http.get(this.url, { headers: this.header });
        //.map((res: Response) => res);
  }

  postData(mappingURL: string, modelData: any) {
    this.url = this.baseappurl + mappingURL;
    return this.http.post(this.url, modelData);
        //.map((res: Response) => res);
  }

}
