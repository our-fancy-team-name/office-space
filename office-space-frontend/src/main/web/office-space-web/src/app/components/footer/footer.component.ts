import { Component, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.scss']
})
export class FooterComponent implements OnInit {

  constructor(private t: TranslateService) { }

  ngOnInit(): void {
  }

  onClickLanguage(lang: string) {
    this.t.use(lang);
  }
}
