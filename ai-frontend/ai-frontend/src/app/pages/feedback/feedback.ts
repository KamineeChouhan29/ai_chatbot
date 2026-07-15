

import {
  Component,
  OnInit,
  ChangeDetectorRef
} from '@angular/core';

import { SharedModule } from '../../utils/shared.component';
import { Api } from '../../services/api';

@Component({
  selector: 'app-feedback',
  standalone: true,
  imports: [SharedModule],
  templateUrl: './feedback.html',
  styleUrl: './feedback.css'
})
export class Feedback implements OnInit {

  questions: any[] = [];

  // QuestionId -> Rating
  ratings: { [key: number]: number } = {};

  loading = false;


  constructor(
    private api: Api,
    private cdr: ChangeDetectorRef
  ) {}


  ngOnInit(): void {
    this.loadQuestions();
  }


  loadQuestions() {

    this.api.getFeedbackQuestions().subscribe({

      next: (data: any) => {

        console.log("Questions:", data);

        this.questions = data;

        this.cdr.detectChanges();

      },

      error: (err: any) => {

        console.log(err);

      }

    });

  }



  rate(questionId: number, star: number) {

    this.ratings[questionId] = star;

  }



  submitFeedback() {


    if(this.loading){
      return;
    }


    this.loading = true;


    const feedbackData = this.questions.map(q => ({

      questionId: q.id,

      rating: this.ratings[q.id] || 0

    }));


    let completed = 0;



    feedbackData.forEach(item => {


      this.api.submitRating(
        item.questionId,
        item.rating
      )
      .subscribe({


        next: () => {


          completed++;



          if(completed === feedbackData.length){


            this.loading = false;



            // ⭐ Only reset ratings
            this.ratings = {};



            this.cdr.detectChanges();



            alert(
              "Feedback submitted successfully!"
            );


          }


        },



        error:(err)=>{


          console.log(err);


          this.loading = false;


          alert(
            "Feedback submission failed!"
          );


        }


      });


    });


  }


}