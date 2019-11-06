import { Component, OnInit, Input, EventEmitter, Output, OnChanges } from '@angular/core';
import { TransactionListComponent } from '../transaction-list.component';

@Component({
  selector: 'app-transation-child',
  templateUrl: './transation-child.component.html',
  styleUrls: ['./transation-child.component.scss']
})
export class TransationChildComponent implements OnInit, OnChanges {

  constructor(private transactionList: TransactionListComponent) { }

  @Input() stock: number;
  @Input() productId: number;
  @Output() stockValueChange = new EventEmitter();
  color = '';
  updatedstockvalue: number;
  public i = 0;
  ngOnInit() {
    this.i = this.i + 1;
    console.log(this.i);

    this.transactionList.getParent();
  }

  stockValueChanged() {
    this.stockValueChange.emit({ id: this.productId, updatdstockvalue: this.updatedstockvalue });
    this.updatedstockvalue = null;
  }

  ngOnChanges() {
    if (this.stock > 10) {
      this.color = 'green';
    } else {
      this.color = 'red';
    }
  }

}
