Vue.component("comment-page", {
	data: function () {
		    return {
				order: {},
				comment: {customerId: "", restaurantId: "", orderId: "", rating: "", content: ""},
				restaurantImage: "",
				restaurant: {},
		    }
	},
	template:`
<div>
<div class="container-fluid my-container justify-content-between" style="margin-top: 1%;">
        <div class="card mb-3" style="width: 100%;">
          <div class="row g-0">
            <div class="col-md-4">
              <img v-bind:src="restaurant.logo" class="img-fluid rounded-start" width="400" height="550">
            </div>
            <div class="col-md-8">
              <div class="card-body">
                <h2 class="card-title" style="color: #dc3545;">{{this.order.restaurantName}}</h2>
              </div>
              <div class="row">
                <div class="col">
                  <ul class="list-group list-group-flush">
                    <li class="list-group-item">Cena:</li>
                    <li class="list-group-item">Datum:</li>
                    <li class="list-group-item">Artikli:</li>
                  </ul>
                </div>
                <div class="col">
                  <ul class="list-group list-group-flush">
                    <li class="list-group-item">{{this.order.price}} din.</li>
                    <li class="list-group-item">{{this.order.dateInfo}}</li>
                    <li class="list-group-item">{{this.order.orderInfo}}</li>
                  </ul>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="container-fluid my-container justify-content-between" style="margin-top: 14mm;">
        <div class="row">
            <div class="form-group">
                <span class="input-group-text" id="basic-addon1">Komentar:</span>
                <textarea class="form-control" v-model="comment.content" id="exampleFormControlTextarea1" rows="6"></textarea>
              </div>
        </div>
        <div class="row"></div>
        <div class="row"></div>
        <div class="row">
            <div class="col"></div>
            <div class="col">
                <select class="form-select col" v-model="comment.rating" aria-label="Example select with button addon">
                    <option value="" disabled selected>Izaberite ocenu...</option>
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                </select>
                </div>
                <div class="col"></div>
                <div class="col"></div>
            <div class="col">
                <button type="button" v-on:click="rateOrder()" class="btn btn-lg btn-danger">Oceni porudžbinu</button>
            </div>
            <div class="col"></div>
        </div>
        </div>
    </div>  
`
	, 
	methods : {
		rateOrder() {
			if (this.comment.rating != "" && this.comment.content.trim() != "") {
				axios
					.post('rest/comments/addComment', this.comment)	
					.then(response => {
						alert("Obaveštenje: Vaša ocena je uspešno poslata.");
		            });
		            this.$router.push('/customerPage'); 
	        		this.$router.go();
		    }
		    else {
		    	alert("Greška: Morate popuniti polje 'komentar' i odabrati ocenu.");
		    }
		},
		getRestaurant(){
			axios
            	.get("/rest/restaurants/getSelectedRestaurant/" + this.comment.restaurantId)
	            .then(response => {
	            this.restaurant = response.data;
	            this.restaurantImage = this.restaurant.logo;
	            })
        },
	},
	mounted () {
		this.order = JSON.parse(localStorage.getItem("orderToRate"));
		this.comment.restaurantId = (JSON.parse(localStorage.getItem("orderToRate"))).restaurantId;
		this.comment.customerId = (JSON.parse(localStorage.getItem("customer"))).user.id;
		this.getRestaurant();
		this.comment.orderId = (JSON.parse(localStorage.getItem("orderToRate"))).id;
    },
});