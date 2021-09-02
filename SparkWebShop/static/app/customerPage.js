Vue.component("customer-page", {
	data: function () {
		    return {
				restaurantSearchQuery : {name: "", type: "", location: "", rating: "", filterType: "", filterStatus: "", sort: ""},
				orderSearchQuery : {restaurantName: "", priceDown: "", priceUp: "", dateDown: "", dateUp: "", filterType: "", filterStatus: "", sort: "" },
				restaurants : null,
				restaurantComments : null,
				restaurantId : "",
				user : "",
				customerType: "",
				customerPoints: "",
		    	orders: "",
		    	notDeliveredOrders: "",
		    	notRatedOrders: "",
		    	customer: {},
		    	userInfo : {id: "", name: "", lastname: "", username: "", password: "", birthDate: "", address: ""},
		    }
	},
	
template:`
  <div>
  <div id="tabs" class="d-flex flex-wrap align-items-center justify-content-center justify-content-sm-start">
        <img id="logo2" src="images/ponesilogo.png" alt="mdo" width="120" height="38" >

      <ul class="nav col-12 col-sm-auto me-sm-auto justify-content-center mb-md-0">
        <ul class="nav nav-tabs" id="myTab" role="tablist">
          <li class="nav-item" role="presentation">
            <button class="nav-link active" id="restaurants-tab" data-bs-toggle="tab" data-bs-target="#restaurants" type="button" role="tab" aria-controls="restaurant" aria-selected="true">Svi restorani</button>
          </li>
          <li class="nav-item" role="presentation">
            <button class="nav-link" id="orders-tab" data-bs-toggle="tab" data-bs-target="#orders" type="button" role="tab" aria-controls="orders" aria-selected="false">Sve porudžbine
          	<span class="badge">{{this.orders.length}}</span>
          	</button>
          </li>
          <li class="nav-item" role="presentation">
            <button class="nav-link" id="notdeliveredorders-tab" data-bs-toggle="tab" data-bs-target="#notdeliveredorders" type="button" role="tab" aria-controls="notdeliveredorders" aria-selected="false">Nedostavljene porudžbine
              <span class="badge">{{this.notDeliveredOrders.length}}</span>
            </button>
          </li>
          <li class="nav-item" role="presentation">
            <button class="nav-link" id="notgradedorders-tab" data-bs-toggle="tab" data-bs-target="#notgradedorders" type="button" role="tab" aria-controls="notgradedorders" aria-selected="false">Neocenjene porudžbine
              <span class="badge">{{this.notRatedOrders.length}}</span>
            </button>
          </li>
          <li class="nav-item" role="presentation" style="margin-left: 100mm;"> <!--125mm-->
            <button class="nav-link" id="myinfo-tab" data-bs-toggle="tab" data-bs-target="#myinfo" type="button" role="tab" aria-controls="myinfo" aria-selected="false">Moji podaci
              <img src="icons/user.png" alt="mdo" width="24" height="24" class="rounded-circle">
              <img v-bind:src="this.customerType" alt="mdo" width="24" height="24" class="rounded-circle">
              <span class="badge">{{this.customer.points}}</span>
            </button>
          </li>
          <li style="margin-left: 10mm;">
            <button type="button" style="margin-top: 2%;" class="btn btn-secondary" data-bs-toggle="modal" data-bs-target="#logOut">
              Odjavi se
            </button>
          </li>
        </ul>
      </ul>
    </div>

<!--MODALNI PROZOR ZA ODJAVU-->
<div class="modal fade" id="logOut" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLongTitle">Odjava</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        Da li ste sigurni da želite da se odjavite ?
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Otkaži</button>
        <button type="button" v-on:click="logOut()" class="btn btn-danger">Potvrdi</button>
      </div>
    </div>
  </div>
</div>

  <div class="tab-content" id="myTabContent">
    <div class="tab-pane fade show active" id="restaurants" role="tabpanel" aria-labelledby="restaurants-tab">
      <div class="surface">
		  <div class="container-fluid">
		  	  <div class="row">
			      <div class="col col-sm-1" style="border-left: #dc3545; border-left-style: groove">
			        <input type="text" v-model="restaurantSearchQuery.name" class="form-control" placeholder="Naziv">
			      </div>
			      <div class="col col-sm-1">
			        <input type="text" v-model="restaurantSearchQuery.type" class="form-control" placeholder="Tip">
			      </div>
			      <div class="col col-sm-1">
			        <input type="text" v-model="restaurantSearchQuery.location" class="form-control" placeholder="Lokacija">
			      </div>
			      <div class="col col-sm-1">
			        <input type="number" min="0" max="5" step="0.1" v-model="restaurantSearchQuery.rating" class="form-control" placeholder="Ocena">
			      </div>
			      <div class="col col-sm-2">
			        <select class="form-select" v-model="restaurantSearchQuery.filterType" id="inputGroupSelect04">
			          <option value="" selected>Filter (tip)</option>
			          <option value="razno">Razno</option>
			          <option value="roštilj">Roštilj</option>
			          <option value="palačinke">Palačinke</option>
			          <option value="krofne">Krofne</option>
			          <option value="pekara">Pekara</option>
			          <option value="poslastičarnica">Poslastičarnica</option>
			          <option value="picerija">Picerija</option>
			          <option value="italijanski">Italijanski</option>
			          <option value="meksički">Meksički</option>
			          <option value="kineski">Kineski</option>
			          <option value="francuski">Francuski</option>
			          <option value="japanski">Japanski</option>
			          <option value="indijski">Indijski</option>
			          <option value="turski">Turski</option>
			          <option value="grčki">Grčki</option>
			        </select>
			      </div>
			      <div class="col col-sm-2">
			        <select class="form-select" v-model="restaurantSearchQuery.filterStatus" id="inputGroupSelect04">
			          <option value="" selected>Filter (status)</option>
			          <option value="opened">Otvoren</option>
			          <option value="closed">Zatvoren</option>
			        </select>
			      </div>
			      <div class="col col-sm-3">
			        <select class="form-select" v-model="restaurantSearchQuery.sort" id="inputGroupSelect04">
			          <option value="" selected>Tip sortiranja</option>
			          <option value="naziv_rastuce">Naziv (rastuće)</option>
			          <option value="naziv_opadajuce">Naziv (opadajuće)</option>
			          <option value="lokacija_rastuce">Lokacija (rastuće)</option>
			          <option value="lokacija_opadajuce">Lokacija (opadajuće)</option>
			          <option value="ocena_rastuce">Prosečna ocena (rastuće)</option>
			          <option value="ocena_opadajuce">Prosečna ocena (opadajuće)</option>
			        </select>
			      </div>
			      <div class="col col-sm-1" style="border-right: #dc3545; border-right-style: groove">
			        <button type="button" v-on:click="searchRestaurant()" class="btn btn-danger">Pretraži</button>
			      </div>
			    <div class="row"></div>
			  </div>
		  </div>
	  </div>
	  
      <div class="container-fluid" style="margin-top: 5mm; margin-left: 5mm;">
	    <div class="row row-cols-1 row-cols-md-4 g-4">
	      <div class="col" v-for="restaurant in restaurants">
	        <div class="card" style="width: 21rem;">
	          <img v-bind:src="restaurant.logo" width="300" height="300" class="card-img-top" alt="...">
	          <div class="card-body">
	            <h5 class="card-title">{{restaurant.name}}</h5>
	          </div>
	          <ul class="list-group list-group-flush">
	            <li class="list-group-item">{{restaurant.location.street}} {{restaurant.location.number}}, {{restaurant.location.city}}</li>
	            <li class="list-group-item">{{restaurant.type}}</li>
	            <li v-if="restaurant.status === 'OPENED'" class="list-group-item">OTVORENO</li>
            	<li v-else class="list-group-item">ZATVORENO</li>
            	<li class="list-group-item">{{restaurant.rating}}/5</li>
	          </ul>
	          <div class="card-body">
				<button type="button" id="showArticles" v-on:click="getRestaurantFoodItems(restaurant.id)" class="btn btn-danger">Prikaži artikle</button>
	          </div>
	        </div>
	      </div>
	    </div>
	  </div>
	</div>

    <div class="tab-pane fade" id="orders" role="tabpanel" aria-labelledby="orders-tab">
      <div class="surface">
		  <div class="container-fluid">
		  	  <div class="row">
			      <div class="col col-sm-1" style="border-left: #dc3545; border-left-style: groove">
			        <input type="text" v-model="orderSearchQuery.restaurantName" class="form-control" placeholder="Restoran">
			      </div>
			      <div class="col col-sm-1">
			        <input type="number" min="0" max="10000" step="100" v-model="orderSearchQuery.priceDown" class="form-control" placeholder="Cena-od">
			      </div>
			      <div class="col col-sm-1">
			        <input type="number" min="100" max="10000" step="100"v-model="orderSearchQuery.priceUp" class="form-control" placeholder="Cena-do">
			      </div>
			      <div class="col col-sm-2">
			        <input type="date" v-model="orderSearchQuery.dateDown" class="form-control" placeholder="Datum-od">
			      	<span class="input-group-text" id="basic-addon1">Datum-od</span>
			      </div>
			      <div class="col col-sm-2">
			        <input type="date" v-model="orderSearchQuery.dateUp" class="form-control" placeholder="Datum-do">
			      	<span class="input-group-text" id="basic-addon1">Datum-do</span>
			      </div>
			      <div class="col col-sm-1">
			        <select class="form-select" v-model="orderSearchQuery.filterType" id="inputGroupSelect04">
			          <option value="" selected>Filter (tip)</option>
			          <option value="roštilj">Roštilj</option>
			          <option value="palačinke">Palačinke</option>
			          <option value="krofne">Krofne</option>
			          <option value="pekara">Pekara</option>
			          <option value="poslastičarnica">Poslastičarnica</option>
			          <option value="picerija">Picerija</option>
			          <option value="italijanski">Italijanski</option>
			          <option value="meksički">Meksički</option>
			          <option value="kineski">Kineski</option>
			          <option value="francuski">Francuski</option>
			          <option value="japanski">Japanski</option>
			          <option value="indijski">Indijski</option>
			          <option value="turski">Turski</option>
			          <option value="grčki">Grčki</option>
			        </select>
			      </div>
			      <div class="col col-sm-1">
			        <select class="form-select" v-model="orderSearchQuery.filterStatus">
			          <option value="" selected>Filter (status)</option>
			          <option value="processing">U obradi</option>
			          <option value="preparing">Priprema se</option>
			          <option value="ready_to_deliver">Spremno za dostavu</option>
			          <option value="in_transport">Dostavlja se</option>
			          <option value="delivered">Dostavljeno</option>
			          <option value="cancelled">Otkazano</option>
			        </select>
			      </div>
			      <div class="col col-sm-2">
			        <select class="form-select" v-model="orderSearchQuery.sort">
			          <option value="" selected>Tip sortiranja</option>
			          <option value="restoran_rastuce">Ime restorana (rastuće)</option>
			          <option value="restoran_opadajuce">Ime restorana (opadajuće)</option>
			          <option value="cena_rastuce">Cena (rastuće)</option>
			          <option value="cena_opadajuce">Cena (opadajuće)</option>
			          <option value="datum_rastuce">Datum (rastuće)</option>
			          <option value="datum_opadajuce">Datum (opadajuće)</option>
			        </select>
			      </div>
			      <div class="col col-sm-1" style="border-right: #dc3545; border-right-style: groove">
			        <button type="button" v-on:click="searchOrders()" class="btn btn-danger">Pretraži</button>
			      </div>
			    <div class="row"></div>
			  </div>
		  </div>
	  </div>
      <div class="container-fluid my-container">
        <div class="row row-cols-1 row-cols-md-4 g-4">
          <div v-for="order in orders" class="col">
            <div class="card" style="width: 21rem;">
              <img v-bind:src="order.restaurantLogo" width="300" height="300" class="card-img-top" alt="...">
              <div class="card-body">
                <h5 class="card-title">{{order.restaurantName}}</h5>
              </div>
              <ul class="list-group list-group-flush">
                <li class="list-group-item">{{order.orderInfo}}</li>
                <li class="list-group-item">{{order.dateInfo}}</li>
                <li v-if="order.status === 'PROCESSING'" class="list-group-item">U OBRADI</li>
            	<li v-else-if="order.status === 'PREPARING'" class="list-group-item">PRIPREMA SE</li>
            	<li v-else-if="order.status === 'READY_TO_DELIVER'" class="list-group-item">SPREMNO ZA DOSTAVU</li>
            	<li v-else-if="order.status === 'IN_TRANSPORT'" class="list-group-item">DOSTAVLJA SE</li>
            	<li v-else-if="order.status === 'DELIVERED'" class="list-group-item">DOSTAVLJENO</li>
            	<li v-else-if="order.status === 'CANCELLED'" class="list-group-item">OTKAZANO</li>
              </ul>
              <div class="card-body">
                <h5 class="card-title">{{order.price}} din.</h5>
              </button>
            </div>
          </div>
          </div>
        </div>
      </div>
    </div>

    <div class="tab-pane fade" id="notdeliveredorders" role="tabpanel" aria-labelledby="notdeliveredorders-tab">
      <div class="container-fluid my-container">
        <div class="row row-cols-1 row-cols-md-4 g-4">
          <div v-for="order in notDeliveredOrders" class="col">
            <div class="card" style="width: 21rem;">
              <img v-bind:src="order.restaurantLogo" width="300" height="300" class="card-img-top" alt="...">
              <div class="card-body">
                <h5 class="card-title">{{order.restaurantName}}</h5>
              </div>
              <ul class="list-group list-group-flush">
                <li class="list-group-item">{{order.orderInfo}}</li>
                <li class="list-group-item">{{order.dateInfo}}</li>
                 <li v-if="order.status === 'PROCESSING'" class="list-group-item">U OBRADI</li>
            	<li v-else-if="order.status === 'PREPARING'" class="list-group-item">PRIPREMA SE</li>
            	<li v-else-if="order.status === 'READY_TO_DELIVER'" class="list-group-item">SPREMNO ZA DOSTAVU</li>
            	<li v-else-if="order.status === 'IN_TRANSPORT'" class="list-group-item">DOSTAVLJA SE</li>
            	<li v-else-if="order.status === 'DELIVERED'" class="list-group-item">DOSTAVLJENO</li>
            	<li v-else-if="order.status === 'CANCELLED'" class="list-group-item">OTKAZANO</li>
              </ul>
              <div class="card-body">
                <h5 class="card-title">{{order.price}} din.</h5>
            </div>
          </div>
          </div>
        </div>
        </div>
      </div>
    
    <div class="tab-pane fade" id="notgradedorders" role="tabpanel" aria-labelledby="notgradedorders-tab">
      <div class="container-fluid">
        <div class="row row-cols-1 row-cols-md-4 g-4">
          <div v-for="order in notRatedOrders" class="col">
            <div class="card" style="width: 21rem;">
              <img v-bind:src="order.restaurantLogo" width="300" height="300" class="card-img-top" alt="...">
              <div class="card-body">
                <h5 class="card-title">{{order.restaurantName}}</h5>
              </div>
              <ul class="list-group list-group-flush">
                <li class="list-group-item">{{order.orderInfo}}</li>
                <li class="list-group-item">{{order.dateInfo}}</li>
                <li v-if="order.status === 'PROCESSING'" class="list-group-item">U OBRADI</li>
            	<li v-else-if="order.status === 'PREPARING'" class="list-group-item">PRIPREMA SE</li>
            	<li v-else-if="order.status === 'READY_TO_DELIVER'" class="list-group-item">SPREMNO ZA DOSTAVU</li>
            	<li v-else-if="order.status === 'IN_TRANSPORT'" class="list-group-item">DOSTAVLJA SE</li>
            	<li v-else-if="order.status === 'DELIVERED'" class="list-group-item">DOSTAVLJENO</li>
            	<li v-else-if="order.status === 'CANCELLED'" class="list-group-item">OTKAZANO</li>
                <li class="list-group-item">{{order.price}} din.</li>
              </ul>
              <div class="card-body">
                <button type="button" v-on:click="rateOrder(order.id)" class="btn btn-danger">Oceni</button>
            </div>
          </div>
          </div>
        </div>
      </div>
    </div>

    <div class="tab-pane fade" id="myinfo" role="tabpanel" aria-labelledby="myinfo-tab">
      <div class="container-fluid my-container">
        <div class="row my-row  justify-content-around">
          <div class="col-sm-6 my-col">
              <span class="input-group-text">Ime:</span>
              <input type="text" id="nameInput" v-model="userInfo.name" disabled class="form-control" placeholder="Unesite ime...">
              <span class="input-group-text" id="basic-addon1" style="margin-top: 5%;">Prezime:</span>
              <input type="text" id="lastnameInput" v-model="userInfo.lastname" disabled class="form-control" placeholder="Unesite prezime...">
              <span class="input-group-text" id="basic-addon1" style="margin-top: 5%;">Korisničko ime:</span>
              <input type="text" id="usernameInput"v-model="userInfo.username" disabled class="form-control" placeholder="Unesite korisničko ime...">
              <span class="input-group-text" id="basic-addon1" style="margin-top: 5%;">Lozinka:</span>
              <input type="password" id="passwordInput"v-model="userInfo.password" disabled  class="form-control" placeholder="Unesite lozinku...">
              <span class="input-group-text" id="basic-addon1" style="margin-top: 5%;">Adresa:</span>
              <input type="text" id="addressInput" v-model="userInfo.address" disabled  class="form-control" placeholder="Unesite adresu (ulica, broj, mesto)">
              <span class="input-group-text" id="basic-addon1" style="margin-top: 5%;">Datum rođenja: (mesec/dan/godina)</span>
              <input type="date" id="birthDateInput" v-model="userInfo.birthDate" disabled class="form-control">
            </div>
            <div class="col-sm-6 my-col">
              <img src="images/myinfo.png" height="450" width="600">
              <button type="button" style="margin-top: 13%; margin-right: 10%; margin-left: 8%;" v-on:click="enableInfoEdit()" class="btn btn-secondary btn-lg">Promeni podatke</button>
              <button type="button" style="margin-top: 13%; margin-left: 10%;"  v-on:click="saveInfoEdit()" class="btn btn-danger btn-lg">Sačuvaj izmene</button>
            </div>
          </div>
      </div>
    </div>
  </div>
</div>
`
	, 
	methods : {
		searchRestaurant() {
			axios
				.post('rest/restaurants/searchRestaurants', this.restaurantSearchQuery)
	          	.then(response => (this.restaurants = response.data))
		},
		searchOrders() {
			axios
				.post('rest/orders/searchOrders', this.orderSearchQuery)
	          	.then(response => (this.orders = response.data))
		},
		getRestaurantFoodItems(id){
            this.restaurantId = id;
            this.$router.push({ path: '/selectedRestaurant', query: { id: this.restaurantId }});
        },
        getLoggedUser() {
        	this.customer = JSON.parse(localStorage.getItem("customer"));
        	console.log(this.customer);
        	this.userInfo.id = this.customer.user.id;
        	this.userInfo.name = this.customer.user.name;
        	this.userInfo.lastname = this.customer.user.lastName;
        	this.userInfo.username = this.customer.user.username;
        	this.userInfo.password = this.customer.user.password;
			this.userInfo.birthDate = this.customer.user.dateInfo;
			this.userInfo.address = this.customer.address;
			document.getElementById("birthDateInput").value = this.customer.user.dateInfo;
        	console.log(this.userInfo);
        },
        getRestaurants() {
	        axios
		        .get('rest/restaurants/getRestaurants')
		        .then(response => (this.restaurants = response.data))
	    },
	    getOrders() {
		    axios
		        .get('rest/orders/getOrders')
		        .then(response => (this.orders = response.data))
        },
        getNotDeliveredOrders() {
	        axios
		        .get('rest/orders/getNotDeliveredOrders')
		        .then(response => (this.notDeliveredOrders = response.data))
        },
        getNotRatedOrders() {
	        axios
		        .get('rest/orders/getNotRatedOrders')
		        .then(response => (this.notRatedOrders = response.data))
		},
		getCustomerInfo() {
			if (this.customer.type === "BRONZE") {
				this.customerType = "icons/bronze.png";
			}
			else if (this.customer.type === "SILVER") {
				this.customerType = "icons/silver.png";
			}
			else {	
				this.customerType = "icons/gold.png";
			}
		},
		logOut() {
			this.$router.push('/'); 
	        this.$router.go();
		},
		enableInfoEdit(){
        	document.getElementById("nameInput").disabled = false;
        	document.getElementById("lastnameInput").disabled = false;
        	document.getElementById("usernameInput").disabled = false;
        	document.getElementById("passwordInput").disabled = false;
        	document.getElementById("addressInput").disabled = false;
        	document.getElementById("birthDateInput").disabled = false;
        	
        },
        saveInfoEdit(){
        	axios
				.post('rest/users/saveInfoEditCustomer', this.userInfo)	
				.then(response => {
	                if (response.data != "Niste popunili sve potrebne podatke !"){
						alert("Obaveštenje: " + response.data);
	                }
	                else {
	                    alert("Greška: " + response.data);
	                }
	        });	
        	document.getElementById("nameInput").disabled = true;
        	document.getElementById("lastnameInput").disabled = true;
        	document.getElementById("usernameInput").disabled = true;
        	document.getElementById("passwordInput").disabled = true;
        	document.getElementById("addressInput").disabled = true;
        	document.getElementById("birthDateInput").disabled = true;
        },
        rateOrder(id) {
        	for (o in this.notRatedOrders) {
        		if (this.notRatedOrders[o].id === id) {
					console.log(this.notRatedOrders[o]);
					localStorage.setItem("orderToRate", JSON.stringify(this.notRatedOrders[o]));
					break;
        		}
        	}
        	this.$router.push('/commentPage'); 
	        this.$router.go();
        },
	},
    mounted () {
	    this.getRestaurants();
	    this.getOrders();
        this.getNotDeliveredOrders();
        this.getNotRatedOrders();
	    this.getLoggedUser();
	    this.getCustomerInfo();
		},
		
});