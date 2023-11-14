const CATEGORY_URL = API_ADMIN_URL + '/category'

function RenderCategoryTemplate() {
    return (`
            <main>
                  <div class="container-fluid px-4">
                      <h1 class="mt-4">Category</h1>
                      <div class="card mb-4">
                          <div class="card-body">
                 
                          </div>
                      </div>
                      <div class="card mb-4">
                          <div class="card-header">
                              <i class="fas fa-table me-1"></i>
                                 DataTable Example
                                 
<!--                          filter box here-->
                            <div id ="filter" class="float-start">
                              </div>
                                 
                             <button type="button" class="btn btn-primary float-end" data-bs-toggle="modal"
                                 data-bs-target="#addCategory"
                               >
                                Add Category
                             </button>
                          </div>
                          <div class="card-body">
                              <table id="content" class="table table-bordered">
                                  <thead>
                                   <tr>
                                       <th>ID</th>
                                       <th>Name</th>
                                       <th>Description</th>
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
            <div class="modal fade" id="addCategory" tabindex="-1" aria-labelledby="addLabel" aria-hidden="true">
              <div class="modal-dialog">
                 <div class="modal-content">
                     <!-- Modal Header -->
                     <div class="modal-header">
                         <h5 class="modal-title" id="addLabel">Add Category</h5>
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
                         <button type="button" class="btn btn-primary" onclick="addCategory()">Submit</button>
                     </div>
            </div>
        </div>
    </div>
    
           <!-- Modal editCategory -->
              <div class="modal fade" id="editCategory" tabindex="-1" aria-labelledby="editLabel" aria-hidden="true">
              <div class="modal-dialog">
                 <div class="modal-content">
                     <!-- Modal Header -->
                     <div class="modal-header">
                         <h5 class="modal-title" id="editLabel">editCategory Category</h5>
                         <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                     </div>
                     <!-- Modal Body -->
                     <div class="modal-body">
                         <form id="editCategoryForm">
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
    </div>\

                        
        `)
}


async function renderCategoryItems() {
    let url = new URL(window.location.href);
    let page = url.searchParams.get("page") ?? 1;
    let limit = url.searchParams.get("limit") ?? LIMIT;
    GET_URL = `${CATEGORY_URL}?page=${page}&size=${limit}`;

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

    let successCallback = (response) => {
        const tableBody = document.querySelector("#content tbody");
        tableBody.innerHTML = ''; // Clear existing rows

        const editBtn = (id) => {
            return `<button type="button" class="btn btn-primary btn-sm" data-bs-toggle="modal" data-bs-target="#editCategory" onclick="editCategoryForm(${id})">editCategory</button>`
        }

        const deleteBtn = (id) => {
            return `<button type="button" class="btn btn-danger btn-sm" onclick="deleteCategory('${CATEGORY_URL}/${id}')">Delete</button>`
        }

        response.data.forEach(item => {
            const row = tableBody.insertRow();

            row.innerHTML = `
                            <td>${item.id}</td>
                            <td>${item.name}</td>
                            <td>${item.description}</td>
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
            urlPage =  `?category&page=${i}&limit=${LIMIT}`
            pagination.innerHTML += `<a href="${urlPage}" class="btn btn-secondary ${i == page ? 'active' : ''}">${i}</a>`
        }
    }

    let errorCallback = (error) => {
    }


    await getDataFromApi(GET_URL, successCallback, errorCallback);
}

async function addCategory() {
    let successCallback = (response) => {
        // const closeBtn = document.querySelector("#closeBtn");
        // closeBtn.click();
        bootstrap.Modal.getInstance(document.querySelector('#addCategory')).hide();
        //get current page
        renderCategoryItems();
    }

    let errorCallback = (error) => {
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

    await postDataToApi(CATEGORY_URL, formData, successCallback, errorCallback);
}

async function deleteCategory(url) {
    let successCallback = (response) => {
        window.location.reload()
    }

    let errorCallback = (error) => {
    }

    await deleteDataFromApi(url, successCallback, errorCallback);
}

async function editCategory(id) {
    const errorCallback = (error) => {
        console.log(error);
    }
    const successCallback = async (response) => {
        bootstrap.Modal.getInstance(document.querySelector("#editCategory")).hide();
        window.location.reload()
    }

    editUrl = `${CATEGORY_URL}/${id}`;

    const form = document.querySelector("#editCategoryForm");
    const formData = new FormData();
    formData.append('name', form.name.value);
    formData.append('description', form.description.value);

    await putDataToApi(editUrl, formData, successCallback, errorCallback);
    //remove event listener
}

//get detail
async function editCategoryForm(id) {
    let successCallback = (response) => {
        console.log(response);
        const form = document.querySelector("#editCategoryForm");
        form.name.value = response.data.name;
        form.description.value = response.data.description;
        //render button in footer
        const footer = document.querySelector("#editCategory .modal-footer");
        footer.innerHTML = `<button type="button" id="closeBtn" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>`
        footer.innerHTML += ` <button type="button" class="btn btn-primary" onclick="editCategory(${response.data.id})">Submit</button>`
    }

    let errorCallback = (error) => {
    }
    editUrl = `${CATEGORY_URL}/${id}`;

    await getDataFromApi(editUrl, successCallback, errorCallback);
}

