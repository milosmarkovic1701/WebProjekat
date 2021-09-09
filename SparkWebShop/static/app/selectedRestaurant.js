Vue.component("selected-restaurant", {
	data: function () {
		    return {
				foodItems : null,
				comments : null,
				restaurant : "",
				restaurantId: "",
				customer: {},
			    customerId: "",
			    foodItemId: "",
				cart: {},
				cartInfo: {customerId: "", foodItemId: "", amount: 1},
				newOrder: {customerId: "", cart: {}},
				restaurantLocation : "",
				restaurantCoordinates : {lat: 0, lng: 0},
		    }
	},
	template:`
  <div>
  
    <div class="modal fade" id="cart" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
    <div class="modal-dialog modal-fullscreen">
      <div class="modal-content">
        <div class="modal-header">
          <h2 class="modal-title" style="color: #dc3545;" id="exampleModalLongTitle">Korpa</h2>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
        <div class="container-fluid my-container justify-content-between" style="margin-top: 2mm;">
        <div class="card mb-3" style="width: 100%;">
            <div class="row g-0">
                <div class="col-sm-3">
                  <button type="button" style="margin-top: 10mm;" class="btn btn-lg btn-secondary" data-bs-dismiss="modal">Artikli restorana</button>
                </div>
                <div class="col">
                    <div>
                        <img src="images/basket.png" alt="mdo" style="margin-top: 3mm" width="100" height="100" >
                    </div>
                </div>
                <div class="col-sm-3" style="font-size: large;">
                    <ul class="list-group list-group-flush">
                      <li class="list-group-item">Cena: {{cart.price}} din.</li>
                      <li v-if="customer.type === 'BRONZE'" class="list-group-item">Popust: 0%</li>
                      <li v-if="customer.type === 'SILVER'" class="list-group-item">Popust: 3%</li>
                      <li v-if="customer.type === 'GOLD'" class="list-group-item">Popust: 5%</li>
                      <li class="list-group-item">Cena sa popustom: {{cart.discountPrice}} din.</li>
                    </ul>
                </div>
                <div class="col-sm-3">
                    <button type="button" style="margin-top: 10mm;" v-on:click="sendOrder(customer.user.id)"  class="btn btn-lg btn-danger">Poruči hranu</button>
                </div>
            </div>
        </div>
    </div>
    <div class="container-fluid my-container justify-content-between" style="margin-top: 2mm;">
        <div class="row row-cols-1 row-cols-md-4 g-4">
          <div class="col" v-for="fi in cart.items">
            <div class="card" style="width: 93%">
              <img v-bind:src="fi.photo" width="300" height="300" class="card-img-top" alt="...">
              <div class="card-body">
                <h5 class="card-title">{{fi.name}}</h5>
              </div>
              <ul class="list-group list-group-flush">
                <li class="list-group-item">{{fi.description}}</li>
                <li class="list-group-item">{{fi.size}}</li>
                <li class="list-group-item">{{fi.price}} din.</li>
              </ul>
              <div class="card-body">
                <div class="container-fluid my-container justify-content-around">
                    <input type="number" id="quantity" v-on:change="updateCart(fi.id, fi.amount)" v-model="fi.amount" name="quantity" min="1" max="10" step="1" style="background-color: #eae7dc; border-radius: 10%; border-color: #dc3545;">
                    <button style="margin-left: 28mm;" v-on:click="removeCartItem(fi.id)" type="button" class="btn btn-danger">Obriši</button>
                </div>
            </div>
          </div>
          </div>
        </div>
      </div>
      </div>
    </div>
  </div>
</div>

<div class="container-fluid my-container justify-content-between" style="margin-top: 1%;">
          <div class="card mb-3" style="width: 100%;">
            <div class="row g-0">
              <div class="col-md-4">
                <img v-bind:src="restaurant.logo" class="img-fluid rounded-start" width="400" height="550">
              </div>
                <div class="col-md-8">
                  <div class="card-body">
                  </div>
                  <div class="row">
                    <div class="col">
                      <ul class="list-group list-group-flush">
                      <li class="list-group-item"><h4 class="card-title" style="color: #dc3545; margin-bottom: 15mm;">{{this.restaurant.name}}</h4></li>
                        <li class="list-group-item">Tip restorana: {{this.restaurant.type}}</li>
                        <li class="list-group-item">Adresa: {{this.restaurantLocation}}</li>
                        <li v-if="restaurant.status === 'OPENED'" class="list-group-item"> Status: OTVORENO</li>
                    <li v-else class="list-group-item">Status: ZATVORENO</li>
                        <li class="list-group-item">Prosečna ocena: {{this.restaurant.rating}}/5</li>
                      </ul>
                    </div>
                    <div class="col">
                    <div id="dvMap" style="width: 500px; height: 350px"></div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
              </div>
              <div class="row">
                <div class="col-sm-4">
                  <h2 style="color: #dc3545; margin-top: 10mm">Svi artikli:</h2>
                </div>
                <div class="col-sm-4">
                    <ul class="list-group list-group-flush rounded" style="border-color: #dc3545; border-style: groove; border-width: thin;">
                            <li class="list-group-item">Cena: {{cart.price}} din.</li>
                            <li v-if="customer.type === 'BRONZE'" class="list-group-item">Popust: 0%</li>
                      		<li v-if="customer.type === 'SILVER'" class="list-group-item">Popust: 3%</li>
                      		<li v-if="customer.type === 'GOLD'" class="list-group-item">Popust: 5%</li>
                            <li class="list-group-item">Cena sa popustom: {{cart.discountPrice}} din.</li>
                          </ul>
                </div>
                <div class="col-sm-4">
                  <button v-if="restaurant.status === 'OPENED'" type="button" style="margin-top: 10mm;" class="btn btn-danger btn-lg" data-bs-toggle="modal" data-bs-target="#cart">
                    Korpa
                  </button>
                  <button v-else type="button" disabled style="margin-top: 10mm;" class="btn btn-danger btn-lg" data-bs-toggle="modal" data-bs-target="#cart">
                    Restoran je zatvoren.
                  </button>
                </div>
              </div>
            <div class="container-fluid my-container justify-content-between" style="margin-top: 5mm;">
              <div class="row row-cols-1 row-cols-md-4 g-4">
                <div class="col" v-for="fi in foodItems">
                  <div class="card" style="width: 93%">
                    <img v-bind:src="fi.photo" width="300" height="300" class="card-img-top" alt="...">
                    <div class="card-body">
                      <h5 class="card-title">{{fi.name}}</h5>
                    </div>
                    <ul class="list-group list-group-flush">
                      <li class="list-group-item">{{fi.description}}</li>
                      <li class="list-group-item">{{fi.size}}</li>
                      <li class="list-group-item">{{fi.price}} din.</li>
                    </ul>
                    <div class="card-body">
                      <div class="container-fluid my-container justify-content-around">
                          <input type="number" id="quantity" v-model="fi.amount" name="quantity" min="1" max="10" step="1" style="background-color: #eae7dc; border-radius: 10%; border-color: #dc3545;">
                          <button v-if="restaurant.status === 'OPENED'" style="margin-left: 13mm;" type="button" v-on:click="addToCart(fi.id, fi.amount)" class="btn btn-danger">Dodaj u korpu</button>
                          <button v-else style="margin-left: 13mm;" type="button" v-on:click="addToCart(fi.id, fi.amount)" disabled class="btn btn-danger">Dodaj u korpu</button>
                      </div>
                  </div>
                </div>
                </div>
              </div>
            </div>
            <div class="container-fluid" style="margin-top: 15mm; margin-bottom: 15mm;">
              <h2 style="color: #dc3545;">{{restaurant.name}} - Komentari i iskustva mušterija:</h2>
            </div>
            <div class="container-fluid">
              <table class="table">
                <thead>
                  <tr>
                    <th scope="col">Ime</th>
                    <th scope="col">Prezime</th>
                    <th scope="col">Porudžbina</th>
                    <th scope="col">Sadržaj komentara</th>
                    <th scope="col">Ocena</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="comment in comments">
                    <td>{{comment.name}}</td>
                    <td>{{comment.lastname}}</td>
                    <td>{{comment.orderInfo}}</td>
                    <td>{{comment.content}}</td>
                    <td>{{comment.rating}}/5</td>
                  </tr>
                </tbody>
              </table>
            </div>
      </div> 
`
	, 
	methods : {
		getFoodItems(){
            axios
	            .get("/rest/restaurants/getFoodItems/" + this.$route.query.id)
	            .then(response => (this.foodItems = response.data))
        },
        getRestaurantComments(){
            axios
	            .get("/rest/comments/getRestaurantComments/" + this.$route.query.id)
	            .then(response => {this.comments = response.data
	            })
        },
        getCartItems() {
        	this.customerId = JSON.parse(localStorage.getItem("customer")).user.id;
        	axios
            	.get("/rest/cart/getCustomerCart/" + this.customerId)
	            .then(response => (this.cart = response.data))
        },
        initMap() {
        var mapOptions = {
                center: new google.maps.LatLng(45.2481976, 19.8274375),
                zoom: 13,
                mapTypeId: google.maps.MapTypeId.SATELITE
            };
            var infoWindow = new google.maps.InfoWindow();
            var latlngbounds = new google.maps.LatLngBounds();
            var latitude = this.restaurantCoordinates;
            var map = new google.maps.Map(document.getElementById("dvMap"), mapOptions);
            new google.maps.Marker({
			    position : new google.maps.LatLng(this.restaurantCoordinates),
			    map,
			});
            google.maps.event.addListener(map, 'click', function (e) {
            });
        },
        getRestaurant(){
			axios
            	.get("/rest/restaurants/getSelectedRestaurant/" + this.$route.query.id)
	            .then(response => {
	            this.restaurant = response.data;
	            this.restaurantLocation = this.restaurant.location.street + " " + this.restaurant.location.number + ", " + this.restaurant.location.city;
	            this.restaurantCoordinates.lat = this.restaurant.location.width;
	            this.restaurantCoordinates.lng = this.restaurant.location.length;
        		this.initMap();
        		this.getRestaurantComments();
	            })
	        this.getCartItems();
        },
        getCustomer() {
        	this.customer = JSON.parse(localStorage.getItem("customer"));
        },
        addToCart(id, amount) {
	         if (amount < 1) {
		        	alert("Greška: Količina mora biti pozitivan broj!");
		     } 
		     else { 
	        	this.cartInfo.customerId = JSON.parse(localStorage.getItem("customer")).user.id;
	        	this.cartInfo.foodItemId = id;
	        	this.cartInfo.amount = amount;
	        	axios
	        		.post('rest/cart/addToCart', this.cartInfo)
		          	.then(response => (this.cart = response.data))   
	        }
        },
        removeCartItem(id) {
        	this.cartInfo.customerId = JSON.parse(localStorage.getItem("customer")).user.id;
        	this.cartInfo.foodItemId = id;
        	axios
        		.post('rest/cart/removeCartItem', this.cartInfo)
	          	.then(response => (this.cart = response.data))  
        },
        updateCart(id, amount) {
	        if (amount < 1) {
	        	alert("Greška: Količina mora biti pozitivan broj!");
	        } 
	        else {
	        	this.cartInfo.customerId = JSON.parse(localStorage.getItem("customer")).user.id;
	        	this.cartInfo.foodItemId = id;
	        	this.cartInfo.amount = amount;
	        	axios
	        		.post('rest/cart/updateCart', this.cartInfo)
		          	.then(response => (this.cart = response.data))   
		    }
        },
        sendOrder(id) {
        	if (this.cart.price < 1) {
	        	alert("Greška: Korpa je prazna. Dodajte hranu ako želite da poručite.");
	        } 
	        else {
	        	this.newOrder.customerId = id;
	        	this.newOrder.cart = this.cart;
	        	axios
	        		.post('rest/orders/sendOrder', this.newOrder)
		          	.then(response => {
		          	alert("Obaveštenje: Vaša porudžbina je poslata.");
		          	this.customer.points = this.customer.points + (this.newOrder.cart.price * 133/1000);
		          	if (this.customer.points >= 3000) {
		          		this.customer.type = 'SILVER';
		          	}
		          	else if (this.customer.points >= 4000) {
		          		this.customer.type = 'GOLD';
		          	}
		          	this.cart = {};
					this.cartInfo.customerId = ""; 
					this.foodItemId = "";
					this.amount = 1;
					this.newOrder.customerId = ""; 
					this.newOrder.cart = {};
		          	localStorage.setItem("customer", JSON.stringify(this.customer));
		          	this.$router.push('/customerPage'); 
	        		this.$router.go();
		          	})   
		    }
        },
	},
	mounted () {
		this.getRestaurant();
		this.getCustomer();
        this.getFoodItems();
    },

});