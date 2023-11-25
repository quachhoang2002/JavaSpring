const ORDER_URL = API_ADMIN_URL + '/order'

function RenderOrderTemplate() {
    return (`
           <main>                
                <div class="container-fluid px-4">
                      <h1 class="mt-4">Order</h1>
                      <div class="card mb-4">
                          <div class="card-body">
                 
                          </div>
                      </div>
                      <div class="card mb-4">
                          <div class="card-header">
                               <div id ="filter" class="float-start">
                               </div>
                                
                          </div>
                          <div class="card-body">
                          
                              <table id="content" class="table table-bordered">
                                  <thead>
                                   <tr>
                                       <th>ID</th>
                                       <th>Customer Name</th>
                                       <th>Customer Phone</th>
                                        <th>Customer Email</th>
                                       <th>Customer Address</th>
                                       <th>Status</th>
                                       <th>Total Price
                                       <th>Created At</th>
                                       <th>Updated At</th>
                                       <th>Actions</th> 
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
                  
                  <!-- Bootstrap Modal -->
                    <div class="modal fade" id="orderDetailsModal" tabindex="-1" aria-hidden="true">
                       <div class="modal-dialog">
                         <div class="modal-content">
                           <div class="modal-header">
                             <h5 class="modal-title">Order Details</h5>
                             <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                           </div>
                           <div class="modal-body">
                             <div id="orderItemsContainer"></div>
                           </div>
                           <div class="modal-footer">
                             <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                           </div>
                         </div>
                       </div>
                     </div>
           </main> 
        `)
}

async function renderOrderItems() {
    //get page from url
    let url = new URL(window.location.href);
    let page = url.searchParams.get("page") ?? 1;
    let limit = url.searchParams.get("limit") ?? LIMIT;

    GET_URL = `${ORDER_URL}?page=${page}&size=${limit}`;
    filterArr = [
        'name',
        "status"
    ];
    renderFilterBox(filterArr);

    filterArr.forEach(item => {
        let value = url.searchParams.get(item);
        if (value) {
            GET_URL += `&${item}=${value}`;
        }
    }, GET_URL);


    let successCallback = (response) => {
        const tableBody = document.querySelector("#content tbody");
        tableBody.innerHTML = ''; // Clear existing rows

        response.data.forEach(item => {
            const row = tableBody.insertRow();

            //button render
            let button = '';
            let status = '';
            if (item.status == 0) {
                button = `<button type="button" class="btn btn-primary btn-sm" onclick="getDetail(${item.id})">Detail</button>
                           <button type="button" class="btn btn-info btn-sm" onclick="updateStatus(${item.id},1)">Aprroved</button>
                            <button type="button" class="btn btn-danger btn-sm"  onclick="updateStatus(${item.id},-1)">Cancel</button>`
                status = `<span class="badge bg-warning text-dark">Pending</span>`
            }

            if (item.status == 1) {
                button = `
                    <button type="button" class="btn btn-primary btn-sm" onclick="getDetail(${item.id})">Detail</button>
                    <button type="button" class="btn btn-danger btn-sm"  onclick="updateStatus(${item.id},-1)">Cancel</button>
                    <button type="button" class="btn btn-success btn-sm"  onclick="updateStatus(${item.id},2)">Complete</button>`
                status = `<span class="badge bg-success">Approved</span>`
            }

            if (item.status == -1) {
                button = `<button type="button" class="btn btn-primary btn-sm" onclick="getDetail(${item.id})">Detail</button>`
                status = `<span class="badge bg-danger">Cancel</span>`
            }

            if (item.status == 2) {
                button = `<button type="button" class="btn btn-primary btn-sm" onclick="getDetail(${item.id})">Detail</button>`
                status = `<span class="badge bg-danger">Complete</span>`
            }

            row.innerHTML = `
                            <td>${item.id}</td>
                            <td>${item.customerName}</td>
                            <td>${item.customerPhone}</td>
                            <td>${item.email_receive}</td>
                            <td>${item.shippingAddress}</td>
                            <td>${status}</td>
                            <td> ${item.total_price}</td>
                            <td>${item.createdAt || ''}</td>
                            <td>${item.updatedAt || ''}</td>
                            <td>
                                ${button}
                             </td>
                        `
        });

        // Pagination
        const pagination = document.querySelector(".card-footer");
        pagination.innerHTML = '';
        totalPage = Math.ceil(response.meta.totalItem / response.meta.limit);
        for (let i = 1; i <= totalPage; i++) {
            //if page empty
            if (url.searchParams.get("page") == null) {
                url.href += `&page=${i}`;
            }

            let urlPage = url.href.replace(`page=${page}`, `page=${i}`);
            pagination.innerHTML += `<a href="${urlPage}" class="btn btn-secondary ${i == page ? 'active' : ''}">${i}</a>`
        }
    }


    let errorCallback = (error) => {
    }

    await getDataFromApi(GET_URL, successCallback, errorCallback);
}

function updateStatus(id, status) {
    if (status == 1) {
        suffix = "confirm";
    } else if (status == -1) {
        suffix = "cancel";
    } else if (status == 2) {
        suffix = "complete";
    }

    let successCallback = (response) => {
        window.location.reload();
    }

    let errorCallback = (error) => {
    }

    postDataToApi(`${ORDER_URL}/${id}/${suffix}`, {}, successCallback, errorCallback);
}


function renderOrderDetailItems(orderDetails) {
    let itemsContainer = document.getElementById('orderItemsContainer');
    itemsContainer.innerHTML = ''; // Clear previous items

    orderDetails.forEach(detail => {
        let itemElement = document.createElement('div');
        itemElement.className = 'order-item';
        itemElement.innerHTML = `
        <div class="item-detail">
            <span class="item-label">Product Name:</span>
            <span class="item-value">${detail.product.name}</span>
        </div>
        <div class="item-detail">
            <span class="item-label">Quantity:</span>
            <span class="item-value">${detail.quantity}</span>
        </div>
        <div class="item-detail">
            <span class="item-label">Price:</span>
            <span class="item-value">${detail.price}</span>
        </div>
        <div class="item-detail">
            --------------------------
        </div>
    `;
        itemsContainer.appendChild(itemElement);
    });

    // Now show the modal
    modal = new bootstrap.Modal(document.getElementById('orderDetailsModal'));
    modal.show();
}

function getDetail(id) {
    let successCallback = (response) => {
        console.log(response)
        renderOrderDetailItems(response.data); // Assuming response is the order details
    }

    let errorCallback = (error) => {
        // Handle the error (e.g., show an error message)

    }

    getDataFromApi(`${ORDER_URL}/${id}/detail`, successCallback, errorCallback);
}