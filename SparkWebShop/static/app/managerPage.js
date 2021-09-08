Vue.component("manager-page", {
	data: function () {
		    return {
			orderSearchQueryForRestaurant : {restaurantId: "", priceDown: "", priceUp: "", dateDown: "", dateUp: "", filterStatus: "", sort: "" },
		    restaurant : {},
		    comments : null,
		    unapprovedComments:null,
		    orders : null,
		    foodItems : null,
		    deliveryMen : null,
		    manager : {},
		    userInfo : {id: "", name: "", lastname: "", username: "", password: "", birthDate: ""},
		    approveDTO :{orderId : "", restaurantId:""},
		    }
	},
	
template:`
  <div>
  <div id="tabs" class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
        <a href="#" class="d-flex align-items-center mb-2 mb-lg-0 text-dark text-decoration-none">
          <img id="logo2" src="images/ponesilogo.png" alt="mdo" width="120" height="38" >
        </a>
  
        <ul class="nav col-12 col-lg-auto me-lg-auto justify-content-center mb-md-0">
          <ul class="nav nav-tabs" id="myTab" role="tablist">
            <li class="nav-item" role="presentation">
              <button class="nav-link active" id="all-orders-tab" data-bs-toggle="tab" data-bs-target="#orders" type="button" role="tab" aria-controls="orders" aria-selected="true">Sve porudžbine</button>
            </li>
            <li class="nav-item" role="presentation">
              <button class="nav-link" id="select-deliveryman-tab" data-bs-toggle="tab" data-bs-target="#selectdelivery" type="button" role="tab" aria-controls="selectdelivery" aria-selected="false">Odabir dostavljača</button>
            </li>
            <li class="nav-item" role="presentation">
              <button class="nav-link" id="comments-tab" data-bs-toggle="tab" data-bs-target="#comments" type="button" role="tab" aria-controls="comments" aria-selected="false">Komentari</button>
            </li>
            <li class="nav-item" role="presentation">
              <button class="nav-link" id="aprove-comments-tab" data-bs-toggle="tab" data-bs-target="#aprove-comments" type="button" role="tab" aria-controls="aprove-comments" aria-selected="false">Odobri Komentare</button>
            </li>
            <li class="nav-item" role="presentation">
              <button class="nav-link" id="restaurant-tab" data-bs-toggle="tab" data-bs-target="#restaurant" type="button" role="tab" aria-controls="restaurant" aria-selected="false">Izmena restorana i artikala</button>
            </li>
            <li class="nav-item" role="presentation" style="margin-left: 50mm;">
              <button class="nav-link" id="myinfo-tab" data-bs-toggle="tab" data-bs-target="#myinfo" type="button" role="tab" aria-controls="myinfo" aria-selected="false">Moji podaci
                <img src="icons/manager.png" alt="mdo" width="24" height="24" class="rounded-circle">
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
          <button type="button" class="btn btn-danger" v-on:click = "logOut()">Potvrdi</button>
        </div>
      </div>
    </div>
  </div>
  
    <div class="tab-content" id="myTabContent">
      <div class="tab-pane fade show active" id="orders" role="tabpanel" aria-labelledby="all-orders-tab">
        <div class="surface">
          <div class="container-fluid">
              <div class="row">
                
                <div class="col col-sm-1" style="border-left: #dc3545; border-left-style: groove">
                  <input type="number" min="0" max="10000" step="100" v-model="orderSearchQueryForRestaurant.priceDown" class="form-control" placeholder="Cena-od">
                </div>
                <div class="col col-sm-1">
                  <input type="number" min="100" max="10000" step="100"v-model="orderSearchQueryForRestaurant.priceUp" class="form-control" placeholder="Cena-do">
                </div>
                <div class="col col-sm-2">
                  <input type="date" v-model="orderSearchQueryForRestaurant.dateDown" class="form-control" placeholder="Datum-od">
                  <span class="input-group-text" id="basic-addon1">Datum-od</span>
                </div>
                <div class="col col-sm-2">
                  <input type="date" v-model="orderSearchQueryForRestaurant.dateUp" class="form-control" placeholder="Datum-do">
                  <span class="input-group-text" id="basic-addon1">Datum-do</span>
                </div>
                <div class="col col-sm-1">
                  <select class="form-select" v-model="orderSearchQueryForRestaurant.filterStatus">
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
                  <select class="form-select" v-model="orderSearchQueryForRestaurant.sort">
                    <option value="" selected>Tip sortiranja</option>
                    <option value="cena_rastuce">Cena (rastuće)</option>
                    <option value="cena_opadajuce">Cena (opadajuće)</option>
                    <option value="datum_rastuce">Datum (rastuće)</option>
                    <option value="datum_opadajuce">Datum (opadajuće)</option>
                  </select>
                </div>
                <div class="col col-sm-1" style="border-right: #dc3545; border-right-style: groove">
                  <button type="button" v-on:click="searchOrdersForRestaurant()" class="btn btn-danger">Pretraži</button>
                </div>
              <div class="row"></div>
            </div>
          </div>
        </div>
		
        <table class="table table-hover table-striped ">
          <thead>
              <th>Ime i prezime mušterije</th>
              <th>Adresa mušterije</th>
              <th>Ime artikla</th>
              <th>Datum i vreme porudžbine</th>
              <th>Cena</th>
              <th colspan="2">Status</th>
          </thead>
       <tbody>
       <tr v-for="order in orders">
       <td>{{order.ime}} {{order.prezime}}</td>
       <td>{{order.adresa}}</td>
       <td>{{order.imeArtikla}}</td>
       <td>{{order.dateInfo}}</td>
       <td>{{order.cena}}</td>
       <td colspan="2">{{order.status}}</td>
      </tr>
      </tbody>
      </table>
            </div>
    
  
     
  
        <div class="tab-pane fade" id="selectdelivery" role="tabpanel" aria-labelledby="select-deliveryman-tab">
          <div class="container-fluid my-container">
            <div class="row my-row  justify-content-around">
              <table class="table table-hover table-striped ">
                <thead>
                    <th>Ime i prezime dostavljača</th>
                    <th>Ime artikla</th>
                    <th>Datum i vreme porudžbine</th>
                    <th>Cena</th>
                    <th></th>
                </thead>
             <tbody>
             <tr v-for="deliveryMan in deliveryMen">
             <td>{{deliveryMan.deliveryManName}} {{deliveryMan.deliveryManLastName}} </td>
             <td>{{deliveryMan.orderInfo}}</td>
             <td>{{deliveryMan.orderDateTime}}</td>
             <td>{{deliveryMan.orderPrice}}</td>
             <td><button type="button" class="btn btn-success btn-sm">Prosledi porudžbinu.</button> <button type="button" class="btn btn-danger btn-sm">Odbij dostavljača.</button></td>
            </tr>
            </tbody>
            </table>
              </div>
          </div>
        </div>
        
        <div class="tab-pane fade" id="aprove-comments" role="tabpanel" aria-labelledby="aprove-comments-tab">
          
          <table class="table table-hover table-striped ">
              <thead>
                <tr>
                  <th>Korisničko ime</th>
                  <th>Ime</th>
                  <th>Prezime</th>
                  <th>Sadržaj komentara</th>
                  <th>Ocena</th>
                  <th>Odobri</th>
                </tr>
              </thead>
              <tbody>
             <tr v-for="comment in comments" v-if="comment.status === 'CEKA_ODOBRENJE'" >
                <td>{{comment.username}}</td>
                <td>{{comment.name}}</td>
                <td>{{comment.lastname}}</td> 
                <td>{{comment.content}}</td>
                <td>{{comment.rating}}/5</td>
                <td><button type="button" v-on:click="changeToApproved(comment.orderId)" class="btn btn-success ">DA.</button><button type="button" v-on:click="changeToUnapproved(comment.orderId)" class="btn btn-danger ">NE.</button></td>
              </tr>
            </tbody>
            </table>
          
        </div>
      <div class="tab-pane fade" id="comments" role="tabpanel" aria-labelledby="comments-tab">
        <div class="container-fluid">
          <table class="table table-hover table-striped ">
            <thead>
              <tr>
                <th scope="col">Korisničko ime</th>
                <th scope="col">Ime</th>
                <th scope="col">Prezime</th>
                <th scope="col">Sadržaj komentara</th>
                <th scope="col">Ocena</th>
                <th scope="col">Odobren</th>
              </tr>
            </thead>
            <tbody>
             <tr v-for="comment in comments" v-if="comment.status != 'CEKA_ODOBRENJE'" >
                <td>{{comment.username}}</td>
                <td>{{comment.name}}</td>
                <td>{{comment.lastname}}</td> 
                <td>{{comment.content}}</td>
                <td>{{comment.rating}}/5</td>
                <td>{{comment.status}}</td>
              </tr>
            </tbody>
          </table>
           
        </div>
      </div>
  
      <div class="tab-pane fade" id="restaurant" role="tabpanel" aria-labelledby="restaurant-tab">
        <div class="container-fluid">
          <!-- Button trigger modal -->
        <button type="button" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#exampleModal">
        Dodaj novi artikal
        </button>
        </div>

<!-- Modal -->
<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-scrollable modal-xl">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Novi artikal</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <div class="row">
        <div class="colsm-5">
          <label class="input-group-text" for="inputGroupFile01">Izbor slike artikla:</label>  
      </div>
      <div class="col">
        <input type="file" class="form-control" id="inputGroupFile01">
      </div>
    </div>
    <div class="row">
      <div class="col-sm-5 ">
        <label class="input-group-text">Ime artikla:</label>  
      </div>
      <div class="col">
      <input type="text" class="form-control">
      </div>
    </div>
    <div class="row">
      <div class="col-sm-5 ">
        <label class="input-group-text">Cena artikla:</label>  
      </div>
      <div class="col">
      <input type="text" class="form-control" >
      </div>
    </div>
    <div class="row">
      <div class="col-sm-5 ">
        <label class="input-group-text">Količina artikla:</label>  
      </div>
      <div class="col">
      <input type="text" class="form-control">
      </div>
      </div>
      <div class="row">
        <div class="col-sm-5 ">
          <label class="input-group-text">Opis artikla:</label>  
        </div>
        <div class="col">
        <textarea type="text" class="form-control"></textarea>
        </div>
    </div>

      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Zatvori</button>
        <button type="button" class="btn btn-primary">Sačuvaj</button>
      </div>
    </div>
  </div>
</div>
		   <div class="row row-cols-1 row-cols-md-4 g-4">
		  <div class="col">
            <div class="card" style="width: 21rem;">
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
              <button type="button" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#izmeniModal">
                <a href="#" class="card-link">Izmeni artikal</a>
              </button>
              <button type="button" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#izmeniModal">
                <a href="#" class="card-link">Obriši artikal</a>
			   </button>
          </div>
        </div>
      </div>
      <div class="modal fade" id="izmeniModal" tabindex="-1" aria-labelledby="izmeniModal" aria-hidden="true">
        <div class="modal-dialog modal-dialog-scrollable modal-xl" v-for="fi in foodItems">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title" id="exampleModalLabel">Izmeni artikal</h5>
              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body" >
              <div class="row">
              <div class="colsm-5">
                <label class="input-group-text" for="inputGroupFile01">Promeni sliku artikla:</label>  
            </div>
            <div class="col">
              <input type="file" class="form-control" id="inputGroupFile01">
            </div>
          </div>
          <div class="row">
            <div class="col-sm-5 ">
              <label class="input-group-text">Ime artikla:</label>  
            </div>
            <div class="col">
            <input type="text" class="form-control">
            </div>
          </div>
          <div class="row">
            <div class="col-sm-5 ">
              <label class="input-group-text">Cena artikla:</label>  
            </div>
            <div class="col">
            <input type="text" class="form-control" >
            </div>
          </div>
          <div class="row">
            <div class="col-sm-5 ">
              <label class="input-group-text">Količina artikla:</label>  
            </div>
            <div class="col">
            <input type="text" class="form-control">
            </div>
            </div>
            <div class="row">
              <div class="col-sm-5 ">
                <label class="input-group-text">Opis artikla:</label>  
              </div>
              <div class="col">
              <textarea type="text" class="form-control"></textarea>
              </div>
          </div>
      
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Zatvori</button>
              <button type="button" class="btn btn-primary">Sačuvaj</button>
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
              <span class="input-group-text" style="margin-top: 10%;">Prezime:</span>
              <input type="text" id="lastnameInput" v-model="userInfo.lastname" disabled class="form-control" placeholder="Unesite prezime...">
              <span class="input-group-text" style="margin-top: 10%;">Korisničko ime:</span>
              <input type="text" id="usernameInput"v-model="userInfo.username" disabled class="form-control" placeholder="Unesite korisničko ime...">
              <span class="input-group-text" style="margin-top: 10%;">Lozinka:</span>
              <input type="password" id="passwordInput"v-model="userInfo.password" disabled class="form-control" placeholder="Unesite lozinku...">
              <span class="input-group-text" style="margin-top: 10%;">Datum rođenja: (mesec/dan/godina)</span>
              <input type="date" id="birthDateInput" v-model="userInfo.birthDate" disabled class="form-control" placeholder="Unesite datum rođenja (DD.MM.YYYY.)">
            </div>
            <div class="col-sm-6 my-col">
              <img src="images/myinfo.png" height="450" width="600">
              <button type="button" style="margin-top: 13%; margin-right: 10%; margin-left: 8%;" v-on:click="enableInfoEdit()" class="btn btn-secondary btn-lg">Promeni podatke</button>
              <button type="button" style="margin-top: 13%; margin-left: 10%;" v-on:click="saveInfoEdit()" class="btn btn-danger btn-lg">Sačuvaj izmene</button>
            </div>
          </div>
      </div>
    </div>
  </div>
</div>
`
	, 
	methods : {
	
	
	logOut() {
			this.$router.push('/'); 
	        this.$router.go();
		},
		
	changeToApproved(id){
			this.approveDTO.restaurantId = this.manager.restaurantId;
			this.approveDTO.orderId = id;
			axios
				.post('rest/comments/approveComment', this.approveDTO)
	          	.then(response => (this.comments = response.data))
	},
	changeToUnapproved(id){
			this.approveDTO.restaurantId = this.manager.restaurantId;
			this.approveDTO.orderId = id;
			axios
				.post('rest/comments/unapproveComment', this.approveDTO)
	          	.then(response => (this.comments = response.data))
	}, 
	searchOrdersForRestaurant() {
			this.orderSearchQueryForRestaurant.restaurantId = this.manager.restaurantId;
			axios
				.post('rest/orders/searchOrdersForRestaurant', this.orderSearchQueryForRestaurant)
	          	.then(response => (this.orders = response.data))
	          	console.log(this.orderSearchQueryForRestaurant);
		},
	getManagedRestaurant(){
			axios
				.get('rest/restaurant/ManagedRestaurant/' + this.manager.user.id)
				.then(response => (this.restaurant = response.data))
				console.log(this.restaurant);
	},
	getDeliveryMen(){
			axios
				.get('rest/orders/getDeliveryMen/'+ this.manager.restaurantId)
				.then(response => (this.deliveryMen = response.data))
	},
	getLoggedUser() {
        	this.manager = JSON.parse(localStorage.getItem("manager"));
        	console.log(this.manager);
        	this.userInfo.id = this.manager.user.id;
        	this.userInfo.name = this.manager.user.name;
        	this.userInfo.lastname = this.manager.user.lastName;
        	this.userInfo.username = this.manager.user.username;
        	this.userInfo.password = this.manager.user.password;
			this.userInfo.birthDate = this.manager.user.dateInfo;
			document.getElementById("birthDateInput").value = this.manager.user.dateInfo;
        	console.log(this.userInfo);
        },
    getOrders(){
    		axios
    			.get('rest/orders/allRestaurantOrders/' + this.manager.restaurantId)
    		    .then(response => (this.orders = response.data))
    },
	getAllCommentsForRestaurant() {
	  	axios
	  	    .get('rest/comments/getAllCommentsForRestaurant/' + this.manager.restaurantId)
	  		.then(response => (this.comments = response.data))
	  		console.log(this.comments)
	  	},
	  getAllFoodItems(){
	  		axios
	  		 .get('rest/restaurant/getAllFoodItems/' + this.manager.restaurantId)
	  		 .then(response => (this.foodItems = response.data))
	  		console.log(this.foodItems)
	    },
		},
		mounted () {
		this.getLoggedUser();
		this.getManagedRestaurant();
		this.getOrders();
		this.getAllCommentsForRestaurant();
		this.getAllFoodItems();
		this.getDeliveryMen();
		},
		
});