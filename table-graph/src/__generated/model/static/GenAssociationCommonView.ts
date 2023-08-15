import type { AssociationType } from '../enums';

export interface GenAssociationCommonView {
    
    associationComment: string;
    
    associationType: AssociationType;
    
    createdTime: string;
    
    id: number;
    
    modifiedTime: string;
    
    orderKey: number;
    
    remark: string;
    
    sourceColumnId: number;
    
    targetColumnId: number;
}
