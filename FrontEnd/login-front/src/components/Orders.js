import React, { useState, useEffect } from 'react';
//import orders from "../assets/data/orders.json";
import { Card, Table, Tag, Modal, Select, Button, message, Popconfirm } from 'antd';
import { useNavigate, Link } from 'react-router-dom';
import SideMenuRestaurant from '../assets/components/SideMenuRestaurant.js';
import backgroundImage from '/home/varun/Desktop/FrontEnd/login-front/src/25386.jpg';
import axios from 'axios';
import OrderItems from './OrderItems.js';

const {Option} = Select;

const Orders = () => {
  const navigate = useNavigate();
  const [orders, setOrders] = useState([]);
  const [restaurantId, setRestaurantId] = useState('');
  const [orderItems, setOrderItems] = useState([]);
  const appUserId = localStorage.getItem('id') ? Number(localStorage.getItem('id')) : null;
  const [customerNames, setCustomerNames] = useState({});
  //const appUserId = localStorage.getItem('id') ? localStorage.getItem('id') : null;


  const [selectedOrder, setSelectedOrder] = useState(null);
  const [isModalVisible, setIsModalVisible] = useState(false);


  const fetchRestaurantIdByAppUserId = async () => {
          try {
            const response = await axios.get(`http://localhost:8080/restaurants/restaurant/by-app-user/${appUserId}`);
            setRestaurantId(response.data);
            console.log(response.data);
          } catch (error) {
            console.error('Error fetching restaurant ID:', error);
          }
        };

  const handleDeleteOrder = async (orderId) => {
      try {
        const response = await axios.delete(`http://localhost:8080/orders/order/${orderId}`);
        if (response.status === 204) {
          message.success('Order Deleted');
          fetchOrders();
        } else {
          message.error('Delete Failed');
        }
      } catch (error) {
        message.error('Delete Failed');
      }
    };

  const tableColumns = [
    {
      title: 'Order ID',
      dataIndex: 'orderId',
      key: 'orderId',
    },
    {
      title: 'Customer ID',
      dataIndex: 'customerId',
      key: 'customerId'
    },
    {
        title: 'Customer Name',
        dataIndex: 'customerId',
        key: 'customerId',
        render: (customerId) => customerNames[customerId] || 'N/A',
    },
    {
      title: 'Status',
      dataIndex: 'status',
      key: 'status',
      render: (status) => (
        <Tag color={status === 'completed' ? 'green' : status === 'delivering' ? 'orange' : 'red'}>
          {status}
        </Tag>
      )
    },
    {
      title: 'Created At',
      dataIndex: 'createdAt',
      key: 'createdAt',
      render: (createdAt) => new Date(createdAt).toLocaleString()
    },
    {
      title: 'Accepted At',
      dataIndex: 'acceptedAt',
      key: 'acceptedAt',
      render: (acceptedAt) => new Date(acceptedAt).toLocaleString()
    },
    {
      title: 'Completed At',
      dataIndex: 'completedAt',
      key: 'completedAt',
      render: (completedAt) => new Date(completedAt).toLocaleString()
    },
    {
      title: 'Total Price',
      dataIndex: 'totalPrice',
      key: 'totalPrice',
      render: (totalPrice, record) => (
        <span onClick={() => navigate(`orders/${record.orderId}`)}>
          {totalPrice} $
        </span>
      )
    },
    {
      title: 'Items',
      dataIndex: 'orderId',
      key: 'actions',
      render: (orderId, record) => (
        <Link to={`/orders/order/${orderId}`}>
          <Button style={{ borderColor: '#f5945f', color: '#f5945f' }}>
           Order Items
          </Button>
        </Link>
      ),
    },
    {
      title: 'Delete',
      dataIndex: 'orderId',
      key: 'actions',
      render: (orderId, record) => (
            <Popconfirm
              title="Are you sure to delete this order?"
              onConfirm={() => handleDeleteOrder(orderId)}
              okText="Yes"
              cancelText="No"
            >
              <Button type="danger">
                &#x2716;
              </Button>
            </Popconfirm>
          ),

    },
  ];

  const handleUpdateClick = (record) => {
      setSelectedOrder(record);
      setIsModalVisible(true);
    };

    const handleOrderSelection = (orderId) => {
      setIsModalVisible(false);
      navigate(`/orders/update/${orderId}`);
    };

    const fetchOrders = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/orders/by-restaurant/${restaurantId}`);
        const ordersWithTotalPrice = await Promise.all(
          response.data.map(async (order) => {
            const orderItemsResponse = await axios.get(`http://localhost:8080/orderItems/byOrder/${order.orderId}`);

            const totalPrice = orderItemsResponse.data.reduce((acc, item) => {
              return acc + item.price * item.quantity;
            }, 0);

            const customerResponse = await axios.get(`http://localhost:8080/customers/customer/${order.customerId}`);
            const customerName = customerResponse.data.firstName;

            setCustomerNames(prevCustomerNames => ({
                ...prevCustomerNames,
                [order.customerId]: customerName,
              }));

            return {
              ...order,
              totalPrice: totalPrice
            };
          })
        );
        setOrders(ordersWithTotalPrice);
      } catch (error) {
        console.error('Error fetching orders:', error);
      }
    };


    const fetchOrderItems = async (orderId) => {
        try {
          const response = await axios.get(`http://localhost:8080/orderItems/byOrder/${orderId}`);
          setOrderItems(response.data);
        } catch (error) {
          console.error('Error fetching order items:', error);
        }
      };


    useEffect(() => {
        fetchRestaurantIdByAppUserId();
      }, [appUserId]);

    useEffect(() => {
        if (restaurantId !== '') {
          fetchOrders();
        }
      }, [restaurantId]);

    return (
        <div style={{
          display: 'flex',
          backgroundImage: `url(${backgroundImage})`,
          backgroundSize: 'cover',
          height: '100vh'
        }}>
          <SideMenuRestaurant />
          <div style={{ flex: '1' }}>
            <Card title={'Orders'} style={{ margin: 20 }}>
              <Button
                type = "primary"
                style={{ float: 'right', marginBottom: 10, backgroundColor: '#f5945f', borderColor: '#f5945f' }}
                onClick={() => setIsModalVisible(true)}
              >
                Update Order
              </Button>
              <Table
                dataSource={orders}
                columns={tableColumns}
                rowKey='orderId'
              />
              <Modal
                title="Select Order to Update"
                visible={isModalVisible}
                onCancel={() => setIsModalVisible(false)}
                footer={null}
              >
                <Select style={{ width: '100%' }} onChange={handleOrderSelection}>
                  {orders.map(order => (
                    <Option key={order.orderId} value={order.orderId}>
                      Order ID: {order.orderId}
                    </Option>
                  ))}
                </Select>
              </Modal>
            </Card>
          </div>
        </div>
      );


};

export default Orders;