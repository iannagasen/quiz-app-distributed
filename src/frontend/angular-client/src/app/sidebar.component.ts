import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [CommonModule],
  template: `
<div [ngClass]="{'-translate-x-full': !isSidebarOpen, 'translate-x-0': isSidebarOpen}"
     class="fixed inset-y-0 left-0 w-96 opacity-30 bg-gradient-to-br from-clr-secondary to-clr-background transition-transform duration-300 ease-in-out">
     <!-- Sidebar content... -->
</div>

<button (click)="toggleSidebar()"
        [ngClass]="{'block': !isSidebarOpen, 'hidden': isSidebarOpen}"
        class="fixed top-1/2 left-0 transform -translate-y-1/2 bg-transparent p-2 rounded-r-md shadow-md focus:outline-none">
  <div class="h-4 w-1 bg-gray-300 transition"></div>
</button>

<button (click)="toggleSidebar()"
        [ngClass]="{'block': isSidebarOpen, 'hidden': !isSidebarOpen}"
        class="fixed top-1/2 left-96 transform -translate-y-1/2 bg-transparent p-2 rounded-l-md shadow-md focus:outline-none">
  <div class="h-4 w-1 bg-gray-300 transition"></div>
</button>
`
})
export class SidebarComponent {
  isSidebarOpen = false;

  toggleSidebar() {
    this.isSidebarOpen = !this.isSidebarOpen;
  }
}
