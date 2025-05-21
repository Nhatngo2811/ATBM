document.addEventListener("DOMContentLoaded", function () {
    const cartItemsContainer = document.getElementById("cart-items");
    const cartEmptyMessage = document.getElementById("cart-empty-message");
    const totalPriceElement = document.getElementById("total-price");
    const minimumWarning = document.getElementById("minimum-warning");
    const checkoutButton = document.getElementById("checkout-button");
    const deliveryAsap = document.getElementById("delivery-asap");
    const deliverySchedule = document.getElementById("delivery-schedule");
    const asapInfo = document.getElementById("asap-info");
    const scheduleInfo = document.getElementById("schedule-info");

    // Các sản phẩm mẫu
    const sampleProducts = [
        // {
        //     name: "Dâu tây Đà Lạt",
        //     price: 161000,
        //     quantity: 3,
        //     imageUrl: "/project_fruit/assets/img/traicaynhapkhau/dautaygiongmy.png"
        // },
        // {
        //     name: "Hộp quà Nguyên Cát 03",
        //     price: 2240000,
        //     quantity: 1,
        //     imageUrl: "/project_fruit/assets/img/hqnc3.png"
        // }
    ];

    // Thêm sản phẩm mẫu vào giỏ hàng
    sampleProducts.forEach(addProductToCart);

    function addProductToCart(product) {
        cartEmptyMessage.style.display = "none";

        const productElement = document.createElement("div");
        productElement.classList.add("cart-item");
        productElement.innerHTML = `
          <div class="product-image">
              <img src="${product.imageUrl}" alt="${product.name}">
          </div>
          <div class="product-info">
              <h4>${product.name}</h4>
              <p>${product.price.toLocaleString()}₫</p>
              <div class="quantity-controls">
                  <button class="decrease">-</button>
                  <span class="quantity">${product.quantity}</span>
                  <button class="increase">+</button>
              </div>
              <p><strong>Tổng:</strong> <span class="item-total">${(product.price * product.quantity).toLocaleString()}₫</span></p>
          </div>
          <button class="remove-item">Xóa</button>
      `;

        cartItemsContainer.appendChild(productElement);

        // Thêm sự kiện cho nút tăng/giảm số lượng
        const decreaseButton = productElement.querySelector(".decrease");
        const increaseButton = productElement.querySelector(".increase");
        const quantityElement = productElement.querySelector(".quantity");
        const itemTotalElement = productElement.querySelector(".item-total");

        decreaseButton.addEventListener("click", () => updateQuantity(product, quantityElement, itemTotalElement, -1));
        increaseButton.addEventListener("click", () => updateQuantity(product, quantityElement, itemTotalElement, 1));

        updateTotalPrice();
    }

    function updateQuantity(product, quantityElement, itemTotalElement, change) {
        product.quantity = Math.max(1, product.quantity + change);
        quantityElement.textContent = product.quantity;
        itemTotalElement.textContent = (product.price * product.quantity).toLocaleString() + "₫";
        updateTotalPrice();
    }

    function updateTotalPrice() {
        const items = cartItemsContainer.getElementsByClassName("cart-item");
        let total = 0;
        Array.from(items).forEach(item => {
            const priceText = item.querySelector(".product-info p").textContent;
            const price = parseFloat(priceText.replace(/[^\d]/g, "")); // Loại bỏ tất cả ký tự không phải số
            const quantity = parseInt(item.querySelector(".quantity").textContent);
            total += price * quantity;
        });

        totalPriceElement.textContent = total.toLocaleString("vi-VN") + "₫";

        if (total >= 100000) {
            minimumWarning.style.display = "none";
            checkoutButton.disabled = false;
            checkoutButton.classList.add("enabled");
        } else {
            minimumWarning.style.display = "block";
            checkoutButton.disabled = true;
            checkoutButton.classList.remove("enabled");
        }
    }

    function toggleDeliveryOptions() {
        asapInfo.style.display = deliveryAsap.checked ? "block" : "none";
        scheduleInfo.style.display = deliverySchedule.checked ? "block" : "none";
    }

    deliveryAsap.addEventListener("change", toggleDeliveryOptions);
    deliverySchedule.addEventListener("change", toggleDeliveryOptions);
});








