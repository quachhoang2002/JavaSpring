const MANUFACTURE_URL = API_ADMIN_URL + '/manufacture'

function RenderManufactureTemplate() {
    return (`
            <main>
                  <div class="container-fluid px-4">
                      <h1 class="mt-4">Manufacture</h1>
                      <div class="card mb-4">
                          <div class="card-body">
                 
                          </div>
                      </div>
                      <div class="card mb-4">
                          <div class="card-header">
                              <i class="fas fa-table me-1"></i>
                                 DataTable Example
                                 
                             <button type="button" class="btn btn-primary float-end" data-bs-toggle="modal"
                                 data-bs-target="#addManu"
                               >
                                 Add Manufacture
                             </button>
                          </div>
                          <div class="card-body">
                              <table id="content" class="table table-bordered">
                                  <thead>
                                   <tr>
                                       <th>ID</th>
                                       <th>Name</th>
                                       <th>Address</th>
                                       <th>Phone</th>
                                       <th>Image</th>
                                       <th>Created At</th>
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
            <div class="modal fade" id="addManu" tabindex="-1" aria-labelledby="addManuLabel" aria-hidden="true">
              <div class="modal-dialog">
                 <div class="modal-content">
                     <!-- Modal Header -->
                     <div class="modal-header">
                         <h5 class="modal-title" id="addManuLabel">Add Manufacturer</h5>
                         <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                     </div>
                     <!-- Modal Body -->
                     <div class="modal-body">
                         <form id="manufacturerForm">
                             <div class="mb-3">
                                    <input type="text" class="form-control" id="manu_id" name="manu_id" hidden> 
                             </div>
                             <div class="mb-3">
                                 <label for="name" class="form-label">Name</label>
                                 <input type="text" class="form-control" id="name" name="name" required>
                             </div>
                             <div class="mb-3">
                                 <label for="address" class="form-label">Address</label>
                                 <input type="text" class="form-control" id="address" name="address" required>
                             </div>
                             <div class="mb-3">
                                 <label for="phone" class="form-label">Phone</label>
                                 <input type="text" class="form-control" id="phone" name="phone" required>
                             </div>
                             
                              <div class="mb-3">                           
                                <label for="formFile" class="form-label">Upload Image</label>
                                <input class="form-control" type="file" id="formFile">                      
                            </div>
                         </form>
                     </div>
                     <!-- Modal Footer -->
                     <div class="modal-footer">
                         <button type="button" class="btn btn-secondary" id="closeBtn" data-bs-dismiss="modal" >Close</button>
                         <button type="button" class="btn btn-primary" onclick="addManu()">Submit</button>
                     </div>
            </div>
        </div>
    </div>
    
           <!-- Modal edit -->
              <div class="modal fade" id="editManu" tabindex="-1" aria-labelledby="editManuLabel" aria-hidden="true">
              <div class="modal-dialog">
                 <div class="modal-content">
                     <!-- Modal Header -->
                     <div class="modal-header">
                         <h5 class="modal-title" id="editManuLabel">Edit Manufacturer</h5>
                         <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                     </div>
                     <!-- Modal Body -->
                     <div class="modal-body">
                         <form id="editManufacturerForm">
                             <div class="mb-3">
                                 <label for="name" class="form-label">Name</label>
                                 <input type="text" class="form-control" id="name" name="name" required>
                             </div>
                             <div class="mb-3">
                                 <label for="address" class="form-label">Address</label>
                                 <input type="text" class="form-control" id="address" name="address" required>
                             </div>
                             <div class="mb-3">
                                 <label for="phone" class="form-label">Phone</label>
                                 <input type="text" class="form-control" id="phone" name="phone" required>
                             </div>
                             <div>
                             
                              <div class="mb-3">                           
                                <label for="formFile" class="form-label">Upload Image</label>
                                <input class="form-control" type="file" id="formFile">                      
                            </div>
                            
                         </form>
                     </div>
                     <!-- Modal Footer  have render in js -->
                     <div class="modal-footer">
                     </div>
            </div>
        </div>
    </div>


           <div class="notifications"></div>

                        
        `)
}


async function renderManufactureItems() {

    let successCallback = (response) => {
        const tableBody = document.querySelector("#content tbody");
        tableBody.innerHTML = ''; // Clear existing rows

        const editBtn = (id) => {
            return `<button type="button" class="btn btn-primary btn-sm" data-bs-toggle="modal" data-bs-target="#editManu" onclick="editForm(${id})">Edit</button>`
        }

        const deleteBtn = (id) => {
            return `<button type="button" class="btn btn-danger btn-sm" onclick="deleteItem('${MANUFACTURE_URL}/${id}')">Delete</button>`
        }

        response.data.forEach(item => {
            const row = tableBody.insertRow();

            row.innerHTML = `
                            <td>${item.id}</td>
                            <td>${item.name}</td>
                            <td>${item.address}</td>
                            <td>${item.phone}</td>
                            <td>
                                <img src="${item.imagePath}" alt="Image" width="100" height="100">
                            </td>
                            <td>${item.createdAt || ''}</td>
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
            urlPage = `?manufacture&page=${i}&limit=${LIMIT}`
            pagination.innerHTML += `<a href="${urlPage}" class="btn btn-secondary ${i == page ? 'active' : ''}">${i}</a>`
        }
    }

    //get page from url
    let url = new URL(window.location.href);
    let page = url.searchParams.get("page") ?? 1;
    let limit = url.searchParams.get("limit") ?? LIMIT;

    let errorCallback = (error) => {
    }


    GET_URL = `${MANUFACTURE_URL}?page=${page}&size=${limit}`;
    console.log(GET_URL);

    await getDataFromApi(GET_URL, successCallback, errorCallback);
}

async function addManu() {
    let successCallback = (response) => {
        alert(response.message);
        // const closeBtn = document.querySelector("#closeBtn");
        // closeBtn.click();
        bootstrap.Modal.getInstance(document.querySelector('#addManu')).hide();
        //get current page
        const currentPage = document.querySelector(".btn-secondary.active").innerText;
        renderManufactureItems();
    }

    let errorCallback = (error) => {
        alert(error.message);
    }

    // const form = document.querySelector("#manufacturerForm");
    //
    // const data = {
    //     name: form.name.value,
    //     address: form.address.value,
    //     phone: form.phone.value
    // }
    const form = document.querySelector("#manufacturerForm");


    const formData = new FormData();
    formData.append('name', form.name.value);
    formData.append('address', form.address.value);
    formData.append('phone', form.phone.value);

    const imageInput = document.querySelector("#formFile");

    if (imageInput.files.length > 0) {
        // Append the selected image to the FormData
        formData.append('image', imageInput.files[0]);
    }

    await postDataToApi(MANUFACTURE_URL, formData, successCallback, errorCallback);

}

async function deleteItem(url) {
    let successCallback = (response) => {
        alert(response.message);
        renderManufactureItems();
    }

    let errorCallback = (error) => {
        alert(error.message);
    }

    await deleteDataFromApi(url, successCallback, errorCallback);
}

async function edit(id) {
    const errorCallback = (error) => {
        console.log(error);
        alert(error);
    }
    const successCallback = async (response) => {
        alert(response.message);
        bootstrap.Modal.getInstance(document.querySelector("#editManu")).hide();
        await renderManufactureItems();
    }

    editUrl = `${MANUFACTURE_URL}/${id}`;

    const form = document.querySelector("#editManufacturerForm");
    const formData = new FormData();
    formData.append('name', form.name.value);
    formData.append('address', form.address.value);
    formData.append('phone', form.phone.value);
    const imageInput = form.querySelector("#formFile");
    if (imageInput.files.length > 0) {
        // Append the selected image to the FormData
        formData.append('image', imageInput.files[0]);
    }


    await putDataToApi(editUrl, formData, successCallback, errorCallback);
    //remove event listener
}

//get detail
async function editForm(id) {
    let successCallback = (response) => {
        console.log(response);
        const form = document.querySelector("#editManufacturerForm");
        form.name.value = response.data.name;
        form.address.value = response.data.address;
        form.phone.value = response.data.phone;
        //render button in footer
        const footer = document.querySelector("#editManu .modal-footer");
        footer.innerHTML = `<button type="button" id="closeBtn" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>`
        footer.innerHTML += ` <button type="button" class="btn btn-primary" onclick="edit(${response.data.id})">Submit</button>`
    }

    let errorCallback = (error) => {
        alert(error.message);
    }
    editUrl = `${MANUFACTURE_URL}/${id}`;

    await getDataFromApi(editUrl, successCallback, errorCallback);
}


function renderFilterBox() {
    // Create the main container div
    filter = document.querySelector("#filter");
    const filterContainer = document.createElement("div");
    filterContainer.className = "filter-container";
    filterContainer.style.width = "100%";
    filterContainer.style.marginBottom = "10px";

    // Create and append labels and select elements
    const labels = ["Type", "Date", "Sort", "Country"];
    labels.forEach(labelText => {
        const label = document.createElement("label");
        label.textContent = labelText + ":";
        select = document.createElement("select");
        renderOptionCategory(select);

        filterContainer.appendChild(label);
        filterContainer.appendChild(select);
    });

    // Create the Price range input and output elements
    const priceLabel = document.createElement("label");
    priceLabel.textContent = "Price:";
    const priceInput = document.createElement("input");
    priceInput.type = "range";
    priceInput.min = "1";
    priceInput.max = "100";
    priceInput.value = "0";
    priceInput.className = "slider";
    priceInput.id = "myRange";
    const priceOutput = document.createElement("output");
    priceOutput.textContent = "0";

    priceInput.addEventListener("input", function () {
        priceOutput.textContent = this.value;
    });

    filterContainer.appendChild(priceLabel);
    filterContainer.appendChild(priceInput);
    filterContainer.appendChild(priceOutput);

    filter.appendChild(filterContainer);
}

