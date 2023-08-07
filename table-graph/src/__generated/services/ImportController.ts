import type { Executor } from '../';
import type { GenTableDto } from '../model/dto';
import type { Page, PageQuery } from '../model/static';

export class ImportController {
    
    constructor(private executor: Executor) {}
    
    async listGenTable(options: ImportControllerOptions['listGenTable']): Promise<
        Page<GenTableDto['DEFAULT']>
    > {
        let _uri = '/genTable/list';
        let _separator = _uri.indexOf('?') === -1 ? '?' : '&';
        let _value: any = undefined;
        _value = options.page.number;
        if (_value !== undefined && _value !== null) {
            _uri += _separator
            _uri += 'number='
            _uri += encodeURIComponent(_value);
            _separator = '&';
        }
        _value = options.page.size;
        if (_value !== undefined && _value !== null) {
            _uri += _separator
            _uri += 'size='
            _uri += encodeURIComponent(_value);
            _separator = '&';
        }
        return (await this.executor({uri: _uri, method: 'GET'})) as Page<GenTableDto['DEFAULT']>
    }
    
    async preview(options: ImportControllerOptions['preview']): Promise<
        ReadonlyArray<GenTableDto['DEFAULT']>
    > {
        let _uri = '/previewTables';
        let _separator = _uri.indexOf('?') === -1 ? '?' : '&';
        let _value: any = undefined;
        _value = options.tablePattern;
        if (_value !== undefined && _value !== null) {
            _uri += _separator
            _uri += 'tablePattern='
            _uri += encodeURIComponent(_value);
            _separator = '&';
        }
        return (await this.executor({uri: _uri, method: 'GET'})) as ReadonlyArray<GenTableDto['DEFAULT']>
    }
}

export type ImportControllerOptions = {
    'listGenTable': {readonly page: PageQuery},
    'preview': {readonly tablePattern?: string}
}