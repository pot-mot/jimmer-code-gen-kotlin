import type { AssociationType } from '../enums';

export interface GenAssociationPreviewView {
    
    readonly associationComment: string;
    
    readonly associationType: AssociationType;
    
    readonly createdTime: string;
    
    readonly modifiedTime: string;
    
    readonly orderKey: number;
    
    readonly remark: string;
}
