import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-quiz-manager-header',
  standalone: true,
  imports: [
    CommonModule
  ],
  template: `
    <div class="flex justify-between mb-4">
      <div class='flex-grow text-4xl font-extrabold orange_gradient'>
        Topic
      </div>
      <div class='flex-shrink text-2xl font-extrabold'>
        {{topic}}
      </div>
    </div>
  `
    
})
export class QuizManagerHeaderComponent {

  @Input({ required: true })
  topic!: string;

}
