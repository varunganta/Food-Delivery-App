import React, { useState, useEffect } from 'react';
import { Card, Table, Tag, Button, Modal } from 'antd';
import SideMenuCustomer from '../assets/components/SideMenuCustomer';
import backgroundImage from '/home/varun/Desktop/FrontEnd/login-front/src/25386.jpg';
import axios from 'axios';
import { Link } from 'react-router-dom';

const OrderHistory = () => {
  const [orderHistory, setOrderHistory] = useState([]);
  const [selectedOrder, setSelectedOrder] = useState(null);
  const [orderItems, setOrderItems] = useState([]);
  const [modalVisible, setModalVisible] = useState(false);

    const fetchOrderHistory = async () => {
      const appUserId = localStorage.getItem('id') ? Number(localStorage.getItem('id')) : null;
      try {
        const response = await axios.get(`http://localhost:8080/orders/by-customer/${appUserId}`);
        const ordersWithTotalPriceAndRestaurant = await Promise.all(response.data.map(async (order) => {
          try {
            const orderItemsResponse = await axios.get(`http://localhost:8080/orderItems/byOrder/${order.orderId}`);
            const orderItems = orderItemsResponse.data;

            const totalPrice = orderItems.reduce((total, item) => total + (item.price * item.quantity), 0);

            const restaurantResponse = await axios.get(`http://localhost:8080/restaurants/restaurant/${order.restaurantId}`);
            const restaurantName = restaurantResponse.data.restaurantName;
            const restaurantLocation = restaurantResponse.data.restaurantLocation;

            return {
              ...order,
              totalPrice,
              restaurantName,
              restaurantLocation,
            };
          } catch (error) {
            console.error('Error fetching details:', error);
            return order;
          }
        }));
        setOrderHistory(ordersWithTotalPriceAndRestaurant);
      } catch (error) {
        console.error('Error fetching order history:', error);
      }
    };



  const tableColumns = [
    {
      title: 'Order ID',
      dataIndex: 'orderId',
      key: 'orderId',
    },
    {
        title: 'Restaurant',
        key: 'restaurant',
        render: (_, record) => (
          <span>
            {record.restaurantName}, {record.restaurantLocation}
          </span>
        ),
      },
    {
      title: 'Total Price',
      dataIndex: 'totalPrice',
      key: 'totalPrice',
      render: (totalPrice) => `${totalPrice.toFixed(2)} $`,
    },
    {
        title: 'Status',
        dataIndex: 'status',
        key: 'status',
        render: (status) => {
          let color;
          if (status === 'completed') {
            color = 'green';
          } else if (status === 'delivering') {
            color = 'orange';
          } else {
            color = 'red';
          }
          return <Tag color={color}>{status}</Tag>;
        },
      },
    {
          title: 'Order Items',
          dataIndex: 'orderId',
          key: 'actions',
          render: (orderId, record) => (
            <Button
              style={{ borderColor: '#f5945f', color: '#f5945f' }}
              onClick={() => {
                fetchOrderItems(orderId);
              }}
            >
              View Order Items
            </Button>
          ),
        },
  ];

   const fetchOrderItems = async (orderId) => {
       try {
         const response = await axios.get(`http://localhost:8080/orderItems/byOrder/${orderId}`);
         const orderItemsWithMenuDetails = await Promise.all(
           response.data.map(async (item) => {
             try {
               const menuResponse = await axios.get(`http://localhost:8080/menus/menu/${item.menuId}`);
               const menuName = menuResponse.data.itemName;

               return {
                 ...item,
                 menuName,
               };
             } catch (menuError) {
               console.error('Error fetching menu details:', menuError);
               return item;
             }
           })
         );
         setOrderItems(orderItemsWithMenuDetails);
         setModalVisible(true);
       } catch (error) {
         console.error('Error fetching order items:', error);
       }
     };

  useEffect(() => {
      fetchOrderHistory();
    }, []);

  return (
    <div style={{
      display: 'flex',
      backgroundImage: `url(${backgroundImage})`,
      backgroundSize: 'cover',
      height: '100vh',
    }}>
      <SideMenuCustomer />
      <div style={{ flex: 1 }}>
        <Card title={'Order History'} style={{ margin: 20 }}>
          <Table dataSource={orderHistory} columns={tableColumns} rowKey='orderID' />
        </Card>
        <Modal
          title="Order Items"
          visible={modalVisible}
          onCancel={() => setModalVisible(false)}
          footer={null}
        >
          {orderItems.map(item => (
            <div key={item.id}>
              {item.menuName} - {item.price.toFixed(2)}$ - {item.quantity}
            </div>
          ))}
        </Modal>
      </div>
    </div>
  );
};

export default OrderHistory;
