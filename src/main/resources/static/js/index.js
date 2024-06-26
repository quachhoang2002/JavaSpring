<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <meta
            name="viewport"
            content="width=device-width, initial-scale=1, shrink-to-fit=no"
    />
    <meta
            name="description"
            content=""
    />
    <meta
            name="author"
            content=""
    />
    <title>Shop Homepage - Start Bootstrap Template</title>
    <!-- Favicon-->
    <link
            rel="icon"
            type="image/x-icon"
            href="assets/favicon.ico"
    />
    <!-- Bootstrap icons-->
    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css"
            rel="stylesheet"
    />
    <!-- Core theme CSS (includes Bootstrap)-->
    <link
            href="css/home.css"
            rel="stylesheet"
    />
    <style>
        .slider {
            -webkit-appearance: none;
            width: 15%;
            height: 10px;
            background: #d3d3d3;
            border-radius: 10px;
            outline: none;
            opacity: 0.7;
            -webkit-transition: 0.2s;
            transition: opacity 0.2s;
        }

        .slider:hover {
            opacity: 1;
        }

        .slider::-webkit-slider-thumb {
            -webkit-appearance: none;
            appearance: none;
            width: 15px;
            height: 15px;
            background: #212529;
            border-radius: 50%;
            cursor: pointer;
        }

        .slider::-moz-range-thumb {
            width: 25px;
            height: 25px;
            background: #04aa6d;
            cursor: pointer;
        }

        .filter-container {
            display: flex;
            gap: 10px;
            align-items: center;
            > select {
                width: 5%;
                border: none;
            }
        }
        .active-page-button {
            background-color: #000; /* Màu nền nút khi được chọn */
            color: #fff; /* Màu văn bản khi được chọn */
        }

        #cart-form {
            > button > a {
                text-decoration: none;
                color: black;
                position: relative;
                width: 100%;
                height: 100%;
                padding: 10px;
            }
            > button > a:hover {
                color: white;
            }
        }
    </style>
</head>
<body>
<!-- Navigation-->
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container px-4 px-lg-5">
        <a
                class="navbar-brand"
                href="#!"
        >Start Bootstrap</a
        >
        <button
                class="navbar-toggler"
                type="button"
                data-bs-toggle="collapse"
                data-bs-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent"
                aria-expanded="false"
                aria-label="Toggle navigation"
        >
            <span class="navbar-toggler-icon"></span>
        </button>
        <div
                class="collapse navbar-collapse"
                id="navbarSupportedContent"
        >
            <ul class="navbar-nav me-auto mb-2 mb-lg-0 ms-lg-4">
                <li class="nav-item">
                    <a
                            class="nav-link active"
                            aria-current="page"
                            href="#!"
                    >Home</a
                    >
                </li>
                <li class="nav-item">
                    <a
                            class="nav-link"
                            href="#!"
                    >About</a
                    >
                </li>
                <li class="nav-item dropdown">
                    <a
                            class="nav-link dropdown-toggle"
                            id="navbarDropdown"
                            href="#"
                            role="button"
                            data-bs-toggle="dropdown"
                            aria-expanded="false"
                    >Shop</a
                    >
                    <ul
                            class="dropdown-menu"
                            aria-labelledby="navbarDropdown"
                    >
                        <li>
                            <a
                                    class="dropdown-item"
                                    href="#!"
                            >All Products</a
                            >
                        </li>
                        <li><hr class="dropdown-divider" /></li>
                        <li>
                            <a
                                    class="dropdown-item"
                                    href="#!"
                            >Popular Items</a
                            >
                        </li>
                        <li>
                            <a
                                    class="dropdown-item"
                                    href="#!"
                            >New Arrivals</a
                            >
                        </li>
                    </ul>
                </li>
            </ul>
            <div id="loginForm" style="display: flex; align-items: center; flex-direction: row">
                <button
                        id="logout-btn"
                        class="btn btn-person-dash-fill"
                        type="submit"
                        onclick="logOutEvent()"
                        style="display: none"
                >
                    <i class="bi-person-fill me-1"></i>
                    Log out
                </button>

                <button
                        id="cart-btn"
                        class="btn btn-outline-dark"
                        onclick="cartEvent()"
                        type="button"
                        style="display: none"
                >
                    <i class="bi-cart-fill me-1"></i>
                    Cart
                    <span
                            class="badge bg-dark text-white ms-1 rounded-pill"
                            id="showQuantity"
                    >0</span
                    >
                </button>

                <button
                        id="user-btn"
                        class="btn btn-outline-dark"
                        onclick="getInfo()"
                        type="button"
                        style="
                                display: flex;
                                align-items: center;
                                justify-content: center;
                                margin-left: 5px;
                                display: none;
                            "
                >
                    <i
                            class="bi bi-person-lines-fill me-1"
                            style="margin: 0"
                    ></i>
                    <span>Info</span>
                </button>

                <button
                        id="login-btn"
                        class="btn btn-outline-dark"
                        type="submit"
                >
                    <a href="/login" class="login-option">
                        <i class="bi-person-fill me-1"></i>
                        Sign in
                    </a>
                </button>
            </div>
        </div>
    </div>
</nav>
<!-- Header-->
<header class="bg-dark py-5">
    <div class="container px-4 px-lg-5 my-5">
        <div class="text-center text-white">
            <h1 class="display-4 fw-bolder">Shop in style</h1>
            <p class="lead fw-normal text-white-50 mb-0">
                With this shop hompeage template
            </p>
        </div>
    </div>
</header>
<!-- Section-->
<section class="py-5">
    <div class="container px-4 px-lg-5 mt-5">
        <div
                class="filter-container"
                style="width: 100%; margin-bottom: 10px; justify-content: space-between"
        >
            <label for="brand">Brand: </label>
            <select
                    id="brand"
                    style="border: 2px solid black; border-radius: 5%"
                    onchange="onBrandSortChange(event)"
            >
                <option value=""></option>
                <option value="ASC">A -> Z</option>
                <option value="DES">Z -> A</option>

            </select>

            <label for="date">Date: </label>
            <select id="date"  style="border: 2px solid black; border-radius: 5%" onchange="onDateSortChange(event)">
                <option value="newest">Newest</option>
                <option value="oldest">Oldest</option>
            </select>

            <label for="type">Type: </label>
            <select id="type"  style="border: 2px solid black; border-radius: 5%" onchange="onCatSortChange(event)">
                <option value=""></option>
                <option value="ASC">A -> Z</option>
                <option value="DES">Z -> A</option>
            </select>

            <label for="price">Brand: </label>
            <select
                    id="price"
                    style="border: 2px solid black; border-radius: 5%"
                    onchange="onPriceSortChange(event)"
            >
                <option value=""></option>
                <option value="price_low">low to high</option>
                <option value="price_high">high to low</option>

            </select>

            <label>Price: </label>

            <input
                    type="range"
                    min="1"
                    max="10000000"
                    value="0"
                    class="slider"
                    id="myRange"
                    oninput="this.nextElementSibling.value = this.value"
            />
            <output>0</output>
            <div class="input-group" style="width: 20%">
                <input type="text" id="home-searchBar" class="form-control" placeholder="name..." aria-label="Recipient's username" aria-describedby="basic-addon2">
                <div class="input-group-append">
                    <i class="btn btn-outline-dark bi bi-search search-btn" onclick="handleSearch()" type="button"></i>
                </div>
            </div>
        </div>
        <div
                class="row gx-4 gx-lg-5 row-cols-2 row-cols-md-3 row-cols-xl-4 justify-content-center"
                id="product-list-container"
        >
            <div class="col mb-5">
                <!--                <div class="card h-100">-->
                <!--                    &lt;!&ndash; Product image&ndash;&gt;-->
                <!--                    <img class="card-img-top" src="https://dummyimage.com/450x300/dee2e6/6c757d.jpg" alt="..." />-->
                <!--                    &lt;!&ndash; Product details&ndash;&gt;-->
                <!--                    <div class="card-body p-4">-->
                <!--                        <div class="text-center">-->
                <!--                            &lt;!&ndash; Product name&ndash;&gt;-->
                <!--                            <h5 class="fw-bolder">Fancy Product</h5>-->
                <!--                            &lt;!&ndash; Product price&ndash;&gt;-->
                <!--                            $40.00 - $80.00-->
                <!--                        </div>-->
                <!--                    </div>-->
                <!--                    &lt;!&ndash; Product actions&ndash;&gt;-->
                <!--                    <div class="card-footer p-4 pt-0 border-top-0 bg-transparent">-->
                <!--                        <div class="text-center"><a class="btn btn-outline-dark mt-auto" href="#">View options</a></div>-->
                <!--                    </div>-->
                <!--                </div>-->
            </div>
        </div>
        <div
                class="pagination-container text-center"
                id="pagination-container"
        >
            <div
                    class="button-container { disabled: !canPreviousPage }"
                    id="prev-page"
                    onclick="onPreviousPage()"
            >
                <a
                        class="page-link"
                        href="#"
                >Previous</a
                >
            </div>

            <ul class="pagination">
                <li
                        class="page-item active button-container"
                        id="page-1"
                >
                    <a
                            class="page-link"
                            href="#"
                    >1</a
                    >
                </li>
                <!-- Thêm các nút phân trang ở đây (số trang được tự động cập nhật) -->
            </ul>

            <div
                    class="button-container { disabled: !canNextPage }"
                    id="next-page"
                    onclick="onNextPage()"
            >
                <a
                        class="page-link"
                        href="#"
                >Next</a
                >
            </div>
        </div>
    </div>
</section>
<!-- Footer-->
<footer class="py-5 bg-dark">
    <div class="container">
        <p class="m-0 text-center text-white">
            Copyright &copy; Your Website 2023
        </p>
    </div>
</footer>
<!-- Bootstrap core JS-->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
<!-- Core theme JS-->
<script src="/js/scripts.js"></script>
<script src="/js/fetch.js"></script>
<script src="/js/ultils.js"></script>
<script src="/js/index.js"></script>
<script
        type="text/javascript"
        src="https://code.jquery.com/jquery-1.7.1.min.js"
></script>
<script>
    window.onload = function () {
        loadQuantity();
        fetchProducts();
        TrangChu.fetchData();
    };
    const productListContainer = document.getElementById(
        'product-list-container'
    );

    const signInButton = document.getElementById('login-btn');
    const logOutButton = document.getElementById('logout-btn');
    const cartButton = document.getElementById('cart-btn');
    const userButton = document.getElementById('user-btn');
    const userName = document.getElementById('userName');
    const cart = document.getElementById('cart');

    const searchButton = document.getElementById('search-btn');


    let products;
    function fetchProducts(page, size, sortBy, sortType, searchTerm) {
        const startIndex = (page - 1) * size;
        const endIndex = startIndex + size;

        fetch(`/api/admin/product/getAll`)
            .then((response) => response.json())
            .then((data) => {

                let productArray = data.data.slice(startIndex, endIndex);
                products = productArray;
<!--                productArray.forEach((product) => {console.log(product.name)});-->

                if(searchTerm != ""){
                   products = productArray.filter((product) => {

                        return product.name == searchTerm;
                   });
                }

                console.log(products);

                renderProducts(products);
                updatePagination();
            })
            .catch((error) => {
                console.error('Error fetching products:', error);
            });
    }




    function updateTotalProductsAndPages() {
        fetch(`/api/admin/product/getAll`)
            .then((response) => response.json())
            .then((data) => {
                let searchTerm = document.getElementById('home-searchBar').value;
                totalProducts = data.data.length;
                totalPages = calculateTotalPages(
                    totalProducts,
                    productsPerPage
                );
            })
            .catch((error) => {
                console.error('Error fetching products:', error);
            });
    }

    function populatePagination() {
        const paginationList = document.querySelector('.pagination');
        paginationList.innerHTML = '';

        for (let i = 1; i <= total; i++) {
            const pageItem = document.createElement('li');
            pageItem.classList.add('page-item');
            pageItem.id = `page-${i}`;
            const pageLink = document.createElement('a');
            pageLink.classList.add('page-link');
            pageLink.href = '#';
            pageLink.textContent = i;
            pageItem.appendChild(pageLink);
            paginationList.appendChild(pageItem);

            // Lắng nghe sự kiện khi nhấn vào trang
            pageItem.addEventListener('click', () => {
            let searchTerm = document.getElementById('home-searchBar').value;
                currentPage = i;
            });
        }
    }

    function renderProducts(products) {
        // Clear the current content of the productListContainer
        productListContainer.innerHTML = '';

        // Iterate through the list of products and render each product
        products.forEach((product) => {
            // Create a div to display product information
            const productCard = document.createElement('div');
            productCard.classList.add('col', 'mb-5');
            productCard.innerHTML = `
                    <div class="item-container card h-100">
                    <img class="card-img-top" src="${product.imagePath}" alt="Product Image" />
                    <div class="card-body p-4">
                    <div class="text-center">
                    <h5 class="fw-bolder">${product.name}</h5>
                    $${product.price.toFixed(2)}
                    </div>
                    </div>
                    <div class="card-footer p-4 pt-0 border-top-0 bg-transparent">
                    <div class "text-center">
                    <a class="btn btn-outline-dark mt-auto" href="/getItem/${
                        product.id
                    }">View options</a>
                    </div>
                    </div>
                    </div>
                    `;

            productListContainer.appendChild(productCard);
        });
    }

    updateTotalProductsAndPages();

    onSuccessfulLogin = (response) => {
        console.log(response);
        if (response.data.token) {
            console.log('Data: ' + response.data);
            localStorage.setItem('user', JSON.stringify(response.data));
            localStorage.setItem('token', response.data.token);
        }
        localStorage.setItem('user', JSON.stringify(response.data));
        logOutButton.style.display = 'block';
        signInButton.style.display = 'none';
        cartButton.style.display = 'block';
        userButton.style.display = 'block';
    };

    onFailedLogin = (error) => {
        document.getElementById('error-message').innerHTML = error;
        document.getElementById('error-message').style.display =
            'block';
        logOutButton.style.display = 'none';
        cartButton.style.display = 'none';
        signInButton.style.display = 'block';
        userButton.style.display = 'none';
    };

    const loginUrl = API_URL + '/auth/login';
    console.log(localStorage.getItem('token'));
    // check if have token in local storage
    if (localStorage.getItem('token')) {
        const data = {
            token: localStorage.getItem('token'),
        };

        onTokenLogin = (response) => {};
        postDataToApi(loginUrl, data, onSuccessfulLogin, onTokenLogin);
    } else if (localStorage.getItem('user')) {
    }

    const loginUser = async (event) => {
        event.preventDefault();
        const email = document.getElementById('loginEmail').value;
        const password = document.getElementById('loginPassword').value;
        const checkbox =
            document.getElementById('rememberPassword').checked;

        const data = {
            email: email,
            password: password,
            remember: checkbox,
        };
        console.log(data);

        postDataToApi(loginUrl, data, onSuccessfulLogin, onFailedLogin);
    };
    document
        .getElementById('loginForm')
        .addEventListener('submit', loginUser);

    function logOutEvent() {
        // Lấy token và user từ localStorage và xóa chúng
        const token = localStorage.getItem('token');
        const user = localStorage.getItem('user');
        localStorage.removeItem('token');
        localStorage.removeItem('user');

        console.log('Lay thong tin Logout');
        const logoutUrl = API_URL + '/auth/logout'; // Thay API_URL bằng địa chỉ URL thực tế của API logout
        fetch(logoutUrl, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ token: token }), // Gửi token để xác định người dùng đăng xuất
        })
            .then((response) => response.json())
            .then((data) => {
                if (JSON.stringify(data.status) == 'success') {
                    // Đăng xuất thành công, chuyển hướng đến trang đăng nhập

                } else {
                    // Xử lý lỗi đăng xuất nếu cần
                    console.error('Error logging out:', data.message);
                    // Có thể hiển thị thông báo lỗi cho người dùng
                }
            })

            .catch((error) => {
                // Xử lý lỗi đăng xuất nếu có lỗi mạng hoặc lỗi server
                console.error('Error logging out:', error);
                // Có thể hiển thị thông báo lỗi cho người dùng
            });
            window.location.reload();
    }

    function cartEvent() {
        window.location.href = '/viewCart';
    }
    function loadQuantity() {
        fetch('https://t.hoangdeptrai.online/api/cart/getAll')
            .then((response) => {
                if (!response.ok) {
                    throw new Error(
                        `HTTP error! Status: ${response.status}`
                    );
                }
                return response.json();
            })
            .then((data) => {
                // Lấy thông tin người dùng từ localStorage
                const user = JSON.parse(localStorage.getItem('user'));
                if (user && user.id) {
                    const cartItems = data.cartItems;
                    let numberOfItemsInCart = 0;

                    // Lặp qua mảng cartItems và đếm số lượng dòng với user_id trùng với user.id
                    for (const item of cartItems) {
                        if (item.user_id === user.id) {
                            numberOfItemsInCart++;
                        }
                    }

                    console.log(numberOfItemsInCart);

                    // Cập nhật số lượng sản phẩm trên giao diện
                    const showQuantity =
                        document.getElementById('showQuantity');
                    showQuantity.textContent = numberOfItemsInCart;
                } else {
                    console.log(
                        'User information not found in localStorage.'
                    );
                }
            })
            .catch((error) => {
                console.error('Error fetching cart data:', error);
            });
    }
    function getInfo(){
        window.location.href = '/user';
    }
</script>
</body>
</html>
