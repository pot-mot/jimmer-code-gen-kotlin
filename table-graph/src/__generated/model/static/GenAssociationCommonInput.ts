import type { AssociationType } from '../enums';

export interface GenAssociationCommonInput {
    
    readonly associationComment: string;
    
    readonly associationType: AssociationType;
    
    readonly id: number;
    
    readonly orderKey: number;
    
    readonly remark: string;
    
    readonly sourceColumnId: number;
    
    readonly targetColumnId: number;
}
