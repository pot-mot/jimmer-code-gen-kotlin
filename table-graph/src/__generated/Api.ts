import type { Executor } from './';

import { GenTableController } from './services';

export class Api {
    
    readonly genTableController: GenTableController;
    
    constructor(executor: Executor) {
        this.genTableController = new GenTableController(executor);
    }
}