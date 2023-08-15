import type { TimeRangeQueryParam } from './';

export interface AssociationQuery {
    
    createdTime?: TimeRangeQueryParam;
    
    ids?: number[];
    
    modifiedTime?: TimeRangeQueryParam;
}
