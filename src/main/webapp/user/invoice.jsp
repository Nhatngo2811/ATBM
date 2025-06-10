<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Chi tiết hóa đơn</title>
    <style>
        .invoice-container {
            max-width: 800px;
            margin: 20px auto;
            padding: 20px;
            border: 1px solid #ddd;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
            font-family: Arial, sans-serif;
        }
        .invoice-header {
            text-align: center;
            margin-bottom: 30px;
        }
        .invoice-header h1 {
            color: #333;
            margin: 0;
        }
        .invoice-details {
            margin-bottom: 30px;
        }
        .invoice-details table {
            width: 100%;
            border-collapse: collapse;
        }
        .invoice-details td {
            padding: 8px;
        }
        .products-table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 30px;
        }
        .products-table th, .products-table td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: left;
        }
        .products-table th {
            background-color: #f5f5f5;
        }
        .total-section {
            text-align: right;
            margin-top: 20px;
        }
        .print-button {
            text-align: center;
            margin-top: 20px;
        }
        .print-button button {
            padding: 10px 20px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .print-button button:hover {
            background-color: #45a049;
        }
        @media print {
            .print-button {
                display: none;
            }
            .invoice-container {
                box-shadow: none;
                border: none;
            }
        }
    </style>
</head>
<body>
    <div class="invoice-container">
        <div class="invoice-header">
            <h1>HÓA ĐƠN BÁN HÀNG</h1>
            <p>Vitamin FRUIT</p>
        </div>
        
        <div class="invoice-details">
            <table>
                <tr>
                    <td><strong>Mã hóa đơn:</strong></td>
                    <td>${invoice.idInvoice}</td>
                    <td><strong>Ngày tạo:</strong></td>
                    <td><fmt:formatDate value="${invoice.createDate}" pattern="dd/MM/yyyy HH:mm"/></td>
                </tr>
                <tr>
                    <td><strong>Khách hàng:</strong></td>
                    <td>${invoice.receiverName}</td>
                    <td><strong>Số điện thoại:</strong></td>
                    <td>${invoice.phone}</td>
                </tr>
                <tr>
                    <td><strong>Địa chỉ:</strong></td>
                    <td colspan="3">${invoice.addressFull}</td>
                </tr>
            </table>
        </div>

        <table class="products-table">
            <thead>
                <tr>
                    <th>STT</th>
                    <th>Tên sản phẩm</th>
                    <th>Số lượng</th>
                    <th>Đơn giá</th>
                    <th>Thành tiền</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="item" items="${invoiceDetails}" varStatus="status">
                    <tr>
                        <td>${status.index + 1}</td>
                        <td>${item.name}</td>
                        <td>${item.quantity}</td>
                        <td><fmt:formatNumber value="${item.price}" pattern="#,###"/> đ</td>
                        <td><fmt:formatNumber value="${item.price * item.quantity}" pattern="#,###"/> đ</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div class="total-section">
            <p><strong>Phí vận chuyển:</strong> <fmt:formatNumber value="${invoice.shippingFee}" pattern="#,###"/> đ</p>
            <p><strong>Tổng tiền:</strong> <fmt:formatNumber value="${invoice.totalPrice}" pattern="#,###"/> đ</p>
        </div>

        <div class="print-button">
            <button onclick="window.print()">In hóa đơn</button>
        </div>
    </div>
</body>
</html> 