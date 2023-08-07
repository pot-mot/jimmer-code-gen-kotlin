import type { Executor } from './';

import { ImportController } from './services';

export class Api {
    
    readonly importController: ImportController;
    
    constructor(executor: Executor) {
        this.importController = new ImportController(executor);
    }
}