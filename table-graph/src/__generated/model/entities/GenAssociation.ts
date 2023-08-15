import type { AssociationType } from '../enums';
import type { GenColumn } from './';

export interface GenAssociation {
    
    createdTime: string;
    
    modifiedTime: string;
    
    remark: string;
    
    id: number;
    
    associationComment: string;
    
    sourceColumn: GenColumn;
    
    sourceColumnId: number;
    
    targetColumn: GenColumn;
    
    targetColumnId: number;
    
    associationType: AssociationType;
    
    orderKey: number;
}
