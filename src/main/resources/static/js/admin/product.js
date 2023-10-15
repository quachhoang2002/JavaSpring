const PRODUCT_URL = API_ADMIN_URL + '/product'

function RenderProductTemplate() {
    return (`
            <main>
                  <div class="container-fluid px-4">
                      <h1 class="mt-4">Product</h1>
                      <div class="card mb-4">
                          <div class="card-body">
                 
                          </div>
                      </div>
                      <div class="card mb-4">
                          <div class="card-header">
                              <i class="fas fa-table me-1"></i>
                                 Product
                                 
                             <button type="button" class="btn btn-primary float-end" data-bs-toggle="modal"
                                 data-bs-target="#addProduct"
                               >
                                Add Product
                             </button>
                          </div>
                          <div class="card-body">
                              <table id="content" class="table table-bordered">
                                  <thead>
                                   <tr>
                                       <th>ID</th>
                                       <th></th>
                                       <th>Name</th>
                                       <th>Category</th>
                                       <th>Brand</th>
                                       <th>Price</th>
                                       <th>Description</th>
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
                          
                          // Pagination
                            <div class="card-footer">
                            </div>
                       </div>
                  </div>
           </main>
                
            <!-- Modal -->
            <div class="modal fade" id="addProduct" tabindex="-1" aria-labelledby="addLabel" aria-hidden="true">
              <div class="modal-dialog">
                 <div class="modal-content">
                     <!-- Modal Header -->
                     <div class="modal-header">
                         <h5 class="modal-title" id="addLabel">Add Product</h5>
                         <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                     </div>
                     <!-- Modal Body -->
                     <div class="modal-body">
                         <form id="addForm">
                             <div class="mb-3">
                                 <label for="name" class="form-label">Name</label>
                                 <input type="text" class="form-control" id="name" name="name" required>
                             </div>
                             <div class="mb-3">
                                 <label for="description" class="form-label">Description</label>
                                 <input type="text" class="form-control" id="description" name="description" required>
                             </div>
                             
                         </form>
                     </div>
                     <!-- Modal Footer -->
                     <div class="modal-footer">
                         <button type="button" class="btn btn-secondary" id="closeBtn" data-bs-dismiss="modal" >Close</button>
                         <button type="button" class="btn btn-primary" onclick="addProduct()">Submit</button>
                     </div>
            </div>
        </div>
    </div>
    
           <!-- Modal editProduct -->
              <div class="modal fade" id="editProduct" tabindex="-1" aria-labelledby="editLabel" aria-hidden="true">
              <div class="modal-dialog">
                 <div class="modal-content">
                     <!-- Modal Header -->
                     <div class="modal-header">
                         <h5 class="modal-title" id="editLabel">Edit Product</h5>
                         <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                     </div>
                     <!-- Modal Body -->
                     <div class="modal-body">
                         <form id="editProductForm">
                              <div class="mb-3">
                                 <label for="name" class="form-label">Name</label>
                                 <input type="text" class="form-control" id="name" name="name" required>
                             </div>
                             <div class="mb-3">
                                 <label for="description" class="form-label">Description</label>
                                 <input type="text" class="form-control" id="description" name="description" required>
                             </div>
                            
                         </form>
                     </div>
                     <!-- Modal Footer  have render in js -->
                     <div class="modal-footer">
                     </div>
            </div>
        </div>
    </div>
              
        `)
}

async function renderProductItems() {

    let successCallback = (response) => {
        const tableBody = document.querySelector("#content tbody");
        tableBody.innerHTML = ''; // Clear existing rows

        const editBtn = (id) => {
            return `<button type="button" class="btn btn-primary btn-sm" data-bs-toggle="modal" data-bs-target="#editProduct" onclick="editProductForm(${id})">editProduct</button>`
        }

        const deleteBtn = (id) => {
            return `<button type="button" class="btn btn-danger btn-sm" onclick="deleteProduct('${PRODUCT_URL}/${id}')">Delete</button>`
        }

        response.data.forEach(item => {
            const row = tableBody.insertRow();

            row.innerHTML = `
                            <td>${item.id}</td>
                            <td>                                 
                                <img src="${item.imagePath}" alt="Image" width="100" height="100">
                            </td>
                            <td>${item.name}</td>
                            <td>${item.category.name}</td>
                            <td>${item.manufacture.name}</td>
                            <td>${item.price}</td>
                            <td>${item.description}</td>
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
            urlPage = `?product&page=${i}&limit=${LIMIT}`
            pagination.innerHTML += `<a href="${urlPage}" class="btn btn-secondary ${i == page ? 'active' : ''}">${i}</a>`
        }
    }

    //get page from url
    let url = new URL(window.location.href);
    let page = url.searchParams.get("page") ?? 1;
    let limit = url.searchParams.get("limit") ?? LIMIT;


    let errorCallback = (error) => {
    }


    GET_URL = `${PRODUCT_URL}?page=${page}&size=${limit}`;
    console.log(GET_URL);

    await getDataFromApi(GET_URL, successCallback, errorCallback);
}

async function addProduct() {
    let successCallback = (response) => {
        alert(response.message);
        // const closeBtn = document.querySelector("#closeBtn");
        // closeBtn.click();
        bootstrap.Modal.getInstance(document.querySelector('#addProduct')).hide();
        //get current page
        renderProductItems();
    }

    let errorCallback = (error) => {
        alert(error.message);
    }

    // const form = document.querySelector("#addForm");
    //
    // const data = {
    //     name: form.name.value,
    //     address: form.address.value,
    //     phone: form.phone.value
    // }
    const form = document.querySelector("#addForm");


    const formData = new FormData();
    formData.append('name', form.name.value);
    formData.append('description', form.description.value);

    await postDataToApi(PRODUCT_URL, formData, successCallback, errorCallback);

}

async function deleteProduct(url) {
    let successCallback = (response) => {
        alert(response.message);
        renderProductItems();
    }

    let errorCallback = (error) => {
        alert(error.message);
    }

    await deleteDataFromApi(url, successCallback, errorCallback);
}

async function editProduct(id) {
    const errorCallback = (error) => {
        console.log(error);
        alert(error);
    }
    const successCallback = async (response) => {
        alert(response.message);
        bootstrap.Modal.getInstance(document.querySelector("#editProduct")).hide();
        await renderProductItems();
    }

    editUrl = `${PRODUCT_URL}/${id}`;

    const form = document.querySelector("#editProductForm");
    const formData = new FormData();
    formData.append('name', form.name.value);
    formData.append('description', form.description.value);

    await putDataToApi(editUrl, formData, successCallback, errorCallback);
    //remove event listener
}

//get detail
async function editProductForm(id) {
    let successCallback = (response) => {
        console.log(response);
        const form = document.querySelector("#editProductForm");
        form.name.value = response.data.name;
        form.description.value = response.data.description;
        //render button in footer
        const footer = document.querySelector("#editProduct .modal-footer");
        footer.innerHTML = `<button type="button" id="closeBtn" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>`
        footer.innerHTML += ` <button type="button" class="btn btn-primary" onclick="editProduct(${response.data.id})">Submit</button>`
    }

    let errorCallback = (error) => {
        alert(error.message);
    }
    editUrl = `${PRODUCT_URL}/${id}`;

    await getDataFromApi(editUrl, successCallback, errorCallback);
}

