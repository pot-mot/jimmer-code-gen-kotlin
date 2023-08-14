import type { AssociationType } from '../enums';

export interface GenAssociationCommonView {
    
    readonly associationComment: string;
    
    readonly associationType: AssociationType;
    
    readonly createdTime: string;
    
    readonly id: number;
    
    readonly modifiedTime: string;
    
    readonly orderKey: number;
    
    readonly remark: string;
    
    readonly sourceColumnId: number;
    
    readonly targetColumnId: number;
}
