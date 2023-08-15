import type { Executor } from '../';
import type { ColumnQuery, GenColumnCommonView, GenTableColumnsView, TableQuery } from '../model/static';

export class TableController {
    
    constructor(private executor: Executor) {}
    
    async delete(options: TableControllerOptions['delete']): Promise<number> {
        let _uri = '/table/';
        return (await this.executor({uri: _uri, method: 'DELETE', body: options.body})) as number
    }
    
    async move(options: TableControllerOptions['move']): Promise<number> {
        let _uri = '/table/move';
        let _separator = _uri.indexOf('?') === -1 ? '?' : '&';
        let _value: any = undefined;
        _value = options.groupId;
        if (_value !== undefined && _value !== null) {
            _uri += _separator
            _uri += 'groupId='
            _uri += encodeURIComponent(_value);
            _separator = '&';
        }
        return (await this.executor({uri: _uri, method: 'PUT', body: options.body})) as number
    }
    
    async query(options: TableControllerOptions['query']): Promise<
        ReadonlyArray<GenTableColumnsView>
    > {
        let _uri = '/table/query';
        return (await this.executor({uri: _uri, method: 'GET', body: options.body})) as ReadonlyArray<GenTableColumnsView>
    }
    
    async queryColumns(options: TableControllerOptions['queryColumns']): Promise<
        ReadonlyArray<GenColumnCommonView>
    > {
        let _uri = '/table/column/query';
        return (await this.executor({uri: _uri, method: 'GET', body: options.body})) as ReadonlyArray<GenColumnCommonView>
    }
}

export type TableControllerOptions = {
    'delete': {readonly body: ReadonlyArray<number>},
    'move': {readonly body: ReadonlyArray<number>, readonly groupId: number},
    'query': {readonly body: TableQuery},
    'queryColumns': {readonly body: ColumnQuery}
}