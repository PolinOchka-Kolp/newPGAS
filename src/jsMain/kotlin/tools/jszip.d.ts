declare module 'jszip' {
  export default class JSZip {
    file(name: string, data: any, options?: object): JSZip;
    generateAsync(options?: object): Promise<any>;
  }
}