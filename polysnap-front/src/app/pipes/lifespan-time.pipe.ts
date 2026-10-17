import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'lifespanTime'
})
export class LifespanTimePipe implements PipeTransform {

  transform(lifespan: number): string {
    if (lifespan === -1) {
      return 'Will never expire';
    }

    if (lifespan < 60) {
      return `Will expire ${lifespan} second${lifespan > 1 ? 's' : ''} after being seen`;
    }

    if (lifespan >= 60 && lifespan < 3600) {
      const minutes = Math.floor(lifespan / 60);
      return `Will expire ${minutes} minute${minutes > 1 ? 's' : ''} after being seen`;
    }

    if (lifespan >= 3600) {
      const hours = Math.floor(lifespan / 3600);
      return `Will expire ${hours} hour${hours > 1 ? 's' : ''} after being seen`;
    }

    return '';
  }

}
