import type { AssociationType } from '../enums';

export interface GenAssociationCommonInput {
    
    associationComment: string;
    
    associationType: AssociationType;
    
    id: number;
    
    orderKey: number;
    
    remark: string;
    
    sourceColumnId: number;
    
    targetColumnId: number;
}
