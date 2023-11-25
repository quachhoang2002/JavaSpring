const STOCK_URL = API_ADMIN_URL + '/stock'

function RenderStockTemplate() {
    return (`
            <main>
                  <div class="container-fluid px-4">
                      <h1 class="mt-4">Stock</h1>
                      <div class="card mb-4">
                          <div class="card-body">
                 
                          </div>
                      </div>
                      <div class="card mb-4">
                          <div class="card-header">
<!--                          filter box -->
                               <div id ="filter" class="float-start">
                               </div>
                                 
                             <button type="button" class="btn btn-outline-dark float-end" data-bs-toggle="modal"
                                 data-bs-target="#addStock"
                               >
                                Add Stock
                             </button>
                          </div>
                          <div class="card-body">
                              <table id="content" class="table table-bordered">
                                  <thead>
                                   <tr>
                                       <th>ID</th>
                                       <th>Product Name</th>
                                       <th>Quantity</th>
                                       <th>Update At</th>
                                   </tr>
                                   </thead>
                                   
                                   <tfoot>
                       
                                   </tfoot>
                                   
                                  <tbody>    
                                         
                                  </tbody>
                              </table>
                          </div>
                          
                            <div class="card-footer">
                            </div>
                       </div>
                  </div>
           </main>
                
            <!-- Modal -->
            <div class="modal fade" id="addStock" tabindex="-1" aria-labelledby="addLabel" aria-hidden="true">
              <div class="modal-dialog">
                 <div class="modal-content">
                     <!-- Modal Header -->
                     <div class="modal-header">
                         <h5 class="modal-title" id="addLabel">Import Product</h5>
                         <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                     </div>
                     <!-- Modal Body -->
                     <div class="modal-body">
                         <form id="addForm">
                        
                              <div class="mb-3">
                                <label for="product"  class="form-label"> Product </label>  
                                <select class="form-select" aria-label="Default select example" id="product" name="product" required>
                                </select> 
                             </div>
                             
                             <div class="mb-3">
                                 <label for="quantity" class="form-label">Quantity</label>
                                 <input type="text" class="form-control" id="quantity" name="quantity" required>
                             </div>
                             
                         </form>
                     </div>
                     <!-- Modal Footer -->
                     <div class="modal-footer">
                         <button type="button" class="btn btn-secondary" id="closeBtn" data-bs-dismiss="modal" >Close</button>
                         <button type="button" class="btn btn-primary" onclick="addStock()">Submit</button>
                     </div>
            </div>
        </div>
    </div>
    
 
                        
        `)
}


async function renderStockItems() {

    let url = new URL(window.location.href);
    let page = url.searchParams.get("page") ?? 1;
    let limit = url.searchParams.get("limit") ?? LIMIT;
    GET_URL = `${STOCK_URL}?page=${page}&size=${limit}`;

    filterArr = [
        'name',
    ];
    renderFilterBox(filterArr);
    filterArr.forEach(item => {
        let value = url.searchParams.get(item);
        if (value) {
            GET_URL += `&${item}=${value}`;
        }
    }, GET_URL);


    productSelect = document.querySelector("#product");
    renderOptionProduct(productSelect);

    let successCallback = (response) => {
        const tableBody = document.querySelector("#content tbody");
        tableBody.innerHTML = ''; // Clear existing rows
        response.data.forEach(item => {
            const row = tableBody.insertRow();

            row.innerHTML = `
                            <td>${item.id}</td>
                            <td>${item.product.name}</td>
                            <td>${item.quantity}</td>
                            <td>${item.updatedAt || ''}</td>
                        `
        });

        // Pagination
        const pagination = document.querySelector(".card-footer");
        pagination.innerHTML = '';
        totalPage = Math.ceil(response.meta.totalItem / response.meta.limit);
        for (let i = 1; i <= totalPage; i++) {
            urlPage = `?category&page=${i}&limit=${LIMIT}`
            pagination.innerHTML += `<a href="${urlPage}" class="btn btn-secondary ${i == page ? 'active' : ''}">${i}</a>`
        }
    }


    let errorCallback = (error) => {
    }



    await getDataFromApi(GET_URL, successCallback, errorCallback);
}

async function addStock() {
    let successCallback = (response) => {
        // const closeBtn = document.querySelector("#closeBtn");
        // closeBtn.click();
        bootstrap.Modal.getInstance(document.querySelector('#addStock')).hide();
        //get current page
        renderStockItems();
    }

    let errorCallback = (error) => {
        alert(error.message);
    }

    const form = document.querySelector("#addForm");

    const data = {
        product: {
            id: form.product.value
        },
        quantity: form.quantity.value

    }

    await postDataToApi(STOCK_URL, data, successCallback, errorCallback);
}


