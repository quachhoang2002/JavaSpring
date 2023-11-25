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
<!--                          filter box here-->
                               <div id ="filter" class="float-start">
                               </div>
                                 
                             <button type="button" class="btn btn-outline-dark float-end" data-bs-toggle="modal"
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
                                 <label for="name" class="form-label">Name *</label>
                                 <input type="text" class="form-control" id="name" name="name" required>
                             </div>
                              <!-- select category -->
                             <div class="mb-3">
                                <label for="category"  class="form-label"> Category *</label>  
                                <select class="form-select" aria-label="Default select example" id="category" name="category" required>
                               </select> 
                              </div>
                            
                             <!-- select manufacture -->
                             <div class="mb-3">
                                <label for="manufacture" class="form-label"> Manufacture *</label>
                                <select class="form-select" aria-label="Default select example" id="manufacture" name="manufacture" required>
                                </select>
                              </div>
                                      
                                      
                              <div class="mb-3">
                                    <label for="price" class="form-label">Price *</label>
                                    <input type="number" class="form-control" id="price" name="price" required>
                              </div>
                              
                             <div class="mb-3">
                                 <label for="description" class="form-label">Description *</label>
                                 <input type="text" class="form-control" id="description" name="description" required>
                             </div>
                             
                              <div class="mb-3">
                                    <label for="image" class="form-label">Image *</label>
                                    <input type="file" class="form-control" id="image" name="image" required>
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
                            <!-- select category -->
                             <div class="mb-3">
                                <label for="category"  class="form-label"> Category </label>  
                                <select class="form-select" aria-label="Default select example" id="category" name="category" required>
                               </select> 
                              </div>
                            
                             <!-- select manufacture -->
                             <div class="mb-3">
                                <label for="manufacture" class="form-label"> Manufacture </label>
                                <select class="form-select" aria-label="Default select example" id="manufacture" name="manufacture" required>
                                </select>
                              </div>
                                      
                              
                              <div class="mb-3">
                                    <label for="price" class="form-label">Price</label>
                                    <input type="number" class="form-control" id="price" name="price" required>
                              </div>
                              
                             <div class="mb-3">
                                 <label for="description" class="form-label">Description</label>
                                 <input type="text" class="form-control" id="description" name="description" required>
                             </div>
                             
                             <div class="mb-3 image-preview">
                              </div>
                             
                              <div class="mb-3">
                                    <label for="image" class="form-label">Image</label>
                                    <input type="file" class="form-control" value="" id="image" name="image" required>
                              </div>
                              
                             <div class="modal-footer">
                                 <button type="button" class="btn btn-secondary" id="closeBtn" data-bs-dismiss="modal" >Close</button>
                                 <button type="button" class="btn btn-primary" onclick="editProduct()">Submit</button>
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
    //get page from url
    let url = new URL(window.location.href);
    let page = url.searchParams.get("page") ?? 1;
    let limit = url.searchParams.get("limit") ?? LIMIT;

    filterArr = [
        'name',
        'category',
        'manufacture',
        'price',
    ];
    renderFilterBox(filterArr);

    GET_URL = `${PRODUCT_URL}?page=${page}&size=${limit}`;
    filterArr.forEach(item => {
        let value = url.searchParams.get(item);
        if (value) {
            GET_URL += `&${item}=${value}`;
        }
    }, GET_URL);


    let successCallback = (response) => {
        const tableBody = document.querySelector("#content tbody");
        tableBody.innerHTML = ''; // Clear existing rows

        const editBtn = (id) => {
            return `<button type="button" class="btn btn-primary btn-sm" data-bs-toggle="modal" data-bs-target="#editProduct" onclick="editProductForm(${id})">Edit</button>`
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

        //category and manufacture in form
        const categorySelect = document.querySelector("#addForm #category");
        const manufactureSelect = document.querySelector("#addForm #manufacture");
        //click event for get option
        renderOptionCategory(categorySelect);
        renderOptionManufacture(manufactureSelect);


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

async function addProduct() {
    let successCallback = (response) => {
        // bootstrap.Modal.getInstance(document.querySelector('#addProduct')).hide();
        //get current page
        window.location.reload()
    }

    let errorCallback = (error) => {
    }

    const form = document.querySelector("#addForm");

    const formData = new FormData();
    formData.append('name', form.name.value);
    formData.append('categoryID', form.category.value);
    formData.append('manufactureID', form.manufacture.value);
    formData.append('price', form.price.value);
    formData.append('description', form.description.value);


    if (form.image.files[0]) {
        formData.append('image', form.image.files[0]);
    }

    await postDataToApi(PRODUCT_URL, formData, successCallback, errorCallback);

}

async function deleteProduct(url) {
    let successCallback = (response) => {
        window.location.reload()
    }

    let errorCallback = (error) => {
    }

    await deleteDataFromApi(url, successCallback, errorCallback);
}

async function editProduct(id) {
    const errorCallback = (error) => {
        console.log(error);
    }
    const successCallback = async (response) => {
        bootstrap.Modal.getInstance(document.querySelector("#editProduct")).hide();
        window.location.reload()
    }

    editUrl = `${PRODUCT_URL}/${id}`;

    const form = document.querySelector("#editProductForm");
    const formData = new FormData();
    formData.append('name', form.name.value);
    formData.append('categoryID', form.category.value);
    formData.append('manufactureID', form.manufacture.value);
    formData.append('price', form.price.value);
    formData.append('description', form.description.value);

    if (form.image.files[0]) {
        formData.append('image', form.image.files[0]);
    }


    await putDataToApi(editUrl, formData, successCallback, errorCallback);
    //remove event listener
}

async function editProductForm(id) {
    let successCallback = (response) => {
        const form = document.querySelector("#editProductForm");

        renderOptionCategory(form.category)
        renderOptionManufacture(form.manufacture)


        form.name.value = response.data.name;
        form.description.value = response.data.description;
        form.price.value = response.data.price;
        //write category and manufacture
        form.category.innerHTML = `<option value="${response.data.category.id}">${response.data.category.name}</option>`
        form.manufacture.innerHTML = `<option value="${response.data.manufacture.id}">${response.data.manufacture.name}</option>`

        //render image
        const prviewImage = document.querySelector("#editProductForm .image-preview");
        prviewImage.innerHTML = `<img src="${response.data.imagePath}" alt="Image" width="100" height="100">`


        form.image.addEventListener("change", (event) => {
            const file = event.target.files[0];
            const reader = new FileReader();
            reader.readAsDataURL(file);
            reader.onloadend = () => {
                image = reader.result;
                prviewImage.innerHTML = `<img src="${image}" alt="Image" width="100" height="100">`
            }
        })


        //render button in footer
        const footer = document.querySelector("#editProduct .modal-footer");
        footer.innerHTML = `<button type="button" id="closeBtn" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>`
        footer.innerHTML += ` <button type="button" class="btn btn-primary" onclick="editProduct(${response.data.id})">Submit</button>`
    }

    let errorCallback = (error) => {
    }

    editUrl = `${PRODUCT_URL}/${id}`;

    await getDataFromApi(editUrl, successCallback, errorCallback);
}




