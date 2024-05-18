import { Routes } from '@angular/router';
import { HotelListComponent } from './components/hotel-list/hotel-list.component';
import { HotelDetailComponent } from './components/hotel-detail/hotel-detail.component';
import { FeedbackComponent } from './components/feedback/feedback.component';
import { FeedbackFormComponent } from './components/feedback-form/feedback-form.component';
import { NearbyHotelsComponent } from './components/nearby-hotels/nearby-hotels.component';

export const routes: Routes = [
  { path: '', redirectTo: '/hotels', pathMatch: 'full' },
  { path: 'hotels', component: HotelListComponent },
  { path: 'hotels/:id', component: HotelDetailComponent },
  { path: 'feedbacks/:hotelId', component: FeedbackComponent },
  { path: 'hotels/:hotelId/feedback', component: FeedbackFormComponent },
  { path: 'nearby-hotels', component: NearbyHotelsComponent },
];
