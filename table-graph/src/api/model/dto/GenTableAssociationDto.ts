export type GenTableAssociationDto = {
    'DEFAULT': {
        readonly createdTime: string, 
        readonly modifiedTime: string, 
        readonly remark: string, 
        readonly id: number, 
        readonly tableAssociationName: string, 
        readonly masterTable: {readonly id: number}, 
        readonly masterColumn: {readonly id: number}, 
        readonly slaveTable: {readonly id: number}, 
        readonly slaveColumn: {readonly id: number}, 
        readonly associationCategory: string
    }
}