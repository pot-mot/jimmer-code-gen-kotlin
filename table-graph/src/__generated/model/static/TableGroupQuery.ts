import type { TimeRangeQueryParam } from './';

export interface TableGroupQuery {
    
    createdTime?: TimeRangeQueryParam;
    
    ids?: number[];
    
    modifiedTime?: TimeRangeQueryParam;
}
