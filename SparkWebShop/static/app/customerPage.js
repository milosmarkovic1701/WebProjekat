Vue.component("customer-page", {
	data: function () {
		    return {
				restaurantSearchQuery : {name: "", type: "", location: "", rating: "", filterType: "", filterStatus: "", sort: ""},
				orderSearchQuery : {restaurantName: "", priceDown: "", priceUp: "", dateDown: "", dateUp: "", filterType: "", filterStatus: "", sort: "" },
				restaurants : null,
				restaurantComments : null,
				restaurantId : "",
				user : "",
				customerStatus: "",
		    	orders: "",
		    	notDeliveredOrders: null,
		    	notRatedOrders: null,
		    	notDeliveredOrdersSize: null,
		    	notRatedOrdersSize: null,
		    }
	},
	
template:`
  <div>
  <div id="tabs" class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
        <img id="logo2" src="images/ponesilogo.png" alt="mdo" width="120" height="38" >

      <ul class="nav col-12 col-lg-auto me-lg-auto justify-content-center mb-md-0">
        <ul class="nav nav-tabs" id="myTab" role="tablist">
          <li class="nav-item" role="presentation">
            <button class="nav-link active" id="restaurants-tab" data-bs-toggle="tab" data-bs-target="#restaurants" type="button" role="tab" aria-controls="restaurant" aria-selected="true">Svi restorani</button>
          </li>
          <li class="nav-item" role="presentation">
            <button class="nav-link" id="orders-tab" data-bs-toggle="tab" data-bs-target="#orders" type="button" role="tab" aria-controls="orders" aria-selected="false">Sve porudžbine</button>
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
          <li class="nav-item" role="presentation">
            <button class="nav-link" disabled id="test-tab" data-bs-toggle="tab" data-bs-target="#test" type="button" role="tab" aria-controls="test" aria-selected="false">Restoran</button>
          </li>
          <li class="nav-item" role="presentation" style="margin-left: 80mm;"> <!--125mm-->
            <button class="nav-link" id="myinfo-tab" data-bs-toggle="tab" data-bs-target="#myinfo" type="button" role="tab" aria-controls="myinfo" aria-selected="false">Moji podaci
              <img src="icons/user.png" alt="mdo" width="24" height="24" class="rounded-circle">
              <img src="icons/gold.png" alt="mdo" width="24" height="24" class="rounded-circle">
              <span class="badge">1000</span>
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
        <button type="button" class="btn btn-danger">Potvrdi</button>
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
			        <input type="text" v-model="restaurantSearchQuery.rating" class="form-control" placeholder="Ocena">
			      </div>
			      <div class="col col-sm-2">
			        <select class="form-select" v-model="restaurantSearchQuery.filterType" id="inputGroupSelect04">
			          <option value="" selected>Filter (tip)</option>
			          <option value="rostilj">Roštilj</option>
			          <option value="palacinke">Palačinke</option>
			          <option value="krofne">Krofne</option>
			          <option value="pekara">Pekara</option>
			          <option value="poslasticarnica">Poslastičarnica</option>
			          <option value="picerija">Picerija</option>
			          <option value="italijanski">Italijanski</option>
			          <option value="meksicki">Meksički</option>
			          <option value="kineski">Kineski</option>
			          <option value="francuski">Francuski</option>
			          <option value="japanski">Japanski</option>
			          <option value="indijski">Indijski</option>
			          <option value="turski">Turski</option>
			          <option value="grcki">Grčki</option>
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
	          <img v-bind:src="restaurant.logo" width="300" height="220" class="card-img-top" alt="...">
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
				<button type="button" v-on:click="getFoodItems(restaurant.id)" class="btn btn-danger">Prikaži artikle</button>
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
              <img v-bind:src="order.restaurantLogo" width="300" height="220" class="card-img-top" alt="...">
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
              <img v-bind:src="order.restaurantLogo" width="300" height="220" class="card-img-top" alt="...">
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
              <img v-bind:src="order.restaurantLogo" width="300" height="220" class="card-img-top" alt="...">
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
                <button type="button" class="btn btn-danger">Oceni</button>
            </div>
          </div>
          </div>
        </div>
      </div>
    </div>

    <div class="tab-pane fade" id="test" role="tabpanel" aria-labelledby="test-tab">
      <div class="container-fluid my-container justify-content-between">
        <div class="card mb-3" style="width: 100%;">
          <div class="row g-0">
            <div class="col-md-4">
              <img src="restaurant logos/gyros master.jpg" class="img-fluid rounded-start" width="400" height="550">
            </div>
            <div class="col-md-8">
              <div class="card-body">
                <h2 class="card-title" style="color: #dc3545;">Gyros Master</h2>
              </div>
              <div class="row">
                <div class="col">
                  <ul class="list-group list-group-flush">
                    <li class="list-group-item">Tip restorana:</li>
                    <li class="list-group-item">Adresa:</li>
                    <li class="list-group-item">Radno vreme:</li>
                    <li class="list-group-item">Prosečna ocena:</li>
                  </ul>
                </div>
                <div class="col">
                  <ul class="list-group list-group-flush">
                    <li class="list-group-item">Grčki</li>
                    <li class="list-group-item">Spensova 3, Novi Sad</li>
                    <li class="list-group-item">08:00 - 24:00</li>
                    <li class="list-group-item">4.5/5</li>
                  </ul>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="container-fluid my-container justify-content-between" style="margin-top: 14mm;">
        <div class="row">
          <div class="col">
            <h2 style="color: #dc3545; margin-left: 10%">Svi artikli:</h2>
          </div>
          <div class="col"></div>
          <div class="col">
            <td><button type="button" class="btn btn-lg btn-danger">Sadržaj korpe</button><p style="font-size: 20px; color: #424242; margin-top: 2mm;">Iznos korpe: 800,00 din</p></td>
          </div>
        </div>
      </div>
      <div class="container-fluid my-container justify-content-between" style="margin-top: 5mm;">
        <div class="row row-cols-1 row-cols-md-4 g-4">
          <div class="col">
            <div class="card" style="width: 21rem;">
              <img  src="food images/pizza.png" width="300" height="200" class="card-img-top" alt="...">
              <div class="card-body">
                <h5 class="card-title">Capricozza</h5>
              </div>
              <ul class="list-group list-group-flush">
                <li class="list-group-item">pelat, sunka, kackavalj, origano</li>
                <li class="list-group-item">50 cm</li>
                <li class="list-group-item">800,00 din.</li>
              </ul>
              <div class="card-body">
                <div class="container-fluid my-container justify-content-around">
                    <input type="number" id="quantity" value="1" name="quantity" min="1" max="10" style="background-color: #eae7dc; border-radius: 10%; border-color: #dc3545;">
                    <button style="margin-left: 13mm;" type="button" class="btn btn-danger">Dodaj u korpu</button>
                </div>
            </div>
          </div>
          </div>
          <div class="col">
            <div class="card" style="width: 21rem;">
              <img  src="food images/pizza.png" width="300" height="200" class="card-img-top" alt="...">
              <div class="card-body">
                <h5 class="card-title">Capricozza</h5>
              </div>
              <ul class="list-group list-group-flush">
                <li class="list-group-item">pelat, sunka, kackavalj, origano</li>
                <li class="list-group-item">50 cm</li>
                <li class="list-group-item">800,00 din.</li>
              </ul>
              <div class="card-body">
                <div class="container-fluid my-container justify-content-around">
                    <input type="number" id="quantity" value="1" name="quantity" min="1" max="10" style="background-color: #eae7dc; border-radius: 10%; border-color: #dc3545;">
                    <button style="margin-left: 13mm;" type="button" class="btn btn-danger">Dodaj u korpu</button>
                </div>
            </div>
          </div>
          </div>
          <div class="col">
            <div class="card" style="width: 21rem;">
              <img  src="food images/pizza.png" width="300" height="200" class="card-img-top" alt="...">
              <div class="card-body">
                <h5 class="card-title">Capricozza</h5>
              </div>
              <ul class="list-group list-group-flush">
                <li class="list-group-item">pelat, sunka, kackavalj, origano</li>
                <li class="list-group-item">50 cm</li>
                <li class="list-group-item">800,00 din.</li>
              </ul>
              <div class="card-body">
                <div class="container-fluid my-container justify-content-around">
                    <input type="number" id="quantity" value="1" name="quantity" min="1" max="10" style="background-color: #eae7dc; border-radius: 10%; border-color: #dc3545;">
                    <button style="margin-left: 13mm;" type="button" class="btn btn-danger">Dodaj u korpu</button>
                </div>
            </div>
          </div>
          </div>
          <div class="col">
            <div class="card" style="width: 21rem;">
              <img  src="food images/pizza.png" width="300" height="200" class="card-img-top" alt="...">
              <div class="card-body">
                <h5 class="card-title">Capricozza</h5>
              </div>
              <ul class="list-group list-group-flush">
                <li class="list-group-item">pelat, sunka, kackavalj, origano</li>
                <li class="list-group-item">50 cm</li>
                <li class="list-group-item">800,00 din.</li>
              </ul>
              <div class="card-body">
                <div class="container-fluid my-container justify-content-around">
                    <input type="number" id="quantity" value="1" name="quantity" min="1" max="10" style="background-color: #eae7dc; border-radius: 10%; border-color: #dc3545;">
                    <button style="margin-left: 13mm;" type="button" class="btn btn-danger">Dodaj u korpu</button>
                </div>
            </div>
          </div>
          </div>
          <div class="col">
            <div class="card" style="width: 21rem;">
              <img  src="food images/pizza.png" width="300" height="200" class="card-img-top" alt="...">
              <div class="card-body">
                <h5 class="card-title">Capricozza</h5>
              </div>
              <ul class="list-group list-group-flush">
                <li class="list-group-item">pelat, sunka, kackavalj, origano</li>
                <li class="list-group-item">50 cm</li>
                <li class="list-group-item">800,00 din.</li>
              </ul>
              <div class="card-body">
                <div class="container-fluid my-container justify-content-around">
                    <input type="number" id="quantity" value="1" name="quantity" min="1" max="10" style="background-color: #eae7dc; border-radius: 10%; border-color: #dc3545;">
                    <button style="margin-left: 13mm;" type="button" class="btn btn-danger">Dodaj u korpu</button>
                </div>
            </div>
          </div>
          </div>
        </div>
      </div>
      <div class="container-fluid" style="margin-top: 15mm; margin-bottom: 15mm;">
        <h2 style="color: #dc3545;">Gyros Master - Komentari i iskustva mušterija:</h2>
      </div>
      <div class="container-fluid">
        <table class="table">
          <thead>
            <tr>
              <th scope="col">Korisničko ime</th>
              <th scope="col">Ime</th>
              <th scope="col">Prezime</th>
              <th scope="col">Porudžbina</th>
              <th scope="col">Sadržaj komentara</th>
              <th scope="col">Ocena</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>mihhh47</td>
              <td>Mihailo</td>
              <td>Majstorovic</td>
              <td>Burger 1x, Pizza 2x</td>
              <td>Burger je fenomenalan, pica je mogla biti pecenija.</td>
              <td>4/5</td>
            </tr>
            <tr>
              <td>misa00</td>
              <td>Misa</td>
              <td>Markovic</td>
              <td>Palačinka Bueno 1x</td>
              <td>Perfection.</td>
              <td>5/5</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <div class="tab-pane fade" id="myinfo" role="tabpanel" aria-labelledby="myinfo-tab">
      <div class="container-fluid my-container">
        <div class="row my-row  justify-content-around">
          <div class="col-sm-6 my-col">
              <span class="input-group-text" id="basic-addon1">Ime:</span>
              <input type="text" class="form-control" placeholder="Unesite ime...">
              <span class="input-group-text" id="basic-addon1" style="margin-top: 5%;">Prezime:</span>
              <input type="text" class="form-control" placeholder="Unesite prezime...">
              <span class="input-group-text" id="basic-addon1" style="margin-top: 5%;">Korisničko ime:</span>
              <input type="text" class="form-control" placeholder="Unesite korisničko ime...">
              <span class="input-group-text" id="basic-addon1" style="margin-top: 5%;">Lozinka:</span>
              <input type="text" class="form-control" placeholder="Unesite lozinku...">
              <span class="input-group-text" id="basic-addon1" style="margin-top: 5%;">Adresa:</span>
              <input type="text" class="form-control" placeholder="Unesite adresu (ulica, broj, mesto)">
              <span class="input-group-text" id="basic-addon1" style="margin-top: 5%;">Datum rođenja:</span>
              <input type="date" class="form-control" placeholder="Unesite datum rođenja (DD.MM.YYYY.)">
            </div>
            <div class="col-sm-6 my-col">
              <img src="images/myinfo.png" height="450" width="600">
              <button type="button" style="margin-top: 13%; margin-right: 10%; margin-left: 8%;" class="btn btn-secondary btn-lg">Promeni podatke</button>
              <button type="button" style="margin-top: 13%; margin-left: 10%;" class="btn btn-danger btn-lg">Sačuvaj izmene</button>
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
	},
    mounted () {
	    axios
	        .get('rest/restaurants/getRestaurants')
	        .then(response => (this.restaurants = response.data))
	    axios
	        .get('rest/orders/getOrders')
	        .then(response => (this.orders = response.data))
        axios
	        .get('rest/orders/getNotDeliveredOrders')
	        .then(response => (this.notDeliveredOrders = response.data))
        axios
	        .get('rest/orders/getNotRatedOrders')
	        .then(response => (this.notRatedOrders = response.data))
		},
});