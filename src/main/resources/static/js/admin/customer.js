const CUSTOMER_URL = API_ADMIN_URL + '/customer';

function RenderCustomerTemplate() {
    return (`
            <main>
                  <div class="container-fluid px-4">
                      <h1 class="mt-4">Customer</h1>
                      <div class="card mb-4">
                          <div class="card-body">
                 
                          </div>
                      </div>
                      <div class="card mb-4">
                          <div class="card-header">
                                <div id ="filter" class="float-start">
                               </div>
                                 
                             <button type="button" class="btn btn-primary float-end" data-bs-toggle="modal"
                                 data-bs-target="#addAccount">
                                 Add Account
                             </button>
                          </div>
                          <div class="card-body">
                              <table id="content" class="table table-bordered">
                                  <thead>
                                   <tr>
                                       <th>ID</th>
                                       <th>Name</th>
                                       <th>Email</th>
                                       <th>Phone</th>
                                        <th>Status</th>
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
          <div class="modal fade" id="addAccount" tabindex="-1" aria-labelledby="addAccountLabel" aria-hidden="true">
             <div class="modal-dialog">
                <div class="modal-content">
            <!-- Modal Header -->
            <div class="modal-header">
                <h5 class="modal-title" id="addAccountLabel">Add Account</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <!-- Modal Body -->
            <div class="modal-body">
                <form id="addCustomerForm">
                    <div class="mb-3">
                        <label for="name" class="form-label">Name *</label>
                        <input type="text" class="form-control" id="name" name="name" required>
                    </div>
                    <div class="mb-3">
                        <label for="email" class="form-label">Email *</label>
                        <input type="email" class="form-control" id="email" name="email" required>
                    </div>
                    <div class="mb-3">
                        <label for="phone" class="form-label">Phone *</label>
                        <input type="text" class="form-control" id="phone" name="phone" required>
                    </div>
                    <div class="mb-3">
                          <label for="password" class="form-label">Password</label>
                            <input type="password" class="form-control" id="password" name="password" required>
                    </div>
                </form>
            </div>
            <!-- Modal Footer -->
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" onclick="addCustomerAccount()">Submit</button>
            </div>
        </div>
          </div>
         </div>

           <!-- Modal edit -->
            <div class="modal fade" id="editAccount" tabindex="-1" aria-labelledby="editAccountLabel" aria-hidden="true">
              <div class="modal-dialog">
                 <div class="modal-content">
                     <!-- Modal Header -->
                     <div class="modal-header">
                         <h5 class="modal-title" id="editAccountLabel">Edit Account</h5>
                         <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                     </div>
                     <!-- Modal Body -->
                     <div class="modal-body">
                         <form id="editAccountForm">
                                <div class="mb-3">
                                    <label for="name" class="form-label">Name *</label>                                  
                                    <input type="text" class="form-control" id="name" name="name" required>
                                </div>
                                <div class="mb-3">
                                    <label for="email" class="form-label">Email *</label>
                                    <input type="email" class="form-control" id="email" name="email" required>
                                </div>
                                <div class="mb-3">
                                    <label for="phone" class="form-label">Phone *</label>
                                    <input type="text" class="form-control" id="phone" name="phone" required>
                                </div>
                                <div class="mb-3">
                                      <label for="password" class="form-label">Password</label>
                                        <input type="password" class="form-control" id="password" name="password" required>
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

async function renderCustomerItems() {
    let url = new URL(window.location.href);
    let page = url.searchParams.get("page") ?? 1;
    let limit = url.searchParams.get("limit") ?? LIMIT;
    GET_URL = `${CUSTOMER_URL}?page=${page}&size=${limit}`;

    filterArr = [
        'name',
        'CustomerStatus',
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

        const user = JSON.parse(localStorage.getItem('admin'));


        const editBtn = (id) => {
            return `<button type="button" class="btn btn-primary btn-sm" data-bs-toggle="modal" data-bs-target="#editAccount" onclick="editCustomerForm(${id})">Edit</button>`
        }

        const deleteBtn = (id) => {
            return `<button type="button" class="btn btn-danger btn-sm" onclick="deleteCustomerItem('${id}')">Delete</button>`
        }

        response.data.forEach(item => {
            const row = tableBody.insertRow();
            //status  = 1  show is block , 0 show is none
            let status = item.status == 0 ? 'block' : 'none';

            let blockBtn = null;
            if (item.status == 0) {
                blockBtn = `<button type="button" class="btn btn-danger btn-sm" onclick="blockCustomerItem('${item.id}')">Unblock</button>`
            } else {
                blockBtn = `<button type="button" class="btn btn-success btn-sm" onclick="blockCustomerItem('${item.id}')">Block</button>`
            }


            row.innerHTML = `
                            <td>${item.id}</td>
                            <td>${item.name}</td>
                            <td>${item.email}</td>
                            <td>${item.phone}</td>
                            <td>${status}</td>
                            <td>${item.createdat || ''}</td>
                            <td>
                                ${blockBtn}
                                ${user.role === 0 ? editBtn(item.id) : ''}
                                ${user.role === 0 ? deleteBtn(item.id) : ''} 
                             </td>
                        `
        });

        // Pagination
        const pagination = document.querySelector(".card-footer");
        pagination.innerHTML = '';
        totalPage = Math.ceil(response.meta.totalItem / response.meta.limit);
        for (let i = 1; i <= totalPage; i++) {
            urlPage = `?Customer&page=${i}&limit=${LIMIT}`
            pagination.innerHTML += `<a href="${urlPage}" class="btn btn-secondary ${i == page ? 'active' : ''}">${i}</a>`
        }
    }

    let errorCallback = (error) => {
    }


    await getDataFromApi(GET_URL, successCallback, errorCallback);
}


async function addCustomerAccount() {

    if (!validateCustomerForm("#addCustomerForm")) {
        return;
    }

    let successCallback = (response) => {
        // const closeBtn = document.querySelector("#closeBtn");
        // closeBtn.click();
        bootstrap.Modal.getInstance(document.querySelector('#addAccount')).hide();
        //get current page
        window.location.reload();
    }

    let errorCallback = (error) => {
    }


    const form = document.querySelector("#addCustomerForm");
    const formData = createCustomerFormData(form);
    
    await postDataToApi(CUSTOMER_URL, formData, successCallback, errorCallback);
}

async function deleteCustomerItem(id) {
    let successCallback = (response) => {
        window.location.reload();
    }

    let errorCallback = (error) => {
    }

    let url = `${CUSTOMER_URL}/${id}`;

    await deleteDataFromApi(url, successCallback, errorCallback);
}

async function updateCustomerAccount(id) {
    if (!validateCustomerForm("#editAccountForm")) {
        return;
    }

    const errorCallback = (error) => {
        console.log(error);
    }
    const successCallback = async (response) => {
        bootstrap.Modal.getInstance(document.querySelector("#editAccount")).hide();
        window.location.reload();
    }

    editUrl = `${CUSTOMER_URL}/${id}`;

    const form = document.querySelector("#editAccountForm");
    const formData = createCustomerFormData(form);

    await putDataToApi(editUrl, formData, successCallback, errorCallback);
    //remove event listener
}

//get detail
async function editCustomerForm(id) {

    let successCallback = (response) => {
        console.log(response.data.name);
        const form = document.querySelector("#editAccountForm");
        form.name.value = response.data.name;
        form.email.value = response.data.email;
        form.phone.value = response.data.phone;
        //render button in footer
        const footer = document.querySelector("#editAccount .modal-footer");
        footer.innerHTML = `<button type="button" id="closeBtn" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>`
        footer.innerHTML += ` <button type="button" class="btn btn-primary" onclick="updateCustomerAccount(${response.data.id})">Submit</button>`
    }

    let errorCallback = (error) => {
        console.log(error);
    }
    editUrl = `${CUSTOMER_URL}/${id}`;

    await getDataFromApi(editUrl, successCallback, errorCallback);
}

function createCustomerFormData(form) {
    const data = {}
    if (form.name.value){
        data.name = form.name.value;
    }
    if (form.email.value){
        //check email is valid or no
        data.email = form.email.value;
    }

    if (form.phone.value){
        data.phone = form.phone.value;
    }

    if (form.password.value){
        data.password = form.password.value;
    }

    //to json

    return data;
}

function validateCustomerForm(formId) {
    form = document.querySelector(formId);
    if (!form.name.value) {
        showToast("Name is required", "error");
        return false;
    }

    if (!form.email.value) {
        showToast("Email is required", "error")
        return false;
    }

    if (form.email.value.indexOf('@') === -1 || form.email.value.indexOf('.') === -1) {
        showToast("Email is invalid", "error")
        return false;
    }

    if (!form.phone.value) {
        showToast("Phone is required", "error");
        return false;
    }

    if (!form.password.value) {
        showToast("Password is required", "error");
        return false;
    }


    return true;
}


async function blockCustomerItem(id) {
    let successCallback = (response) => {
        window.location.reload();
    }

    let errorCallback = (error) => {
    }

    const url = `${CUSTOMER_URL}/${id}/block`;

    await putDataToApi(url, {}, successCallback, errorCallback);
}