import type { TimeRangeQueryParam } from './';

export interface TableQuery {
    
    createdTime?: TimeRangeQueryParam;
    
    ids?: number[];
    
    modifiedTime?: TimeRangeQueryParam;
}
