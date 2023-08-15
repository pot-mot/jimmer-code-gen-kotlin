import type { TimeRangeQueryParam } from './';

export interface ColumnQuery {
    
    createdTime?: TimeRangeQueryParam;
    
    ids?: number[];
    
    modifiedTime?: TimeRangeQueryParam;
}
