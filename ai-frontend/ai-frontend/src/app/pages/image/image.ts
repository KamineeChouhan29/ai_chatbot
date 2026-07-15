// import { ChangeDetectorRef, Component, NgZone } from '@angular/core';
// import { SharedModule } from '../../utils/shared.component';
// import { Api } from '../../services/api';

// @Component({
//   selector: 'app-image',
//   imports: [SharedModule],
//   templateUrl: './image.html',
//   styleUrl: './image.css',
// })
// export class Image {
//  constructor(
//     private apiService: Api,
//     private cdr: ChangeDetectorRef,
//     private zone: NgZone
//   ) {}

//   inputTextPrompt = '';
//   loadingChat = false;

//   imageUrl: string = '';

// //   askButtonClicked() {

// //     if (this.inputTextPrompt.trim() === '') {
// //       return;
// //     }

// //     this.loadingChat = true;

// //     const prompt = this.inputTextPrompt;
// //     this.inputTextPrompt = '';

// //     console.log("Prompt:", prompt);

// //     this.apiService.getImagesResponse(prompt).subscribe({

// //       next: (blob: Blob) => {

// //         console.log("Blob:", blob);

// //         this.zone.run(() => {

// //           // Purana URL release karo
// //           if (this.imageUrl) {
// //             URL.revokeObjectURL(this.imageUrl);
// //           }

// //           // Naya Blob URL banao
// //           this.imageUrl = URL.createObjectURL(blob);

// //           console.log("Image URL:", this.imageUrl);

// //           this.loadingChat = false;

// //           this.cdr.detectChanges();

// //         });

// //       },

// //       error: (err) => {

// //         console.error(err);

// //         this.zone.run(() => {

// //           this.loadingChat = false;

// //           this.cdr.detectChanges();

// //         });

// //       },

// //       complete: () => {

// //         console.log("Image Request Completed");

// //       }

// //     });

// //   }

// // }
// askButtonClicked() {

//   if (!this.inputTextPrompt.trim()) {
//     return;
//   }

//   this.loadingChat = true;

//   const prompt = this.inputTextPrompt;
//   this.inputTextPrompt = '';

//   this.apiService.getImagesResponse(prompt).subscribe({

//     next: (blob: Blob) => {

//       // Purana URL release karo
//       if (this.imageUrl) {
//         URL.revokeObjectURL(this.imageUrl);
//       }

//       // Naya image URL
//       const url = URL.createObjectURL(blob);

//       // Nayi reference assign karo
//       this.imageUrl = url;

//       this.loadingChat = false;

//       // Force state change
//       this.imageUrl = this.imageUrl + '';

//       this.cdr.markForCheck();

//     },

//     error: (err) => {

//       console.error(err);

//       this.loadingChat = false;

//       this.cdr.markForCheck();

//     }

//   });

// }
// }
import { ChangeDetectorRef, Component, NgZone } from '@angular/core';
import { SharedModule } from '../../utils/shared.component';
import { Api } from '../../services/api';

@Component({
  selector: 'app-image',
  imports: [SharedModule],
  templateUrl: './image.html',
  styleUrl: './image.css',
})
export class Image {


  constructor(
    private apiService: Api,
    private cdr: ChangeDetectorRef,
    private zone: NgZone
  ) {}


  inputTextPrompt: string = '';

  loadingChat: boolean = false;

  imageUrl: string = '';

  answer: string = '';



  askButtonClicked() {


    if(this.inputTextPrompt.trim() === '') {
      return;
    }


    this.loadingChat = true;


    const prompt = this.inputTextPrompt;


    this.inputTextPrompt = '';



    console.log("User Prompt:", prompt);



    this.apiService.getImagesResponse(prompt)
    .subscribe({


      next: (response) => {


        this.zone.run(() => {



          const contentType =
          response.headers.get('Content-Type');



          console.log(
            "Content Type:",
            contentType
          );



          this.loadingChat = false;



          // ==========================
          // IMAGE RESPONSE
          // ==========================

          if(contentType?.startsWith('image')) {



            console.log("Image Response");



            if(this.imageUrl) {

              URL.revokeObjectURL(
                this.imageUrl
              );

            }



            const blob =
            new Blob(
              [
                response.body!
              ],
              {
                type: 'image/png'
              }
            );



            this.imageUrl =
            URL.createObjectURL(blob);



            // remove old text
            this.answer = '';



          }



          // ==========================
          // TEXT RESPONSE
          // ==========================

          else {



            console.log("Text Response");



            response.body!
            .text()
            .then((text)=>{


              console.log(
                "AI Answer:",
                text
              );



              this.answer = text;



              // remove old image
              this.imageUrl = '';



              this.cdr.detectChanges();


            });


          }



          this.cdr.detectChanges();


        });


      },



      error:(error)=>{


        console.error(
          "API Error:",
          error
        );



        this.loadingChat = false;



        this.cdr.detectChanges();


      }



    });


  }



}