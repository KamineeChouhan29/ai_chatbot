import { ChangeDetectorRef, Component, NgZone } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Api } from '../../services/api';
import emailjs from '@emailjs/browser';

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

    const templateParams = {
      name: this.helpRequest.name,
      email: this.helpRequest.email,
      subject: this.helpRequest.subject,
      message: this.helpRequest.message
    };

    // 1. Send actual email directly from the browser using EmailJS to ADMIN
    emailjs.send(
      'service_dszpd3e',
      'template_s48pi8l', // Admin Template ID
      templateParams,
      'IbBM3MQyY1E6v6lp_'
    )
    .catch(e => console.error('Admin EmailJS Error:', e));

    // 2. Send auto-reply directly from the browser using EmailJS to USER
    emailjs.send(
      'service_dszpd3e',
      'template_ee6s5yj', // User Template ID
      templateParams,
      'IbBM3MQyY1E6v6lp_'
    )
    .then((response) => {
      // 3. Also send to our backend so it gets saved in our MySQL Database!
      this.api.sendHelpRequest(this.helpRequest).subscribe({
        next: (res) => {
          this.zone.run(() => {
            this.loading = false;
            this.successMessage = "Thank you for your response. We will try to resolve your problem as soon as possible.";
            this.errorMessage = "";
            this.helpRequest = { name: '', email: '', subject: '', message: '' };
            form.resetForm(this.helpRequest);
            this.cdr.detectChanges();
          });
        },
        error: (err) => {
          this.zone.run(() => {
            console.error(err);
            this.loading = false;
            // We still consider it a success because the EmailJS sent the email!
            this.successMessage = "Thank you for your response. We will try to resolve your problem as soon as possible.";
            this.errorMessage = "";
            this.helpRequest = { name: '', email: '', subject: '', message: '' };
            form.resetForm(this.helpRequest);
            this.cdr.detectChanges();
          });
        }
      });
    })
    .catch((error) => {
      this.zone.run(() => {
        console.error('EmailJS Error:', error);
        this.loading = false;
        this.successMessage = '';
        this.errorMessage = "Unable to send request.";
        this.cdr.detectChanges();
      });
    });

  }

}