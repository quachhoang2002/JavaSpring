const MANUFACTURE_URL = API_ADMIN_URL + '/manufacture'

function RenderManufactureTemplate() {
    return (
        `
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
                          
                          // Pagination
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
                         </form>
                     </div>
                     <!-- Modal Footer  have render in js -->
                     <div class="modal-footer">
                     </div>
            </div>
        </div>
    </div>\

                        
        `
    )
}


async function renderManufactureItems(i = 1) {

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
                            <td>${item.createat || ''}</td>
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
            pagination.innerHTML += `<button type="button" class="btn btn-secondary btn-sm" onclick="renderManufactureItems(${i})">${i}</button>`
        }

    }

    let errorCallback = (error) => {
    }

    GET_URL = `${MANUFACTURE_URL}?page=${i}&size=${LIMIT}`;
    console.log(GET_URL);

    await getDataFromApi(GET_URL, successCallback, errorCallback);
}

async function addManu() {
    let successCallback = (response) => {
        alert(response.message);
        // const closeBtn = document.querySelector("#closeBtn");
        // closeBtn.click();
        bootstrap.Modal.getInstance(document.querySelector('#addManu')).hide();
        renderManufactureItems();
    }

    let errorCallback = (error) => {
        alert(error.message);
    }

    const form = document.querySelector("#manufacturerForm");

    const data = {
        name: form.name.value,
        address: form.address.value,
        phone: form.phone.value
    }
    await postDataToApi(MANUFACTURE_URL, data, successCallback, errorCallback);

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

    const form = document.querySelector("#editManufacturerForm");
    editUrl = `${MANUFACTURE_URL}/${id}`;
    console.log(editUrl)
    const data = {
        name: form.name.value,
        address: form.address.value,
        phone: form.phone.value
    }
    await putDataToApi(editUrl, data, successCallback, errorCallback);
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

