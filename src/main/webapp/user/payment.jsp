    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    <!DOCTYPE html>
    <html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Thanh toán đơn giản</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 0;
                padding: 0;
                background-color: #f4f4f4;
            }
            .container {
                max-width: 1200px;
                margin: auto;
                padding: 20px;
                background: white;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                border-radius: 10px;
                margin-top: 50px
            }
            .content {
                display: flex;
                flex-wrap: wrap;
                gap: 20px;
            }
            .left, .right {
                flex: 1;
                min-width: 300px;
                padding: 20px;
                box-sizing: border-box;
            }
            .right {
                border-left: 2px solid #ddd;
            }
            label {
                font-weight: bold;
                margin-bottom: 5px;
            }
            input, select {
                padding: 12px;
                margin-bottom: 15px; /* Adding space between input fields */
                border: 1px solid #ddd;
                border-radius: 5px;
                box-sizing: border-box;
                font-size: 16px; /* Adjust input font size */
            }
            input[type="text"], input[type="tel"], input[type="email"] {
                display: block;
                width: 100%;
                margin-bottom: 15px;
            }
            select {
                display: block;
                width: 100%;
                margin-bottom: 15px;
            }
            .btn-container {
                display: flex;
                justify-content: space-between;
                gap: 10px;
            }
            button {
                width: 100%;
                padding: 12px;
                background-color: #ff6600;
                color: white;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                font-size: 16px;
                margin-top: 15px;
            }
            button.cod {
                background: green;
                border: 2px solid transparent;
            }

            button.transfer {
                background: blue;
                border: 2px solid transparent;
            }

            /* Khi được chọn (active) sẽ có viền đậm */
            button.cod.active,
            button.transfer.active {
                border: 2px solid #222;
                box-shadow: 0 0 5px rgba(0, 0, 0, 0.2);
            }
            .cart-item {
                display: flex;
                align-items: center;
                margin-bottom: 10px;
                border-bottom: 1px solid #ddd;
                padding-bottom: 10px;
            }
            .cart-item img {
                width: 50px;
                height: 50px;
                margin-right: 10px;
                border-radius: 5px;
            }
            .submit-btn {
                width: 100%;
                padding: 12px;
                background: #ff6600;
                color: white;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                margin-top: 15px;
            }
            .voucher {
                margin-top: 20px;
            }
            @media (max-width: 768px) {
                .content {
                    flex-direction: column;
                }
                .right {
                    border-left: none;
                    border-top: 2px solid #ddd;
                }
            }
            .loader {
                border: 3px solid #f3f3f3;
                border-top: 3px solid #009688;
                border-radius: 50%;
                width: 20px;
                height: 20px;
                animation: spin 0.8s linear infinite;
                display: inline-block;
                margin-left: 10px;
            }

            @keyframes spin {
                0% { transform: rotate(0deg); }
                100% { transform: rotate(360deg); }
            }
            #shipping_method {
                width: 100%;
                border-collapse: collapse;
                margin-top: 10px;
            }

            #shipping_method th, #shipping_method td {
                padding: 10px;
                text-align: left;
            }

            #shipping_method th {
                background-color: #f8f8f8;
                font-weight: bold;
                color: #333;
            }

            #shipping_method td {
                border-bottom: 1px solid #ddd;
                vertical-align: middle;
            }

            #shipping_method input[type="radio"] {
                transform: scale(1.3); /* Làm cho radio button lớn hơn */
                margin-right: 10px;
                cursor: pointer;
            }

            #shipping_method label {
                font-size: 16px;
                color: #333;
                cursor: pointer;
            }

            #shipping_method td:last-child {
                text-align: right;
                font-weight: bold;
            }

            #shipping_method tr:hover {
                background-color: #f1f1f1;
            }

            #shipping_method tr.selected {
                background-color: #f9f9f9;
            }

            #shipping_method input[type="radio"]:checked + label {
                color: #007bff; /* Đổi màu chữ khi được chọn */
            }
            .show-voucher-link {
                display: inline-flex;
                align-items: center;
                color: #007bff;
                text-decoration: none;
                font-weight: 500;
                font-size: 14px;
                margin-top: 10px;
                transition: color 0.2s ease;
            }

            .show-voucher-link i {
                margin-right: 6px;
                font-size: 14px;
            }

            .show-voucher-link:hover {
                color: #0056b3;
                text-decoration: underline;
                cursor: pointer;
            }

            .voucher-overlay {
                position: fixed;
                inset: 0;
                background: rgba(0,0,0,0.5);
                z-index: 1000;
                display: flex;
                justify-content: center;
                align-items: center;
            }

            .voucher-popup {
                background: #fff;
                border-radius: 10px;
                width: 500px;
                max-height: 90vh;
                overflow-y: auto;
                padding: 20px;
                box-shadow: 0 0 20px rgba(0,0,0,0.3);
                animation: fadeIn 0.3s ease;
                position: relative;
            }

            @keyframes fadeIn {
                from { opacity: 0; transform: scale(0.95); }
                to { opacity: 1; transform: scale(1); }
            }

            .popup-header {
                display: flex;
                justify-content: space-between;
                align-items: center;
                border-bottom: 1px solid #eee;
                padding-bottom: 10px;
                margin-bottom: 15px;
                padding-left: 10px;
                padding-right: 10px;
            }

            .voucher-title {
                font-size: 18px;
                font-weight: 600;
                color: #222;
                margin: 0;
            }

            .close-btn {
                position: absolute;
                top: -20px;
                right: -240px;
                background: none;
                border: none;
                font-size: 30px;
                color: #555;
                font-weight: bold;
                cursor: pointer;
                transition: color 0.2s ease;
            }

            .close-btn:hover {
                color: #000;
            }

            .voucher-item {
                display: flex;
                justify-content: space-between;
                align-items: center;
                border: 1px solid #ddd;
                border-radius: 6px;
                padding: 10px 15px;
                margin-bottom: 12px;
                background-color: #fafafa;
            }

            .voucher-title {
                display: flex;
                align-items: center;
                gap: 8px;
                font-size: 16px;
                font-weight: bold;
                color: #007bff;
            }

            .voucher-desc {
                margin: 4px 0;
                font-size: 14px;
            }

            .voucher-date {
                font-size: 13px;
                color: #666;
            }

            .apply-btn {
                background-color: #007bff;
                color: white;
                border: none;
                padding: 8px 14px;
                border-radius: 5px;
                font-size: 14px;
                cursor: pointer;
                transition: background-color 0.2s ease;
            }

            .apply-btn:hover {
                background-color: #0056b3;
            }
        </style>
        <script>
            function setPaymentMethod(method) {
                document.getElementById("payment_method").value = method;

                const codBtn = document.getElementById("btn-cod");
                const transferBtn = document.getElementById("btn-transfer");

                codBtn.classList.remove("active");
                transferBtn.classList.remove("active");

                if (method === "COD") codBtn.classList.add("active");
                else transferBtn.classList.add("active");
            }
        </script>
        <script>
            // load xử lý
            document.addEventListener("DOMContentLoaded", function () {
                const form = document.querySelector(".voucher form");
                const button = form.querySelector("button");

                form.addEventListener("submit", function (e) {
                    e.preventDefault(); // Ngăn submit ngay lập tức
                    button.disabled = true;
                    button.innerHTML = 'Đang xử lý <span class="loader"></span>';

                    // Giả lập delay 2.5 giây rồi mới submit
                    setTimeout(() => {
                        form.submit();
                    }, 2500);
                });
            });
        </script>
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                const provinceSelect = document.getElementById("province");
                const districtSelect = document.getElementById("district");
                const wardSelect = document.getElementById("ward");

                const provinceNameInput = document.getElementById("province_name");
                const districtNameInput = document.getElementById("district_name");
                const wardNameInput = document.getElementById("ward_name");

                const hcmData = {
                    code: 79,
                    name: "TP. Hồ Chí Minh",
                    districts: [
                        {
                            code: 760,
                            name: "Quận 1",
                            wards: [
                                { code: 26734, name: "Phường Bến Nghé" },
                                { code: 26737, name: "Phường Bến Thành" },
                                { code: 26740, name: "Phường Cô Giang" },
                                { code: 26743, name: "Phường Cầu Kho" },
                                { code: 26746, name: "Phường Cầu Ông Lãnh" },
                                { code: 26749, name: "Phường Đa Kao" },
                                { code: 26752, name: "Phường Nguyễn Thái Bình" },
                                { code: 26755, name: "Phường Nguyễn Cư Trinh" },
                                { code: 26758, name: "Phường Phạm Ngũ Lão" },
                                { code: 26761, name: "Phường Tân Định" }
                            ]
                        },
                        {
                            code: 769,
                            name: "Quận 3",
                            wards: [
                                { code: 26824, name: "Phường 1" },
                                { code: 26827, name: "Phường 2" },
                                { code: 26830, name: "Phường 3" },
                                { code: 26833, name: "Phường 4" },
                                { code: 26836, name: "Phường 5" },
                                { code: 26839, name: "Phường 6" },
                                { code: 26842, name: "Phường 7" },
                                { code: 26845, name: "Phường 8" },
                                { code: 26848, name: "Phường 9" },
                                { code: 26851, name: "Phường 10" },
                                { code: 26854, name: "Phường 11" },
                                { code: 26857, name: "Phường 12" },
                                { code: 26860, name: "Phường 13" },
                                { code: 26863, name: "Phường 14" }
                            ]
                        },
                        {
                            code: 768,
                            name: "Quận Phú Nhuận",
                            wards: [
                                { code: 27118, name: "Phường 1" },
                                { code: 27121, name: "Phường 2" },
                                { code: 27124, name: "Phường 3" },
                                { code: 27127, name: "Phường 4" },
                                { code: 27130, name: "Phường 5" },
                                { code: 27133, name: "Phường 7" },
                                { code: 27136, name: "Phường 8" },
                                { code: 27139, name: "Phường 9" },
                                { code: 27142, name: "Phường 10" },
                                { code: 27145, name: "Phường 11" },
                                { code: 27148, name: "Phường 13" },
                                { code: 27151, name: "Phường 15" },
                                { code: 27154, name: "Phường 17" }
                            ]
                        },
                        {
                            code: 773,
                            name: "Quận Gò Vấp",
                            wards: [
                                { code: 27037, name: "Phường 1" },
                                { code: 27040, name: "Phường 3" },
                                { code: 27043, name: "Phường 4" },
                                { code: 27046, name: "Phường 5" },
                                { code: 27049, name: "Phường 6" },
                                { code: 27052, name: "Phường 7" },
                                { code: 27055, name: "Phường 8" },
                                { code: 27058, name: "Phường 9" },
                                { code: 27061, name: "Phường 10" },
                                { code: 27064, name: "Phường 11" },
                                { code: 27067, name: "Phường 12" },
                                { code: 27070, name: "Phường 13" },
                                { code: 27073, name: "Phường 14" },
                                { code: 27076, name: "Phường 15" },
                                { code: 27079, name: "Phường 16" },
                                { code: 27082, name: "Phường 17" }
                            ]
                        },
                        {
                            code: 776,
                            name: "Quận Bình Thạnh",
                            wards: [
                                { code: 27085, name: "Phường 1" },
                                { code: 27088, name: "Phường 2" },
                                { code: 27091, name: "Phường 3" },
                                { code: 27094, name: "Phường 5" },
                                { code: 27097, name: "Phường 6" },
                                { code: 27100, name: "Phường 7" },
                                { code: 27103, name: "Phường 11" },
                                { code: 27106, name: "Phường 12" },
                                { code: 27109, name: "Phường 13" },
                                { code: 27112, name: "Phường 14" },
                                { code: 27115, name: "Phường 15" },
                                { code: 27118, name: "Phường 17" },
                                { code: 27121, name: "Phường 19" },
                                { code: 27124, name: "Phường 21" },
                                { code: 27127, name: "Phường 22" },
                                { code: 27130, name: "Phường 24" },
                                { code: 27133, name: "Phường 25" },
                                { code: 27136, name: "Phường 26" },
                                { code: 27139, name: "Phường 27" },
                                { code: 27142, name: "Phường 28" }
                            ]
                        },
                        {
                            code: 770,
                            name: "Quận 10",
                            wards: [
                                { code: 26890, name: "Phường 1" },
                                { code: 26893, name: "Phường 2" },
                                { code: 26896, name: "Phường 3" },
                                { code: 26899, name: "Phường 4" },
                                { code: 26902, name: "Phường 5" },
                                { code: 26905, name: "Phường 6" },
                                { code: 26908, name: "Phường 7" },
                                { code: 26911, name: "Phường 8" },
                                { code: 26914, name: "Phường 9" },
                                { code: 26917, name: "Phường 10" },
                                { code: 26920, name: "Phường 11" },
                                { code: 26923, name: "Phường 12" },
                                { code: 26926, name: "Phường 13" },
                                { code: 26929, name: "Phường 14" },
                                { code: 26932, name: "Phường 15" }
                            ]
                        },
                        {
                            code: 771,
                            name: "Quận 11",
                            wards: [
                                { code: 26935, name: "Phường 1" },
                                { code: 26938, name: "Phường 2" },
                                { code: 26941, name: "Phường 3" },
                                { code: 26944, name: "Phường 4" },
                                { code: 26947, name: "Phường 5" },
                                { code: 26950, name: "Phường 6" },
                                { code: 26953, name: "Phường 7" },
                                { code: 26956, name: "Phường 8" },
                                { code: 26959, name: "Phường 9" },
                                { code: 26962, name: "Phường 10" },
                                { code: 26965, name: "Phường 11" },
                                { code: 26968, name: "Phường 12" },
                                { code: 26971, name: "Phường 13" },
                                { code: 26974, name: "Phường 14" },
                                { code: 26977, name: "Phường 15" },
                                { code: 26980, name: "Phường 16" }
                            ]
                        },
                        {
                            code: 778,
                            name: "Quận 7",
                            wards: [
                                { code: 27190, name: "Phường Tân Thuận Đông" },
                                { code: 27193, name: "Phường Tân Thuận Tây" },
                                { code: 27196, name: "Phường Tân Kiểng" },
                                { code: 27199, name: "Phường Tân Hưng" },
                                { code: 27202, name: "Phường Bình Thuận" },
                                { code: 27205, name: "Phường Tân Phong" },
                                { code: 27208, name: "Phường Phú Mỹ" }
                            ]
                        },
                        {
                            code: 774,
                            name: "Quận 12",
                            wards: [
                                { code: 27052, name: "Phường Tân Chánh Hiệp" },
                                { code: 27055, name: "Phường Tân Thới Hiệp" },
                                { code: 27058, name: "Phường Trung Mỹ Tây" },
                                { code: 27061, name: "Phường Tân Hưng Thuận" },
                                { code: 27064, name: "Phường Đông Hưng Thuận" },
                                { code: 27067, name: "Phường Tân Thới Nhất" },
                                { code: 27070, name: "Phường An Phú Đông" },
                                { code: 27073, name: "Phường Thạnh Lộc" },
                                { code: 27076, name: "Phường Thạnh Xuân" }
                            ]
                        },
                        {
                            code: 7691,
                            name: "TP Thủ Đức",
                            wards: [
                                { code: 27001, name: "Phường Linh Trung" },
                                { code: 27004, name: "Phường Linh Đông" },
                                { code: 27007, name: "Phường Linh Tây" },
                                { code: 27010, name: "Phường Bình Thọ" },
                                { code: 27013, name: "Phường Trường Thọ" },
                                { code: 27016, name: "Phường Hiệp Bình Chánh" },
                                { code: 27019, name: "Phường Hiệp Bình Phước" },
                                { code: 27022, name: "Phường Tam Phú" },
                                { code: 27025, name: "Phường Tam Bình" }
                            ]
                        }
                    ]
                };

                // Gán sẵn TP.HCM vào <select>
                let option = document.createElement("option");
                option.value = hcmData.code;
                option.textContent = hcmData.name;
                provinceSelect.appendChild(option);

                provinceSelect.addEventListener("change", function () {
                    provinceNameInput.value = this.options[this.selectedIndex].textContent;

                    districtSelect.innerHTML = "<option value=''>Chọn Quận/Huyện</option>";
                    wardSelect.innerHTML = "<option value=''>Chọn Phường/Xã</option>";
                    hcmData.districts.forEach(district => {
                        let opt = document.createElement("option");
                        opt.value = district.code;
                        opt.textContent = district.name;
                        districtSelect.appendChild(opt);
                    });

                    districtNameInput.value = "";
                    wardNameInput.value = "";
                });

                districtSelect.addEventListener("change", function () {
                    const selectedDistrict = hcmData.districts.find(d => d.code == this.value);
                    districtNameInput.value = selectedDistrict?.name || "";

                    wardSelect.innerHTML = "<option value=''>Chọn Phường/Xã</option>";
                    if (selectedDistrict) {
                        selectedDistrict.wards.forEach(ward => {
                            let opt = document.createElement("option");
                            opt.value = ward.code;
                            opt.textContent = ward.name;
                            wardSelect.appendChild(opt);
                        });
                    }

                    wardNameInput.value = "";
                });
                wardSelect.addEventListener("change", function () {
                    wardNameInput.value = this.options[this.selectedIndex]?.textContent || "";
                });


                // Auto chọn TP.HCM luôn
                provinceSelect.value = hcmData.code;
                provinceSelect.dispatchEvent(new Event("change"));
            });
        </script>
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                const radios = document.querySelectorAll('input[name="shipping_method"]');
                radios.forEach(radio => {
                    radio.addEventListener("change", function () {
                        const selectedId = this.value;
                        const url = new URL(window.location.href);
                        url.searchParams.set("shipping_method", selectedId);
                    });
                });
            });
        </script>
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                const radios = document.querySelectorAll('input[name="shipping_method"]');
                const shippingFeeEl = document.querySelector("#shipping_fee_value");
                const finalTotalEl = document.querySelector("#final_total_value");

                radios.forEach(radio => {
                    radio.addEventListener("change", function () {
                        const selectedId = this.value;

                        fetch("${pageContext.request.contextPath}/update-shipping", {
                            method: "POST",
                            headers: {
                                "Content-Type": "application/x-www-form-urlencoded"
                            },
                            body: "shipping_method_id=" + selectedId
                        })
                            .then(response => response.json())
                            .then(data => {
                                // Cập nhật phí ship và tổng tiền
                                shippingFeeEl.textContent = data.shippingFee.toLocaleString('vi-VN') + " ₫";
                                finalTotalEl.textContent = data.finalTotal.toLocaleString('vi-VN') + " ₫";
                            });
                    });
                });
            });
        </script>
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                const openLink = document.querySelector(".show-voucher-link");
                const closeBtn = document.getElementById("closeVoucherOverlay");
                const overlay = document.getElementById("voucherOverlay");

                openLink.addEventListener("click", function (e) {
                    e.preventDefault();
                    overlay.style.display = "flex";
                });

                closeBtn.addEventListener("click", function () {
                    overlay.style.display = "none";
                });

                // Đóng khi click ngoài khung
                window.addEventListener("click", function (e) {
                    if (e.target === overlay) {
                        overlay.style.display = "none";
                    }
                });
            });
        </script>
        <script>
            function setPaymentMethod(method) {
                document.getElementById("payment_method").value = method;
            }
        </script>

        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    </head>
    <body>
    <div class="container">
        <div class="content">
            <!-- Bên trái: Thông tin đơn hàng -->
            <div class="left">
                <a href="${pageContext.request.contextPath}/home" style="
            display: inline-flex;
            align-items: center;
            gap: 8px;
            background: #eee;
            color: #333;
            text-decoration: none;
            padding: 8px 12px;
            border-radius: 6px;
            font-weight: bold;
            margin-bottom: 10px;
        ">
                    <i class="fas fa-arrow-left"></i> Tiếp tục mua hàng
                </a>
                <h2>Thông tin thanh toán</h2>
                <form method="post" action="${pageContext.request.contextPath}/checkout">
                    <input type="hidden" name="confirm" value="1" />

                    <label>Họ và Tên:</label>
                    <input type="text" name="receiver_name" required>

                    <label>Số điện thoại:</label>
                    <input type="tel" name="phone" required>

                    <label>Email:</label>
                    <input type="email" name="email" required>

                    <label>Tỉnh/Thành phố:</label>
                    <select type="hidden" id="province" name="province">
                        <option value="">Chọn Tỉnh/Thành phố</option>
                        <!-- Chỉ TP.HCM sẽ được thêm vào đây -->
                    </select>
                    <input type="hidden" id="province_name" name="province_name" />

                    <label>Quận/Huyện:</label>
                    <select type="hidden" id="district" name="district">
                        <option value="">Chọn Quận/Huyện</option>
                        <!-- Các quận, huyện của TP.HCM sẽ được thêm vào đây -->
                    </select>
                    <input type="hidden" id="district_name" name="district_name" />

                    <label>Phường/Xã:</label>
                    <select type="hidden" id="ward" name="ward">
                        <option value="">Chọn Phường/Xã</option>
                        <!-- Các phường/xã của quận/huyện sẽ được thêm vào đây -->
                    </select>
                    <input type="hidden" id="ward_name" name="ward_name" />

                    <label>Địa chỉ cụ thể:</label>
                    <input type="text" name="address" required>

                    <label for="shipping_method">Phương thức vận chuyển:</label>
                    <table id="shipping_method" style=" width: 100%; border-collapse: collapse;">
                        <thead>
                        <tr>
                            <th style="text-align: left;">Phương thức vận chuyển</th>
                            <th style="text-align: right;">Giá</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:set var="selectedShippingId" value="${sessionScope.shipping_method != null ? sessionScope.shipping_method : param.shipping_method}" />
                        <c:forEach var="method" items="${shippingMethods}">
                        <tr style="border-bottom: 1px solid #ddd; padding: 10px;">
                                <td style="padding: 10px; display: flex; align-items: center;">
                                    <input type="radio"
                                           id="shippingMethod${method.id}"
                                           name="shipping_method"
                                           value="${method.id}"
                                           style="margin-right: 15px; transform: scale(1.3); cursor: pointer;"
                                           <c:if test="${selectedShippingId == method.id}">checked</c:if>
                                           required />
                                    <label for="shippingMethod${method.id}" style="font-size: 16px; color: #333; font-weight: normal; flex: 1; cursor: pointer;">
                                        <span style="font-weight: bold; color: #444;">${method.methodName}</span> - ${method.carrier}
                                    </label>
                                </td>
                                <td style="padding: 10px; text-align: right; font-size: 16px; font-weight: bold; color: #333;">
                                    <fmt:formatNumber value="${method.shippingFee}" type="currency" currencySymbol="₫"/>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <input type="hidden" id="payment_method" name="payment_method" value="COD" />

                    <h3>Phương thức thanh toán</h3>
                    <div class="btn-container">
                        <button type="button" id="btn-cod" class="cod" onclick="setPaymentMethod('COD')">COD</button>
                        <button type="button" id="btn-transfer" class="transfer" onclick="setPaymentMethod('Bank Transfer')">Chuyển khoản</button>
                    </div>

                    <button type="submit" class="submit-btn">Xác nhận thanh toán</button>
                </form>
            </div>
            <!-- Bên phải: Hiển thị sản phẩm và tổng tiền -->
            <div class="right">
                <h2>Giỏ hàng</h2>
                <c:forEach var="item" items="${cart.getList()}">
                    <div class="cart-item">
                        <c:if test="${not empty item.listImg}">
                            <img src="${item.listImg[0].url}" alt="${item.name}" width="50" />
                        </c:if>
                        <c:if test="${empty item.listImg}">
                            <img src="${pageContext.request.contextPath}/assets/img/default.png" alt="No image" width="50" />
                        </c:if>
                        <span>
                ${item.name} -
                <fmt:formatNumber value="${item.price * item.quantity}" type="number" groupingUsed="true" /> ₫
            </span>
                    </div>
                </c:forEach>

                <div class="voucher">
                    <form action="apply-voucher" method="post" style="display: flex; gap: 10px; flex-wrap: wrap;">
                        <label for="voucher" style="width: 100%;">Mã giảm giá:</label>
                        <input type="text" name="voucherCode" id="voucher" placeholder="Nhập mã giảm giá" style="flex: 2;">
                        <input type="hidden" name="shipping_method" value="${selectedShippingId}" />
                        <button type="submit" class="submit-btn" style="flex: 1; background: #009688;">Áp dụng</button>
                    </form>
                    <a href="#" class="show-voucher-link">
                        <i class="fas fa-ticket-alt"></i> Xem thêm mã giảm giá
                    </a>
                    <c:choose>
                        <c:when test="${not empty discountSuccess}">
                            <p style="color: green;">${discountSuccess}</p>
                        </c:when>
                        <c:when test="${not empty discountError}">
                            <p style="color: red;">${discountError}</p>
                        </c:when>
                    </c:choose>
                </div>

                <c:choose>
                    <c:when test="${not empty newTotalPrice}">
                        <!-- Nếu đã áp dụng mã giảm giá -->
                        <p><strong>Tạm tính:</strong>
                            <fmt:formatNumber value="${tempTotal}" type="number" groupingUsed="true" /> đ
                        </p>
                        <p><strong>Mã giảm giá:</strong>
                            - <fmt:formatNumber value="${discount}" type="number" groupingUsed="true" /> đ
                        </p>
                        <p><strong>Phí vận chuyển:</strong>
                            <fmt:formatNumber value="${shippingFee}" type="number" groupingUsed="true" /> đ
                        </p>
                        <hr />
                        <h3><strong>Tổng cộng:</strong>
                            <span style="color: red;">
                    <fmt:formatNumber value="${finalTotal}" type="number" groupingUsed="true" /> đ
                </span>
                        </h3>
                        <p>(Đã áp dụng mã: <strong>${appliedPromotion.code}</strong>)</p>
                    </c:when>
                    <c:otherwise>
                        <!-- Trường hợp chưa áp mã, hiển thị mặc định -->
                        <p><strong>Tạm tính:</strong>
                            <fmt:formatNumber value="${cart.getTotalPrice()}" type="number" groupingUsed="true" /> đ
                        </p>
                        <p><strong>Mã giảm giá:</strong> 0 ₫</p>
                        <p><strong>Phí vận chuyển:</strong>
                            <fmt:formatNumber value="${shippingFee}" type="number" groupingUsed="true" /> đ
                        </p>
                        <hr />
                        <h3><strong>Tổng cộng:</strong>
                            <span style="color: red;">
                    <fmt:formatNumber value="${finalTotal}" type="number" groupingUsed="true" /> đ
                </span>
                        </h3>
                    </c:otherwise>
                </c:choose>
            </div>

        </div>
    </div>
    <c:if test="${not empty discountSuccess}">
        <script>
            Swal.fire({
                icon: 'success',
                title: 'Thành công!',
                text: '${discountSuccess}',
                showConfirmButton: false,
                timer: 2500
            });
        </script>
        <%
            session.removeAttribute("discountSuccess");
        %>
    </c:if>

    <c:if test="${not empty discountError}">
        <script>
            Swal.fire({
                icon: 'error',
                title: 'Lỗi',
                text: '${discountError}',
                showConfirmButton: true
            });
        </script>
        <%
            session.removeAttribute("discountError");
        %>
    </c:if>
    <c:if test="${orderSuccess}">
        <script>
            setTimeout(() => {
                Swal.fire({
                    icon: 'success',
                    title: 'Đặt hàng thành công!',
                    text: 'Mã hóa đơn của bạn là: ${invoiceId}',
                    confirmButtonText: 'OK'
                }).then(() => {
                    window.location.href = '${pageContext.request.contextPath}/home';
                });
            }); // delay 2 giây
        </script>
    </c:if>

    <div id="voucherOverlay" class="voucher-overlay" style="display: none;">
        <div class="voucher-popup">
            <div class="popup-header">
                <h3 class="voucher-title">Chọn giảm giá</h3>
                <button id="closeVoucherOverlay" class="close-btn">&times;</button>
            </div>
            <div class="voucher-list">
                <c:choose>
                    <c:when test="${not empty promotionList}">
                        <c:forEach var="voucher" items="${promotionList}">
                            <div class="voucher-item">
                                <div class="voucher-info">
                                    <div class="voucher-title">
                                        <i class="fas fa-percent"></i>
                                        <strong>${voucher.code}</strong>
                                    </div>
                                    <p class="voucher-desc">
                                        Giảm <strong>${voucher.percent_discount}%</strong> cho đơn từ
                                        <fmt:formatNumber value="${voucher.min_order_amount}" type="number" groupingUsed="true"/> ₫
                                    </p>
                                    <p class="voucher-date">
                                        Hiệu lực: ${voucher.start_date} → ${voucher.end_date}
                                    </p>
                                </div>
                                <form action="apply-voucher" method="post" style="margin-left: 10px;">
                                    <input type="hidden" name="voucherCode" value="${voucher.code}" />
                                    <input type="hidden" name="shipping_method" value="${sessionScope.shipping_method}" />
                                    <button type="submit" class="apply-btn">Áp dụng</button>
                                </form>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <p style="color: red; text-align: center;">Không có mã giảm giá nào.</p>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
    </body>
    </html>