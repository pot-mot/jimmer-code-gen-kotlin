import type { Executor } from '../';
import type { GenTableDto } from '../model/dto';
import type { ColumnQuery, GenColumnCommonView, GenTableColumnsInput, GenTableColumnsView, Iterable, Optional, TableQuery } from '../model/static';

export class TableController {
    
    constructor(private executor: Executor) {}
    
    async delete(options: TableControllerOptions['delete']): Promise<number> {
        let _uri = '/table/';
        return (await this.executor({uri: _uri, method: 'DELETE', body: options.body})) as number
    }
    
    async import(options: TableControllerOptions['import']): Promise<
        ReadonlyArray<Optional<GenTableColumnsView>>
    > {
        let _uri = '/table/import';
        let _separator = _uri.indexOf('?') === -1 ? '?' : '&';
        let _value: any = undefined;
        _value = options.groupId;
        if (_value !== undefined && _value !== null) {
            _uri += _separator
            _uri += 'groupId='
            _uri += encodeURIComponent(_value);
            _separator = '&';
        }
        return (await this.executor({uri: _uri, method: 'POST', body: options.body})) as ReadonlyArray<Optional<GenTableColumnsView>>
    }
    
    async importAll(options: TableControllerOptions['importAll']): Promise<
        ReadonlyArray<GenTableColumnsView>
    > {
        let _uri = '/table/importAll';
        let _separator = _uri.indexOf('?') === -1 ? '?' : '&';
        let _value: any = undefined;
        _value = options.groupId;
        if (_value !== undefined && _value !== null) {
            _uri += _separator
            _uri += 'groupId='
            _uri += encodeURIComponent(_value);
            _separator = '&';
        }
        return (await this.executor({uri: _uri, method: 'POST'})) as ReadonlyArray<GenTableColumnsView>
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
    
    async refresh(options: TableControllerOptions['refresh']): Promise<
        ReadonlyArray<Optional<GenTableColumnsView>>
    > {
        let _uri = '/table/refresh';
        let _separator = _uri.indexOf('?') === -1 ? '?' : '&';
        let _value: any = undefined;
        _value = options.groupId;
        if (_value !== undefined && _value !== null) {
            _uri += _separator
            _uri += 'groupId='
            _uri += encodeURIComponent(_value);
            _separator = '&';
        }
        return (await this.executor({uri: _uri, method: 'PUT', body: options.body})) as ReadonlyArray<Optional<GenTableColumnsView>>
    }
    
    async refreshAll(options: TableControllerOptions['refreshAll']): Promise<
        ReadonlyArray<GenTableColumnsView>
    > {
        let _uri = '/table/refreshAll';
        let _separator = _uri.indexOf('?') === -1 ? '?' : '&';
        let _value: any = undefined;
        _value = options.groupId;
        if (_value !== undefined && _value !== null) {
            _uri += _separator
            _uri += 'groupId='
            _uri += encodeURIComponent(_value);
            _separator = '&';
        }
        return (await this.executor({uri: _uri, method: 'PUT'})) as ReadonlyArray<GenTableColumnsView>
    }
    
    async view(options: TableControllerOptions['view']): Promise<
        ReadonlyArray<GenTableDto['DEFAULT']>
    > {
        let _uri = '/table/view';
        let _separator = _uri.indexOf('?') === -1 ? '?' : '&';
        let _value: any = undefined;
        _value = options.namePattern;
        if (_value !== undefined && _value !== null) {
            _uri += _separator
            _uri += 'namePattern='
            _uri += encodeURIComponent(_value);
            _separator = '&';
        }
        return (await this.executor({uri: _uri, method: 'GET'})) as ReadonlyArray<GenTableDto['DEFAULT']>
    }
}

export type TableControllerOptions = {
    'delete': {readonly body: Iterable<number>},
    'import': {readonly body: ReadonlyArray<GenTableColumnsInput>, readonly groupId?: number},
    'importAll': {readonly groupId?: number},
    'move': {readonly body: ReadonlyArray<number>, readonly groupId: number},
    'query': {readonly body: TableQuery},
    'queryColumns': {readonly body: ColumnQuery},
    'refresh': {readonly body: ReadonlyArray<GenTableColumnsInput>, readonly groupId?: number},
    'refreshAll': {readonly groupId?: number},
    'view': {readonly namePattern?: string}
}