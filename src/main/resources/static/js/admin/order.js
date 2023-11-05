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
                                       <th>Total Price</th>
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
                  
           </main>
                  
              
        `)
}

async function renderOrderItems() {
    //get page from url
    let url = new URL(window.location.href);
    let page = url.searchParams.get("page") ?? 1;
    let limit = url.searchParams.get("limit") ?? LIMIT;

    GET_URL = `${ORDER_URL}?page=${page}&size=${limit}`;

    let successCallback = (response) => {
        const tableBody = document.querySelector("#content tbody");
        tableBody.innerHTML = ''; // Clear existing rows

        const editBtn = (id) => {
            return `<button type="button" class="btn btn-primary btn-sm" data-bs-toggle="modal" data-bs-target="#editProduct" onclick="editProductForm(${id})">Edit</button>`
        }

        const deleteBtn = (id) => {
            return `<button type="button" class="btn btn-danger btn-sm" onclick="deleteProduct('${ORDER_URL}/${id}')">Delete</button>`
        }

        response.data.forEach(item => {
            const row = tableBody.insertRow();

            row.innerHTML = `
                            <td>${item.id}</td>
                            <td>                                 
                                <img src="${item.imagePath}" alt="Image" width="100" height="100">
                            </td>
                            <td>${item.customer_name}</td>
                            <td>${item.cusomter_phone}</td>
                            <td>${item.email_receive}</td>
                            <td>${item.shipping_address}</td>
                            <td>${item.status}</td>
                            <td> ${item.total_price}</td>
                            <td>${item.createdAt || ''}</td>
                            <td>${item.updatedAt || ''}</td>
                            <td>
                                ${editBtn(item.id)}
                                ${deleteBtn(item.id)} 
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





