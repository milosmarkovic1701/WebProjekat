Vue.component("cart", {
	data: function () {
		    return {
				
		    }
	},
	template:`
<div>
	<div class="container-fluid my-container justify-content-between" style="margin-top: 2mm;">
        <div class="card mb-3" style="width: 100%;">
            <div class="row g-0">
                <div class="col-sm-3">
                  <button type="button" style="margin-top: 10mm;" class="btn btn-lg btn-danger">Artikli restorana</button>
                </div>
                <div class="col-sm-3" style="font-size: large;">
                <div class="col">
                    <div>
                        <p class="card-title" style="color: #dc3545; font-size: xx-large; margin-top: 10mm; margin-right: 20mm;">Korpa</p>
                    </div>
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
                    <input type="number" onchange="myFunction()" id="quantity" v-model="fi.amount" name="quantity" value="1" min="1" max="10" step="1" style="background-color: #eae7dc; border-radius: 10%; border-color: #dc3545;">
                    <button style="margin-left: 28mm;" type="button" class="btn btn-danger">Obriši</button>
                </div>
            </div>
          </div>
          </div>
        </div>
      </div>
</div>  
`
	, 
	methods : {
	},
	mounted () {

    },
});