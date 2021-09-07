const StartUpPage = { template: "<startup-page></startup-page>" };
const AdministratorPage = { template: "<administrator-page></administrator-page>" };
const CustomerPage = { template: "<customer-page></customer-page>" };
const SelectedRestaurant = { template: "<selected-restaurant></selected-restaurant>" };
const CommentPage = { template: "<comment-page></comment-page>" };
const ManagerPage = {template: "<manager-page></manager-page>"};

const router = new VueRouter({
	  mode: 'hash',
	  routes: [
	    { path: '/', component: StartUpPage },
	    { path: '/administratorPage', component: AdministratorPage },
	    { path: '/customerPage', component: CustomerPage },
	    { path: '/selectedRestaurant', component: SelectedRestaurant },
	    { path: '/commentPage', component: CommentPage },
	    { path: '/managerPage', component: ManagerPage },
	  ]
});

var app = new Vue({
	router,
	el: '#startUpPage'
});

