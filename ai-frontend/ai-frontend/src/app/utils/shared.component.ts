import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { RouterModule, RouterOutlet } from "@angular/router";

@NgModule({
    exports : [CommonModule,FormsModule,RouterModule]
})
export class SharedModule{

}