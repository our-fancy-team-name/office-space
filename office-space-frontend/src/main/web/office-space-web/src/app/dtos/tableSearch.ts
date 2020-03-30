import { DataBaseOperation, DataBaseDirection } from '../enums/tableSearchEnum';

export class TableSearchRequest {
    columnSearchRequests: ColumnSearchRequest[];
    pagingRequest: TablePagingRequest;
    sortingRequest: TableSortingRequest;

}

export class ColumnSearchRequest {
    columnName: string;
    operation: DataBaseOperation;
    term: string;
    isOrTerm: boolean;
}

export class TablePagingRequest {
    page: number;
    pageSize: number;
}

export class TableSortingRequest {
    columnName: string;
    direction: DataBaseDirection;
}