export const tables = [{
    id: 1,
    tableName: 't1',
    tableComment: '表1',
    columns: [{
        id: 1,
        columnName: 'c1',
        columnComment: '列1'
    },{
        id: 2,
        columnName: 'c2',
        columnComment: '列2'
    }]
}, {
    id: 2,
    tableName: 't2',
    tableComment: '表2',
    columns: [{
        id: 3,
        columnName: 'c3',
        columnComment: '列3'
    },{
        id: 4,
        columnName: 'c4',
        columnComment: '列4'
    }]
}]

export const associations = [{
    slaveColumn: {
        id: 1,
        columnName: 'c1',
        columnComment: '列1'
    },
    masterColumn: {
        id: 4,
        columnName: 'c4',
        columnComment: '列4'
    },
    tableAssociationName: '1-4',
    remark: ''
}, {
    slaveColumn: {
        id: 2,
        columnName: 'c2',
        columnComment: '列2'
    },
    masterColumn: {
        id: 3,
        columnName: 'c3',
        columnComment: '列3'
    },
    tableAssociationName: '2-3',
    remark: ''
}]