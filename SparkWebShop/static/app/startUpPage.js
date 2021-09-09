Vue.component("startup-page", {
	data: function () {
		    return {
		    	userRegister : {usernameRegister: "", passwordRegister: "", name: "", lastname: "", birthDate: "", address: ""},
		    	userLogin : {usernameLogin: "", passwordLogin: ""},
		    	searchQuery : {name: "", type: "", location: "", rating: "", filterType: "", filterStatus: "", sort: ""},
				restaurants : null,
				roleLogin: ""
		    }
	},
	template:`
<div>
	<div id="tabs" class="d-flex flex-wrap align-items-center justify-content-center justify-content-sm-start">
	      <ul class="nav col-sm-12 col-sm-auto me-sm-auto justify-content-center mb-md-0">
      <img src="images/ponesilogo.png" alt="mdo" width="120" height="42" >
	      <ul class="nav nav-tabs" id="myTab" role="tablist">
	        <li>
	          <button type="button" disabled style="margin-left: 30mm; margin-bottom: 1mm; margin-top: 1mm;" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#exampleModalCenter">
	            Dobrodošli na početnu stranu ponesi.com ! Ako nešto želite da naručite, molimo Vas da se prijavite. Ako ste nov korisnik, molimo Vas da se registrujete. --->
	          </button>
	          </li>
	          <li>
	          <button type="button" style="margin-left: 20mm; margin-top: 1mm; margin-bottom: 1mm;" class="btn btn-light" data-bs-toggle="modal" data-bs-target="#logInModal">
	            Prijava
	          </button>
	          </li>
	          <li>
	          <button type="button" style="margin-left: 5mm; margin-bottom: 1mm; margin-top: 1mm;" class="btn btn-secondary" data-bs-toggle="modal" data-bs-target="#registerModal">
	            Registracija
	          </button>
	        </li>
	      </ul>
	    </ul>
	</div>

	<!--MODALNI PROZOR ZA PRIJAVU-->
	<div class="modal fade" id="logInModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
	  <div class="modal-dialog modal-dialog-centered" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLongTitle">Prijava</h5>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	      </div>
	      <div class="modal-body">
	        <img src="images/login.png" width="120" height="120">
	        <span class="input-group-text" id="basic-addon1" style="margin-top: 5%;">Korisničko ime:</span>
	        <input type="text" v-model="userLogin.usernameLogin" class="form-control" placeholder="Unesite korisničko ime...">
	        <span class="input-group-text" id="basic-addon1" style="margin-top: 5%;">Lozinka:</span>
	        <input type="password" v-model="userLogin.passwordLogin" class="form-control" placeholder="Unesite lozinku...">
	        <select class="form-select" v-model="roleLogin" style="margin-top: 5%;">
		          <option value="" selected>Uloga</option>
		          <option value="customer">Kupac</option>
		          <option value="deliveryman">Dostavljac</option>
		          <option value="administrator">Administrator</option>
		          <option value="manager">Menadzer</option>
		        </select>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Otkaži</button>
	        <button type="button" v-on:click="login()" class="btn btn-danger">Prijavi se</button>
	      </div>
	    </div>
	  </div>
	</div>

	<!--MODALNI PROZOR ZA REGISTRACIJU-->
	<div class="modal fade" id="registerModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
	  <div class="modal-dialog modal-dialog-centered" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLongTitle">Registracija</h5>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	      </div>
	      <div class="modal-body">
	        <span class="input-group-text" id="basic-addon1" style="margin-top: 5%;">Ime:</span>
	        <input type="text" v-model="userRegister.name" class="form-control" placeholder="Unesite ime...">
	        <span class="input-group-text" id="basic-addon1" style="margin-top: 5%;">Prezime:</span>
	        <input type="text" v-model="userRegister.lastname" class="form-control" placeholder="Unesite prezime...">
	        <span class="input-group-text" id="basic-addon1" style="margin-top: 5%;">Datum rođenja:</span>
	        <input type="date" v-model="userRegister.birthDate" class="form-control" placeholder="dd-mm-yyyy">
	        <span class="input-group-text" id="basic-addon1" style="margin-top: 5%;">Korisničko ime:</span>
	        <input type="text" v-model="userRegister.usernameRegister" class="form-control" placeholder="Unesite korisničko ime...">
	        <span class="input-group-text" id="basic-addon1" style="margin-top: 5%;">Lozinka:</span>
	        <input type="password" v-model="userRegister.passwordRegister" class="form-control" placeholder="Unesite lozinku...">
	        <span class="input-group-text" id="basic-addon1" style="margin-top: 5%;">Adresa:</span>
	        <input type="text" v-model="userRegister.address" class="form-control" placeholder="Unesite adresu (ulica, broj, mesto)">
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Otkaži</button>
	        <button type="button" v-on:click="register(userRegister)" data-bs-dismiss="modal" class="btn btn-danger">Registruj se</button>
	      </div>
	    </div>
	  </div>
	</div>
	
	<div class="container-fluid" style="margin-top: 3mm;">
		<div class="row">
		  <div class="col">
		    <img src="images/startupfood.jpg" width="100%" style="border-style: groove; border-color: #dc3545;">
		  </div>
		  <div class="col">
		    <img src="images/food_cookies_donuts.jpg" width="100%" style="border-style: groove; border-color: #dc3545;">
		  </div>
		  <div class="col">
		    <img src="images/wallpaper-burger.jpg" width="100%" style="border-style: groove; border-color: #dc3545;">
		  </div>
		  <div class="row">
		    <p style="margin-top: 2cm; font-size: 12mm;"><b>SVI RESTORANI</b></p>
		  </div>
		</div>
	</div>

  <div class="surface">
	  <div class="container-fluid">
	  	  <div class="row">
		      <div class="col col-sm-1" style="border-left: #dc3545; border-left-style: groove">
		        <input type="text" v-model="searchQuery.name" class="form-control" placeholder="Naziv">
		      </div>
		      <div class="col col-sm-1">
		        <input type="text" v-model="searchQuery.type" class="form-control" placeholder="Tip">
		      </div>
		      <div class="col col-sm-1">
		        <input type="text" v-model="searchQuery.location" class="form-control" placeholder="Lokacija">
		      </div>
		      <div class="col col-sm-1">
		        <input type="text" v-model="searchQuery.rating" class="form-control" placeholder="Ocena">
		      </div>
		      <div class="col col-sm-2">
		        <select class="form-select" v-model="searchQuery.filterType" id="inputGroupSelect04">
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
		        <select class="form-select" v-model="searchQuery.filterStatus" id="inputGroupSelect04">
		          <option value="" selected>Filter (status)</option>
		          <option value="opened">Otvoren</option>
		          <option value="closed">Zatvoren</option>
		        </select>
		      </div>
		      <div class="col col-sm-3">
		        <select class="form-select" v-model="searchQuery.sort" id="inputGroupSelect04">
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
  
  <div class="container-fluid" style="margin-top: 15mm; margin-left: 5mm;">
    <div class="row row-cols-1 row-cols-sm-4 g-4">
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
          </ul>
          <div class="card-body">
            {{restaurant.rating}}/5
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
			.post('rest/restaurants/searchRestaurants', this.searchQuery)
          	.then(response => (this.restaurants = response.data))
		},
		login() {
			if (this.roleLogin == "administrator") {
			axios
	            .post("/rest/users/loginAdministrator", this.userLogin)
	            .then(response => {
	                if (response.data != "Prijava neuspešna. Proverite korisničko ime i lozinku." && response.data != "Ovaj nalog je obrisan."){
	                    localStorage.setItem("admin", JSON.stringify(response.data));
	                    //localStorage mora da cuva parove kljuc:string i da bi se koristio korisnik kao objekat mora se pozvati JSON.parse nad dobavljenim stringom iz localStorage
	                    this.$router.push('/administratorPage'); 
	                    this.$router.go();
	                }
	                else {
	                    alert("Greška: " + response.data);
	                }
	            });
	        }
	        else if (this.roleLogin == "customer") {
			axios
	            .post("/rest/users/loginCustomer", this.userLogin)
	            .then(response => {
	                if (response.data != "Prijava neuspešna. Proverite korisničko ime i lozinku." && response.data != "Prijava neuspešna. Vaš nalog je blokiran ili obrisan."){
	                    localStorage.setItem("customer", JSON.stringify(response.data));
	                    //localStorage mora da cuva parove kljuc:string i da bi se koristio korisnik kao objekat mora se pozvati JSON.parse nad dobavljenim stringom iz localStorage
	                    this.$router.push('/customerPage'); 
	                    this.$router.go();
	                }
	                else {
	                    alert("Greška: " + response.data);
	                }
	            });
	        }
	        else if (this.roleLogin == "manager") {
			axios
	            .post("/rest/users/loginManager", this.userLogin)
	            .then(response => {
	                if (response.data != "Prijava neuspešna. Proverite korisničko ime i lozinku." && response.data != "Prijava neuspešna. Vaš nalog je blokiran ili obrisan."){
	                    localStorage.setItem("manager", JSON.stringify(response.data));
	                    //localStorage mora da cuva parove kljuc:string i da bi se koristio korisnik kao objekat mora se pozvati JSON.parse nad dobavljenim stringom iz localStorage
	                    this.$router.push('/managerPage'); 
	                    this.$router.go();
	                }
	                else {
	                    alert("Greška: " + response.data);
	                }
	            });
	        }
	        else if (this.roleLogin == "deliveryman") {
			axios
	            .post("/rest/users/loginDeliveryman", this.userLogin)
	            .then(response => {
	                if (response.data != "Prijava neuspešna. Proverite korisničko ime i lozinku." && response.data != "Prijava neuspešna. Vaš nalog je blokiran ili obrisan."){
	                    localStorage.setItem("deliveryman", JSON.stringify(response.data));
	                    //localStorage mora da cuva parove kljuc:string i da bi se koristio korisnik kao objekat mora se pozvati JSON.parse nad dobavljenim stringom iz localStorage
	                    this.$router.push('/deliveryManPage'); 
	                    this.$router.go();
	                }
	                else {
	                    alert("Greška: " + response.data);
	                }
	            });
	        }
	        else if (this.roleLogin == "") {
	        	alert("Greška: Morate odabrati ulogu za koju se prijavljujete !")
	        }
        },
		register() {
		axios
			.post('rest/users/register', this.userRegister)	
			.then(response => {
	                if (response.data != "Korisničko ime je zauzeto !" && response.data != "Niste popunili sve potrebne podatke !"){
						alert("Obaveštenje: " + response.data);
						this.userRegister.usernameRegister = "";
	                	this.userRegister.passwordRegister = "";
	                	this.userRegister.name = "";
	                	this.userRegister.lastname = "";
	                	this.userRegister.birthDate = "";
	                	this.userRegister.address = "";
	                }
	                else {
	                    alert("Greška: " + response.data);
	                }
	            });
		}
	},
	mounted () {
        axios
          .get('rest/restaurants/getRestaurants')
          .then(response => (this.restaurants = response.data))
    },
});