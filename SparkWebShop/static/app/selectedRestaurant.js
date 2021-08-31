Vue.component("selected-restaurant", {
	data: function () {
		    return {
				foodItems : null,
				comments : null,
				restaurant : {},
				restaurantId: "",
		    }
	},
	template:`
<div>
<div class="container-fluid my-container justify-content-between" style="margin-top: 1%;">
        <div class="card mb-3" style="width: 100%;">
          <div class="row g-0">
            <div class="col-md-4">
              <img src="restaurant logos/gyros master.jpg" class="img-fluid rounded-start" width="400" height="550">
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
          <div class="col" v-for="fi in foodItems">
            <div class="card" style="width: 21rem;">
              <img  src="food images/pizza.png" width="300" height="200" class="card-img-top" alt="...">
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
                    <input type="number" id="quantity" v-model="fi.amount" value="1" name="quantity" min="1" max="10" step="1" style="background-color: #eae7dc; border-radius: 10%; border-color: #dc3545;">
                    <button style="margin-left: 13mm;" type="button" v-on:click="doit()" class="btn btn-danger">Dodaj u korpu</button>
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
	},
	mounted () {
		this.getRestaurant();
        this.getFoodItems();
        this.getRestaurantComments();
    },
});