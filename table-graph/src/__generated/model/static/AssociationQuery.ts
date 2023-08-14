import type { TimeRangeQueryParam } from './';

export interface AssociationQuery {
    
    readonly createdTime?: TimeRangeQueryParam;
    
    readonly ids?: ReadonlyArray<number>;
    
    readonly modifiedTime?: TimeRangeQueryParam;
}
