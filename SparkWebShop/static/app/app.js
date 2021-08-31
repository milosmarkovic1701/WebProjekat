const StartUpPage = { template: "<startup-page></startup-page>" };
const ShoppingCart = { template: "<shopping-cart></shopping-cart>" };
const AdministratorPage = { template: "<administrator-page></administrator-page>" };
const CustomerPage = { template: "<customer-page></customer-page>" };
const SelectedRestaurant = { template: "<selected-restaurant></selected-restaurant>" };

const router = new VueRouter({
	  mode: 'hash',
	  routes: [
	    { path: '/', component: StartUpPage },
	    { path: '/sc', component: ShoppingCart },
	    { path: '/administratorPage', component: AdministratorPage },
	    { path: '/customerPage', component: CustomerPage },
	    { path: '/selectedRestaurant', component: SelectedRestaurant }
	  ]
});

var app = new Vue({
	router,
	el: '#startUpPage'
});

