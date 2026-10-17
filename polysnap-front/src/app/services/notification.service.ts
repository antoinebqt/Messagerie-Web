import { Injectable } from '@angular/core';
import {MatSnackBar} from "@angular/material/snack-bar";

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  constructor(private snackBar: MatSnackBar) { }

  notifyError(message: string) {
    this.snackBar.open(message, 'Close', {
      duration: 3000, // Duration in milliseconds for which the notification is shown
      horizontalPosition: 'center', // Position of the notification horizontally ('start', 'center', 'end', 'left', 'right')
      verticalPosition: 'bottom', // Position of the notification vertically ('top', 'bottom')
      panelClass: 'error-snackbar' // Custom CSS class for the notification panel (optional)
    });
  }

  notifyInfo(userCreatedSuccessfully: string) {
    this.snackBar.open(userCreatedSuccessfully, 'Close', {
      duration: 3000, // Duration in milliseconds for which the notification is shown
      horizontalPosition: 'right', // Position of the notification horizontally ('start', 'center', 'end', 'left', 'right')
      verticalPosition: 'bottom', // Position of the notification vertically ('top', 'bottom')
      panelClass: 'info-snackbar' // Custom CSS class for the notification panel (optional)
    });
  }
}
