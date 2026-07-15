import { ChangeDetectorRef, Component, NgZone } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Api } from '../../services/api';

@Component({
  selector: 'app-help',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './help.html'
})
export class Help {

  helpRequest = {
    name: '',
    email: '',
    subject: '',
    message: ''
  };

  loading = false;
  successMessage = '';
  errorMessage = '';

  constructor(
    private api: Api,
    private zone: NgZone,
    private cdr: ChangeDetectorRef
  ) {}

  sendRequest(form: NgForm) {

    if (form.invalid) {
      return;
    }

    this.loading = true;
    this.successMessage = '';
    this.errorMessage = '';

    this.api.sendHelpRequest(this.helpRequest).subscribe({

      next: (res) => {

        this.zone.run(() => {

          this.loading = false;

          this.successMessage =
            "Thank you for your response. We will try to resolve your problem as soon as possible.";

          this.errorMessage = "";

          // Reset model
          this.helpRequest = {
            name: '',
            email: '',
            subject: '',
            message: ''
          };

          // Reset form
          form.resetForm(this.helpRequest);

          this.cdr.detectChanges();

        });

      },

      error: (err) => {

        this.zone.run(() => {

          console.error(err);

          this.loading = false;

          this.successMessage = '';

          this.errorMessage = "Unable to send request.";

          this.cdr.detectChanges();

        });

      }

    });

  }

}