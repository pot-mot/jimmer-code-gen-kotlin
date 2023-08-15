import type { TimeRangeQueryParam } from './';

export interface EntityQuery {
    
    createdTime?: TimeRangeQueryParam;
    
    ids?: number[];
    
    modifiedTime?: TimeRangeQueryParam;
}
