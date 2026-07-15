export class Message{
    content? : string;
    own : boolean;
    constructor(content : string | undefined, own:boolean){
      this.content= content;
      this.own=own;
    }
}