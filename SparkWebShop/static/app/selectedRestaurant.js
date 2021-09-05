Vue.component("selected-restaurant", {
	data: function () {
		    return {
				foodItems : null,
				comments : null,
				restaurant : {},
				restaurantId: "",
				cartItems: null,
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
                      <li class="list-group-item">Cena: 1000.00 din.</li>
                      <li class="list-group-item">Popust: 3%</li>
                      <li class="list-group-item">Cena sa popustom: 970.00 din.</li>
                    </ul>
                </div>
                <div class="col-sm-3">
                    <button type="button" style="margin-top: 10mm;" class="btn btn-lg btn-danger">Poruči hranu</button>
                </div>
            </div>
        </div>
    </div>
    <div class="container-fluid my-container justify-content-between" style="margin-top: 2mm;">
        <div class="row row-cols-1 row-cols-md-4 g-4">
          <div class="col" v-for="fi in foodItems">
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
                <div class="container-fluid my-container justify-content-around">
                    <input type="number" id="quantity" v-model="fi.amount" name="quantity" min="1" max="10" step="1" style="background-color: #eae7dc; border-radius: 10%; border-color: #dc3545;">
                    <button style="margin-left: 28mm;" type="button" class="btn btn-danger">Obriši</button>
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
                    <h2 class="card-title" style="color: #dc3545;">{{this.restaurant.name}}</h2>
                  </div>
                  <div class="row">
                    <div class="col">
                      <ul class="list-group list-group-flush">
                        <li class="list-group-item">Tip restorana:</li>
                        <li class="list-group-item">Adresa:</li>
                        <li class="list-group-item">Status:</li>
                        <li class="list-group-item">Prosečna ocena:</li>
                      </ul>
                    </div>
                    <div class="col">
                      <ul class="list-group list-group-flush">
                        <li class="list-group-item">{{this.restaurant.type}}</li>
                        <li class="list-group-item">{{this.restaurant.location.street}} {{this.restaurant.location.number}}, {{this.restaurant.location.city}}</li>
                        <li v-if="restaurant.status === 'OPENED'" class="list-group-item">OTVORENO</li>
                    <li v-else class="list-group-item">ZATVORENO</li>
                        <li class="list-group-item">{{this.restaurant.rating}}/5</li>
                      </ul>
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
                            <li class="list-group-item">Cena: 1000.00 din.</li>
                            <li class="list-group-item">Popust: 3%</li>
                            <li class="list-group-item">Cena sa popustom: 970.00 din.</li>
                          </ul>
                </div>
                <div class="col-sm-4">
                  <button type="button" style="margin-top: 10mm;" class="btn btn-danger btn-lg" data-bs-toggle="modal" data-bs-target="#cart">
                    Korpa
                  </button>
                </div>
              </div>
            <div class="container-fluid my-container justify-content-between" style="margin-top: 5mm;">
              <div class="row row-cols-1 row-cols-md-4 g-4">
                <div class="col" v-for="fi in foodItems">
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
                      <div class="container-fluid my-container justify-content-around">
                          <input type="number" id="quantity" v-model="fi.amount" name="quantity" min="1" max="10" step="1" style="background-color: #eae7dc; border-radius: 10%; border-color: #dc3545;">
                          <button style="margin-left: 13mm;" type="button" v-on:click="addToCart(fi.id)" class="btn btn-danger">Dodaj u korpu</button>
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
	            .then(response => (this.comments = response.data))
        },
        getRestaurant(){
			axios
            	.get("/rest/restaurants/getSelectedRestaurant/" + this.$route.query.id)
	            .then(response => (this.restaurant = response.data))
        },
        addToCart() {
        	
        }
	},
	mounted () {
		this.getRestaurant();
        this.getFoodItems();
        this.getRestaurantComments();
    },
});