import { HttpClient } from '@angular/common/http';
import { AfterContentInit, AfterViewInit, Component, Input, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { NgxSpinnerService } from 'ngx-spinner';
import { debounceTime } from 'rxjs/operators';
import { TableSearchRequest } from 'src/app/dtos/tableSearch';
import { ValidatorsService } from 'src/app/utils/validators.service';

@Component({
  selector: 'app-select-search',
  templateUrl: './select-search.component.html',
  styleUrls: ['./select-search.component.scss']
})
export class SelectSearchComponent implements OnInit, AfterViewInit, AfterContentInit {

  @Input() identifier;
  @Input() label;
  @Input() options;
  @Input() url;
  @Input() tableSearch: TableSearchRequest;
  @Input() enableBackEndSearch = false;
  @Input() displayField = '';
  @Input() isRequired = false;

  spinnerName = 'sp';

  searchCtr = new FormControl('');
  displayedOptions = [];
  totalElementsLeft = 0;

  selectCtr = new FormControl();
  constructor(
    private http: HttpClient,
    public validator: ValidatorsService,
    private spinner: NgxSpinnerService
  ) { }

  ngAfterContentInit(): void {
    if (this.isRequired) {
      this.selectCtr = new FormControl('', [this.validator.required()]);
    }
    setTimeout(() => {
      if (this.enableBackEndSearch) {
        this.search('');
      } else {
        this.displayedOptions = this.options;
      }
    });
  }

  ngAfterViewInit(): void {
    this.searchCtr.valueChanges.pipe(debounceTime(500)).subscribe(res => {
      this.search(res);
    });
  }

  ngOnInit(): void {
  }

  search(value) {
    if (this.enableBackEndSearch) {
      this.spinner.show(this.spinnerName);
      const tableSearchRequest = this.tableSearch;
      tableSearchRequest.columnSearchRequests.forEach(i => i.term = value);
      this.http.post(this.url, tableSearchRequest).subscribe((res: any) => {
        this.displayedOptions = res.content;
        this.totalElementsLeft = res.totalElements - res.numberOfElements;
        this.spinner.hide(this.spinnerName);
      });
    } else {
      this.displayedOptions = this.options.filter(i => i.includes(value));
    }
  }

  isShowclear() {
    return this.searchCtr.value !== '';
  }

  clear() {
    this.searchCtr.setValue('');
  }

  getValue() {
    return this.selectCtr.value;
  }

  setValue(value) {
    return this.selectCtr.setValue(value);
  }

  compareObjects(thiz: any, that: any) {
    if (thiz == null && that == null) {
      return true;
    }
    if ((thiz != null && that == null) || (thiz == null && that != null)) {
      return false;
    }
    const compareImpl = (tiz, tat) => {
      for (const key of Object.keys(tiz)) {
        if (!Object.prototype.hasOwnProperty.call(tat, key) || tiz[key] !== tat[key]) {
            return false;
        }
      }
      return true;
    };
    return compareImpl(that, thiz) && compareImpl(thiz, that);
  }
}
