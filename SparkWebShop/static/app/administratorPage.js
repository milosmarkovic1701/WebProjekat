Vue.component("administrator-page", {
	data: function () {
		    return {
				restaurantSearchQuery : {name: "", type: "", location: "", rating: "", filterType: "", filterStatus: "", sort: ""},
				usersSearchQuery : {username: "", name: "", lastname: "", filterRole: "", filterType: "", sort: ""},
				restaurantLogo : "",
				location : {latitude: 0, longitude: 0},
				employee : {name: "", lastname: "", username: "", password: "", role: "", birthDate: ""},
				restaurants : null,
				adminComments : null,
				users : null,
				spamUsers: null,
				managers: null,
				restaurantId: "",
				userId: "",
				spamUserId: "",
		    }
	},
	
	template:`
  <div>
  <div id="tabs" class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
      <img src="images/ponesilogo.png" alt="mdo" width="120" height="38" >
      <ul class="nav col-12 col-lg-auto me-lg-auto justify-content-center mb-md-0">
        <ul class="nav nav-tabs" id="myTab" role="tablist">
          <li class="nav-item" role="presentation">
            <button class="nav-link active" id="restaurants-tab" data-bs-toggle="tab" data-bs-target="#restaurants" type="button" role="tab" aria-controls="restaurant" aria-selected="true">Svi restorani</button>
          </li>
          <li class="nav-item" role="presentation">
            <button class="nav-link" id="addrestaurant-tab" data-bs-toggle="tab" data-bs-target="#addrestaurant" type="button" role="tab" aria-controls="addrestaurant" aria-selected="false">Dodaj restoran</button>
          </li>
          <li class="nav-item" role="presentation">
            <button class="nav-link" id="addemployee-tab" data-bs-toggle="tab" data-bs-target="#addemployee" type="button" role="tab" aria-controls="addemployee" aria-selected="false">Dodaj zaposlenog</button>
          </li>
          <li class="nav-item" role="presentation">
            <button class="nav-link" id="comments-tab" data-bs-toggle="tab" data-bs-target="#comments" type="button" role="tab" aria-controls="comments" aria-selected="false">Komentari</button>
          </li>
          <li class="nav-item" role="presentation">
            <button class="nav-link" id="users-tab" data-bs-toggle="tab" data-bs-target="#users" type="button" role="tab" aria-controls="users" aria-selected="false">Korisnici</button>
          </li>
          <li class="nav-item" role="presentation">
            <button class="nav-link" id="spam-tab" data-bs-toggle="tab" data-bs-target="#spam" type="button" role="tab" aria-controls="spam" aria-selected="false">Spam</button>
          </li>
          <li class="nav-item" role="presentation" style="margin-left: 125mm;">
            <button class="nav-link" id="myinfo-tab" data-bs-toggle="tab" data-bs-target="#myinfo" type="button" role="tab" aria-controls="myinfo" aria-selected="false">Moji podaci
              <img src="icons/administrator.png" alt="mdo" width="24" height="24" class="rounded-circle">
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
			        <input type="text" v-model="restaurantSearchQuery.rating" class="form-control" placeholder="Ocena">
			      </div>
			      <div class="col col-sm-2">
			        <select class="form-select" v-model="restaurantSearchQuery.filterType" id="inputGroupSelect04">
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
	  
	  <div class="container-fluid" style="margin-top: 15mm; margin-left: 5mm;">
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
	          </ul>
	          <div class="card-body">
	            {{restaurant.rating}}/5
	          </div>
	        </div>
	      </div>
	    </div>
	  </div>
	</div>

    <div class="tab-pane fade" id="addrestaurant" role="tabpanel" aria-labelledby="addrestaurant-tab">
      <div class="container-fluid my-container">
        <div class="row my-row  justify-content-around">
          <div class="col-sm-4 my-col">
              <label class="input-group-text" for="inputGroupFile01">Izbor logoa restorana:</label>
              <input id="uploadImage" name="myPhoto" onchange="PreviewImage();" type="file" accept="image/png, image/jpeg" class="form-control">
          </div>
          <div class="col-sm-2 my-col"></div>
          <div class="col-sm-6 my-col">
              <span class="input-group-text" id="basic-addon1">Ime restorana:</span>
              <input type="text" class="form-control" placeholder="Unesite ime..." aria-label="type" aria-describedby="basic-addon1">
            </div>
          </div>
        <div class="row my-row justify-content-around">
          <div class="col-sm-4 my-col">
            <img id="uploadPreview" style="width: 270px; height: 200px;" />
          </div>
          <div class="col-sm-2 my-col"></div>
          <div class="col-sm-6 my-col" style="height: 100px; margin-top: 15mm;">
            <span class="input-group-text" id="basic-addon1">Tip restorana:</span>
            <input type="text" class="form-control" placeholder="Unesite tip..." aria-label="type" aria-describedby="basic-addon1">
          </div>
        </div>
        <div class="row my-row  justify-content-around">
          <div class="col-sm-4 my-col" style="margin-top: 2mm;">
          <div id="dvMap" style="width: 600px; height: 370px"></div>
            </div>
          <div class="col-sm-2 my-col"></div>
          <div class="col-sm-6 my-col">
            <div container>
             <div class="row my-row justify-content-around">
                <div class="col-sm-4 my-col">
                  <span class="input-group-text" id="basic-addon1">Ulica:</span>
                  <input type="text" class="form-control" placeholder="Unesite ulicu..." aria-label="type" aria-describedby="basic-addon1">
                </div>
                <div class="col-sm-4 my-col">
                  <span class="input-group-text" id="basic-addon1">Broj:</span>
                  <input type="text" class="form-control" placeholder="Unesite broj..." aria-label="type" aria-describedby="basic-addon1">
                </div>
                <div class="col-sm-4 my-col">
                  <span class="input-group-text" id="basic-addon1">Mesto:</span>
                  <input type="text" class="form-control" placeholder="Unesite mesto..." aria-label="type" aria-describedby="basic-addon1">
                </div>
              </div>
              <div class="row my-row justify-content-around">
                <div class="col-sm-4 my-col">
                  <span class="input-group-text" id="basic-addon1">Geografska širina:</span>
                  <input type="text" id="latitude" class="form-control" placeholder="Nadjite restoran na mapi" aria-label="type" aria-describedby="basic-addon1">
                </div>
                <div class="col-sm-4 my-col">
                  <span class="input-group-text" id="basic-addon1">Geografska dužina:</span>
                  <input type="text" id="longitude" class="form-control" placeholder="Nadjite restoran na mapi" aria-label="type" aria-describedby="basic-addon1">
                </div>
                <div class="col-sm-4 my-col">
                  <span class="input-group-text" id="basic-addon1">Poštanski broj:</span>
                  <input type="text" class="form-control" placeholder="Unesite poštanski broj..." aria-label="type" aria-describedby="basic-addon1">
                </div>
              </div>
            <div class="container" style="margin-top: 5%;">
            <span class="input-group-text sm-4" id="basic-addon1">Menadžer:</span>
              <select class="form-select" id="inputGroupSelect04" aria-label="Example select with button addon">
                <option v-for="manager in managers" value="manager.user.id">{{manager.user.fullName}}</option>
              </select>
            </div>
            </div>
              <button class="btn btn-outline-danger btn-lg" style="margin-top: 7%;" type="button">Dodavanje novog menadžera</button>
              <button type="button" v-on:click="addRestaurant()" style="margin-top: 7%; margin-left: 12%;" class="btn btn-danger btn-lg">Dodaj restoran</button>
          </div>
        </div>
      </div>
      </div>

      <div class="tab-pane fade" id="addemployee" role="tabpanel" aria-labelledby="addemployee-tab">
        <div class="container-fluid my-container">
          <div class="row my-row  justify-content-around">
            <div class="col-sm-6 my-col">
                <span class="input-group-text" id="basic-addon1">Ime:</span>
                <input type="text" v-model="employee.name" class="form-control" placeholder="Unesite ime...">
                <span class="input-group-text" id="basic-addon1" style="margin-top: 5%;">Prezime:</span>
                <input type="text" v-model="employee.lastname" class="form-control" placeholder="Unesite prezime...">
                <span class="input-group-text" id="basic-addon1" style="margin-top: 5%;">Korisničko ime:</span>
                <input type="text" v-model="employee.username" class="form-control" placeholder="Unesite korisničko ime...">
                <span class="input-group-text" id="basic-addon1" style="margin-top: 5%;">Lozinka:</span>
                <input type="password" v-model="employee.password" class="form-control" placeholder="Unesite lozinku...">
                <span class="input-group-text" id="basic-addon1" style="margin-top: 5%;">Uloga:</span>
                <select class="form-select" v-model="employee.role" id="inputGroupSelect04" aria-label="Example select with button addon">
                  <option value="" disabled selected>Izaberite ulogu...</option>
                  <option value="manager">Menadžer</option>
                  <option value="deliveryman">Dostavljač</option>
                </select>
                <span class="input-group-text" id="basic-addon1" style="margin-top: 5%;">Datum rođenja:</span>
                <input type="date" v-model="employee.birthDate" class="form-control">
              </div>
              <div class="col-sm-6 my-col">
                <img src="images/new employee.jpg" height="450" width="600" style="border-style: solid; border-color: #dc3545;">
                <button type="button" v-on:click="addEmployee()" style="margin-top: 10%;" class="btn btn-danger btn-lg">Dodaj zaposlenog</button>
              </div>
            </div>
        </div>
      </div>
    
    <div class="tab-pane fade" id="users" role="tabpanel" aria-labelledby="users-tab">
      <div class="surface">
		  <div class="container-fluid">
		  	  <div class="row">
		  	  <div class="col col-sm-2" style="border-left: #dc3545; border-left-style: groove">
			        <input type="text" v-model="usersSearchQuery.username" class="form-control" placeholder="Korisničko ime">
			      </div>
			      <div class="col col-sm-1">
			        <input type="text" v-model="usersSearchQuery.name" class="form-control" placeholder="Ime">
			      </div>
			      <div class="col col-sm-1">
			        <input type="text" v-model="usersSearchQuery.lastname" class="form-control" placeholder="Prezime">
			      </div>
			      <div class="col col-sm-2">
			        <select v-model="usersSearchQuery.filterRole" class="form-select">
			          <option value="" selected>Filter (uloga)</option>
			          <option value="administrator">Administrator</option>
			          <option value="kupac">Kupac</option>
			          <option value="dostavljač">Dostavljač</option>
			          <option value="menadžer">Menadžer</option>
			        </select>
			      </div>
			      <div class="col col-sm-2">
			        <select v-model="usersSearchQuery.filterType" class="form-select">
			          <option value="" selected>Filter (tip)</option>
			          <option value="bronzani">Bronzani</option>
			          <option value="srebrni">Srebrni</option>
			          <option value="zlatni">Zlatni</option>
			        </select>
			      </div>
			      <div class="col col-sm-3">
			        <select v-model="usersSearchQuery.sort" class="form-select" id="inputGroupSelect04">
			          <option value="" selected>Tip sortiranja</option>
			          <option value="ime_rastuce">Ime (rastuće)</option>
			          <option value="ime_opadajuce">Ime (opadajuće)</option>
			          <option value="prezime_rastuce">Prezime (rastuće)</option>
			          <option value="prezime_opadajuce">Prezime (opadajuće)</option>
			          <option value="korisnicko_rastuce">Korisničko ime (rastuće)</option>
			          <option value="korisnicko_opadajuce">Korisničko ime (opadajuće)</option>
			          <option value="bodovi_rastuce">Broj bodova (rastuće)</option>
			          <option value="bodovi_opadajuce">Broj bodova (opadajuće)</option>
			        </select>
			      </div>
			      <div class="col col-sm-1" style="border-right: #dc3545; border-right-style: groove">
			        <button type="button" v-on:click="searchUsers()" class="btn btn-danger">Pretraži</button>
			      </div>
			    <div class="row"></div>
			  </div>
		  </div>
	  </div>
      <div class="container-fluid">
        <table class="table">
          <thead>
            <tr>
              <th scope="col">Korisničko ime</th>
              <th scope="col">Ime</th>
              <th scope="col">Prezime</th>
              <th scope="col">Datum rođenja</th>
              <th scope="col">Uloga</th>
              <th scope="col">Broj bodova</th>
              <th scope="col">Tip korisnika</th>
              <th scope="col">Brisanje korisnika</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="user in users">
              <td>{{user.username}}</td>
              <td>{{user.name}}</td>
              <td>{{user.lastname}}</td>
              <td>{{user.birthDate}}</td>
              <td>{{user.role}}</td>
              <td>{{user.points}}</td>
              <td>{{user.userType}}</td>
              <td><button v-if="user.role === 'Administrator'" type="button" v-on:click="deleteSelectedUser(user.id)" disabled class="btn btn-danger">Obriši</button>
              	  <button v-else type="button" v-on:click="deleteSelectedUser(user.id)" class="btn btn-danger">Obriši</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <div class="tab-pane fade" id="comments" role="tabpanel" aria-labelledby="comments-tab">
      <div class="container-fluid">
        <table class="table">
          <thead>
            <tr>
              <th scope="col">Korisničko ime</th>
              <th scope="col">Ime</th>
              <th scope="col">Prezime</th>
              <th scope="col">Restoran</th>
              <th scope="col">Sadržaj komentara</th>
              <th scope="col">Ocena</th>
              <th scope="col">Odobren</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="comment in adminComments">
              <td>{{comment.username}}</td>
              <td>{{comment.name}}</td>
              <td>{{comment.lastname}}</td>
              <td>{{comment.restaurantName}}</td>
              <td>{{comment.content}}</td>
              <td>{{comment.rating}}/5</td>
              <td>{{comment.approved}}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <div class="tab-pane fade" id="spam" role="tabpanel" aria-labelledby="spam-tab">
      <div class="container-fluid">
        <table class="table">
          <thead>
            <tr>
              <th scope="col">Korisničko ime</th>
              <th scope="col">Ime</th>
              <th scope="col">Prezime</th>
              <th scope="col">Datum rođenja</th>
              <th scope="col">Broj bodova</th>
              <th scope="col">Tip korisnika</th>
              <th scope="col">Broj otkazivanja</th>
              <th scope="col">Blokiranje korisnika</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="spamUser in spamUsers">
              <td>{{spamUser.username}}</td>
              <td>{{spamUser.name}}</td>
              <td>{{spamUser.lastname}}</td>
              <td>{{spamUser.birthDate}}</td>
              <td>{{spamUser.points}}</td>
              <td>{{spamUser.userType}}</td>
              <td>{{spamUser.cancels}}</td>
              <td>
              	<button v-if="spamUser.blocked" v-on:click="changeBlockedCustomer(spamUser.id)" type="button" class="btn btn-danger">Blokiraj</button>
              	<button v-else type="button" v-on:click="changeBlockedCustomer(spamUser.id)" class="btn btn-danger">Odblokiraj</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <div class="tab-pane fade" id="myinfo" role="tabpanel" aria-labelledby="myinfo-tab">
      <div class="container-fluid my-container">
        <div class="row my-row  justify-content-around">
          <div class="col-sm-6 my-col">
              <span class="input-group-text">Ime:</span>
              <input type="text" id="nameInput" disabled class="form-control" placeholder="Unesite ime...">
              <span class="input-group-text" style="margin-top: 10%;">Prezime:</span>
              <input type="text" id="lastnameInput" disabled class="form-control" placeholder="Unesite prezime...">
              <span class="input-group-text" style="margin-top: 10%;">Korisničko ime:</span>
              <input type="text" id="usernameInput" disabled class="form-control" placeholder="Unesite korisničko ime...">
              <span class="input-group-text" style="margin-top: 10%;">Lozinka:</span>
              <input type="password" id="passwordInput" disabled class="form-control" placeholder="Unesite lozinku...">
              <span class="input-group-text" style="margin-top: 10%;">Datum rođenja:</span>
              <input type="date" id="birthDateInput" disabled class="form-control" placeholder="Unesite datum rođenja (DD.MM.YYYY.)">
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
	
		searchRestaurant() {
		axios
			.post('rest/restaurants/searchRestaurants', this.restaurantSearchQuery)
          	.then(response => (this.restaurants = response.data))
		},
		
		searchUsers() {
		axios
			.post('rest/users/searchUsers', this.usersSearchQuery)
          	.then(response => (this.users = response.data))
		},
		
		logOut() {
			this.$router.push('/'); 
	        this.$router.go();
		},
		
		addEmployee() {
			axios
			.post('rest/users/addEmployee', this.employee)	
			.then(response => {
	                if (response.data != "Korisničko ime je zauzeto !" && response.data != "Niste popunili sve potrebne podatke !"){
						alert("Obaveštenje: " + response.data);
	                }
	                else {
	                    alert("Greška: " + response.data);
	                }
	            });
		},
		deleteSelectedUser(id){
            this.userId = id;
            axios
	            .post('rest/users/deleteSelectedUser', this.userId)
	          	.then(response => (this.users = response.data))
        },
        changeBlockedCustomer(id){
            this.spamUserId = id;
            axios
	            .post('rest/users/changeBlockedUser', this.spamUserId)
	          	.then(response => (this.spamUsers = response.data))
        },
        enableInfoEdit(){
        	document.getElementById("nameInput").disabled = false;
        	document.getElementById("lastnameInput").disabled = false;
        	document.getElementById("usernameInput").disabled = false;
        	document.getElementById("passwordInput").disabled = false;
        	document.getElementById("birthDateInput").disabled = false;
        	
        },
        saveInfoEdit(){
        	document.getElementById("nameInput").disabled = true;
        	document.getElementById("lastnameInput").disabled = true;
        	document.getElementById("usernameInput").disabled = true;
        	document.getElementById("passwordInput").disabled = true;
        	document.getElementById("birthDateInput").disabled = true;
        },
        addRestaurant() {
        	this.restaurantLogo = document.getElementById("uploadImage").value
        },
	},
    mounted () {
	    axios
	        .get('rest/restaurants/getRestaurants')
	        .then(response => (this.restaurants = response.data))
	  	axios
	  	    .get('rest/comments/getComments')
	  		.then(response => (this.adminComments = response.data))
	  	axios
	  	    .get('rest/users/getUsers')
	  		.then(response => (this.users = response.data))	
  		axios
  	    	.get('rest/users/getSpamUsers')
  			.then(response => (this.spamUsers = response.data))	
  		axios
	        .get('rest/users/getManagers')
	        .then(response => (this.managers = response.data))
	  		
	      var mapOptions = {
                center: new google.maps.LatLng(45.2450573,19.8390942),
                zoom: 13,
                mapTypeId: google.maps.MapTypeId.SATELITE
            };
            var infoWindow = new google.maps.InfoWindow();
            var latlngbounds = new google.maps.LatLngBounds();
            var map = new google.maps.Map(document.getElementById("dvMap"), mapOptions);
            google.maps.event.addListener(map, 'click', function (e) {
                document.getElementById("latitude").value = e.latLng.lat();
                document.getElementById("longitude").value = e.latLng.lng();
            });
		},
});