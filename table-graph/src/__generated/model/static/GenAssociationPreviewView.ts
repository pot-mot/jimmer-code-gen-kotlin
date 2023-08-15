import type { AssociationType } from '../enums';

export interface GenAssociationPreviewView {
    
    associationComment: string;
    
    associationType: AssociationType;
    
    createdTime: string;
    
    modifiedTime: string;
    
    orderKey: number;
    
    remark: string;
}
