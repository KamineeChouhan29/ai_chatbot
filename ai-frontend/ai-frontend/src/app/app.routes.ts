import { Routes } from '@angular/router';
import { Chat } from './pages/chat/chat';
import { Image } from './pages/image/image';
import { Cricket } from './pages/cricket/cricket';
import { Dashboard } from './pages/dashboard/dashboard';
import { Feedback } from './pages/feedback/feedback';
import { Help } from './pages/help/help';

export const routes: Routes = [
    {
        path:'',
        redirectTo : 'dashboard',
        pathMatch : 'full'
    },
    {
        path:'dashboard',
        component : Dashboard
    },
    {
        path: 'chat',
        component : Chat
    },
    {
        path : 'image',
        component : Image
    },
    {
        path : 'cricket',
        component : Cricket
    },
    {
        path: 'feedback',
        component : Feedback
    },
    {
   path:'help',
   loadComponent:()=>import('./pages/help/help')
   .then(m=>m.Help)
}
    
];
