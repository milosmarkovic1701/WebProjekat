Vue.component("deliveryMan-page", {
	data: function () {
		    return {
				orderSearchQuery : {deliverymanId:"", restaurantName: "", priceDown: "", priceUp: "", dateDown: "", dateUp: "", filterType: "", filterStatus: "", sort: "" },
		    	userInfo : {id: "", name: "", lastname: "", username: "", password: "", birthDate: ""},
		    	orders: null,
		    	deliveryman: {},
		    	newDTO : {orderId:"",deliverymanId:""},
		    	ordersSize: 0,
			    ordersInTransportSize: 0,
			    ordersToDeliverSize: 0,
		    }
	},
	
template:`
  <div>
<div id="tabs" class="d-flex flex-wrap align-items-center justify-content-center justify-content-sm-start">
      <ul class="nav col-sm-12 col-sm-auto me-sm-auto justify-content-center mb-md-0">
      <img src="images/ponesilogo.png" alt="mdo" width="120" height="42" >
          <ul class="nav nav-tabs" id="myTab" role="tablist">
            <li class="nav-item" role="presentation">
              <button class="nav-link active" id="all-deliveries-tab" data-bs-toggle="tab" data-bs-target="#all-deliveries" type="button" role="tab" aria-controls="all-deliveries" aria-selected="true">Sve porudžbine
              <span class="badge">{{this.ordersSize}}</span>
              </button>
            </li>
            <li class="nav-item" role="presentation">
              <button class="nav-link" id="waiting-for-delivery-tab" data-bs-toggle="tab" data-bs-target="#waiting-for-delivery" type="button" role="tab" aria-controls="waiting-for-delivery" aria-selected="false">Pripremljenje porudžbine
              <span class="badge">{{this.ordersToDeliverSize}}</span>
              </button>
            </li>
            <li class="nav-item" role="presentation">
              <button class="nav-link" id="transport-delivery-tab" data-bs-toggle="tab" data-bs-target="#transport-delivery" type="button" role="tab" aria-controls="transport-delivery" aria-selected="false">Porudžbine u transportu
              <span class="badge">{{this.ordersInTransportSize}}</span>
              </button>
            </li>
            <li class="nav-item" role="presentation" style="margin-left: 171mm;">
              <button class="nav-link" id="myinfo-tab" data-bs-toggle="tab" data-bs-target="#myinfo" type="button" role="tab" aria-controls="myinfo" aria-selected="false">Moji podaci
                <img src="icons/delivery-man.png" alt="mdo" width="24" height="24" class="rounded-circle">
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
          <button type="button" v-on:click = "logOut()" class="btn btn-danger">Potvrdi</button>
        </div>
      </div>
    </div>
  </div>
  
    <div class="tab-content" id="myTabContent">
      <div class="tab-pane fade show active" id="all-deliveries" role="tabpanel" aria-labelledby="all-deliveries-tab">
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

                <table class="table table-hover table-striped ">
                    <thead>
                        <th>Ime i prezime mušterije</th>
                        <th>Adresa mušterije</th>
                        <th>Ime artikla</th>
                        <th>Ime restorana</th>
                        <th>Datum i vreme porudžbine</th>
                        <th>Cena</th>
                        <th>Status</th>
                    </thead>
                 <tbody>
                 <tr v-for="order in orders" v-if="order.deliveryId === deliveryman.user.id">
                 <td>{{order.ime}} {{order.prezime}}</td>
                 <td>{{order.adresa}}</td>
                 <td>{{order.orderInfo}}</td>
                 <td>{{order.restaurantName}}</td>
                 <td>{{order.dateInfo}}</td>
                 <td>{{order.cena}}</td>
                 <td v-if="order.status === 'PROCESSING'" class="list-group-item">U OBRADI</td>
            	<td v-else-if="order.status === 'PREPARING'" class="list-group-item">PRIPREMA SE</td>
            	<td v-else-if="order.status === 'READY_TO_DELIVER'" class="list-group-item">SPREMNO ZA DOSTAVU</td>
            	<td v-else-if="order.status === 'IN_TRANSPORT'" class="list-group-item">DOSTAVLJA SE</td>
            	<td v-else-if="order.status === 'DELIVERED'" class="list-group-item">DOSTAVLJENO</td>
            	<td v-else-if="order.status === 'CANCELLED'" class="list-group-item">OTKAZANO</td>
                </tr>
              </tbody>
              </table>
            </div>
    
  
     
  
        <div class="tab-pane fade" id="waiting-for-delivery" role="tabpanel" aria-labelledby="waiting-for-delivery-tab">
          <div class="container-fluid my-container">
            <div class="row my-row  justify-content-around">

                <table class="table table-hover table-striped ">
                    <thead>
                        <th>Ime i prezime mušterije</th>
                        <th>Adresa mušterije</th>
                        <th>Ime artikla</th>
                        <th>Ime restorana</th>
                        <th>Datum i vreme porudžbine</th>
                        <th>Cena</th>
                        <th>Preuzmi</th>
                    </thead>
                 <tbody>
				<tr v-for="order in orders" v-if="order.deliveryId === 0 && order.status === 'READY_TO_DELIVER'" >
                 <td>{{order.ime}} {{order.prezime}}</td>
                 <td>{{order.adresa}}</td>
                 <td>{{order.orderInfo}}</td>
                 <td>{{order.restaurantName}}</td>
                 <td>{{order.dateInfo}}</td>
                 <td>{{order.cena}}</td>
                 <td><button  type="button" v-on:click="GiveOrderToDeliveryMan(order.orderId)" class="btn btn-success">Prihvati</button></td>
                </tr>
                </tbody>
                </table>
              </div>
          </div>
        </div>
      
        <div class="tab-pane fade" id="transport-delivery" role="tabpanel" aria-labelledby="transport-delivery-tab">
          
            <table class="table table-hover table-striped ">
                <thead>
                    <th>Ime i prezime mušterije</th>
                    <th>Adresa mušterije</th>
                    <th>Ime artikla</th>
                    <th>Ime restorana</th>
                    <th>Datum i vreme porudžbine</th>
                    <th>Cena</th>
                    <th></th>
                </thead>
             <tbody>
             <tr v-for="order in orders" v-if="order.deliveryId === deliveryman.user.id && order.status === 'IN_TRANSPORT'" >
                 <td>{{order.ime}} {{order.prezime}}</td>
                 <td>{{order.adresa}}</td>
                 <td>{{order.orderInfo}}</td>
                 <td>{{order.restaurantName}}</td>
                 <td>{{order.dateInfo}}</td>
                 <td>{{order.cena}}</td>
                 <td><button  type="button" v-on:click = "FinishDelivery(order.orderId)" class="btn btn-success">Završi</button></td>
                </tr>
            </tbody>
            </table>
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
		searchOrders() {
			this.orderSearchQuery.deliverymanId = this.deliveryman.user.id
			axios
				.post('rest/orders/searchOrdersDeliveryman', this.orderSearchQuery)
	          	.then(response => {
	          	this.orders = response.data;
	          	this.getNumberIndicatorsOrders();
	          	})
		},
        getLoggedUser() {
        	this.deliveryman = JSON.parse(localStorage.getItem("deliveryman"));
        	this.userInfo.id = this.deliveryman.user.id;
        	this.userInfo.name = this.deliveryman.user.name;
        	this.userInfo.lastname = this.deliveryman.user.lastName;
        	this.userInfo.username = this.deliveryman.user.username;
        	this.userInfo.password = this.deliveryman.user.password;
			this.userInfo.birthDate = this.deliveryman.user.dateInfo;
			document.getElementById("birthDateInput").value = this.deliveryman.user.dateInfo;
        },
		getNumberIndicatorsOrders() {
		this.ordersSize = 0;
	    this.ordersInTransportSize = 0;
	    this.ordersToDeliverSize = 0;	
	    for (o in this.orders) {
        		if (this.orders[o].deliveryId === this.deliveryman.user.id)
        			this.ordersSize += 1;
        		if (this.orders[o].deliveryId === this.deliveryman.user.id && this.orders[o].status === 'IN_TRANSPORT')
					this.ordersInTransportSize += 1;
				if (this.orders[o].deliveryId === 0 && this.orders[o].status === 'READY_TO_DELIVER') 
					this.ordersToDeliverSize += 1;
        	}
		},
	    getAllOrders() {
		    axios
		        .get('rest/orders/getAllOrders')
		        .then(response => {
		        this.orders = response.data;
		        this.getNumberIndicatorsOrders();
		        })
		        
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
        	document.getElementById("birthDateInput").disabled = false;	
        },
        saveInfoEdit(){
        	axios
				.post('rest/users/saveInfoEdit', this.userInfo)	
				.then(response => {
	                if (response.data != "Niste popunili sve potrebne podatke !"){
						alert("Obaveštenje: " + response.data);
						this.deliveryman.user.id = this.userInfo.id
        				this.deliveryman.user.name = this.userInfo.name; 
        				this.deliveryman.user.lastName = this.userInfo.lastname;
        				this.deliveryman.user.username = this.userInfo.username;
        				this.deliveryman.user.password = this.userInfo.password;
			 			this.deliveryman.user.dateInfo = this.userInfo.birthDate;
			 			localStorage.setItem("deliveryman", JSON.stringify(this.deliveryman));
	                }
	                else {
	                    alert("Greška: " + response.data);
	                }
	        });	
        	document.getElementById("nameInput").disabled = true;
        	document.getElementById("lastnameInput").disabled = true;
        	document.getElementById("usernameInput").disabled = true;
        	document.getElementById("passwordInput").disabled = true;
        	document.getElementById("birthDateInput").disabled = true;
        },
        GiveOrderToDeliveryMan(id){
        	this.newDTO.deliveryId = this.deliveryman.user.id
			this.newDTO.orderId = id;
		axios
				.post('rest/order/giveOrderToDeliveryMan', this.newDTO)
	          	.then(response => {
	          	this.orders = response.data;
	          	this.getNumberIndicatorsOrders();
	          	})    	
        },
        FinishDelivery(id){
        	this.newDTO.deliveryId = this.deliveryman.user.id
			this.newDTO.orderId = id
		axios
				.post('rest/order/FinishDelivery', this.newDTO)
	          	.then(response => {
	          	this.orders = response.data;
	          	this.getNumberIndicatorsOrders();		
	          	})    	
        },
        
        
	},
    mounted () {
    	this.getLoggedUser();
    	this.getAllOrders();
		},
		
});