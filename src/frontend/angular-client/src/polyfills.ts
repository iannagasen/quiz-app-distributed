import * as process from 'process';

// window['process'] = process;

(window as any).process = {
  env: { DEBUG: undefined },
};