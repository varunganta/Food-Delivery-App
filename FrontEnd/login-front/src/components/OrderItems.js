import React, { useState, useEffect } from 'react';
import { Card, Table } from 'antd';
import SideMenuRestaurant from '../assets/components/SideMenuRestaurant.js';
import backgroundImage from '/home/varun/Desktop/FrontEnd/login-front/src/25386.jpg';
import axios from 'axios';
import { useParams } from 'react-router-dom';

const OrderItems = () => {
  const [orderItems, setOrderItems] = useState([]);
  const { orderId } = useParams();

  console.log(orderId);

  const fetchOrderItems = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/orderItems/byOrder/${orderId}`);
      setOrderItems(response.data);
    } catch (error) {
      console.error('Error fetching order items:', error);
    }
  };

  useEffect(() => {
      if (orderId) {
        fetchOrderItems();
      }
    }, [orderId]);

  const tableColumns = [
    {
      title: 'Menu ID',
      dataIndex: 'menuId',
      key: 'menuId',
    },
    {
      title: 'Price',
      dataIndex: 'price',
      key: 'price',
      render: (price) => `${price} $`,
    },
    {
      title: 'Quantity',
      dataIndex: 'quantity',
      key: 'quantity',
    },
  ];

  return (
    <div style={{
      display: 'flex',
      backgroundImage: `url(${backgroundImage})`,
      backgroundSize: 'cover',
      height: '100vh'
    }}>
      <SideMenuRestaurant />
      <div style={{ flex: '1' }}>
        <Card title={'Order Items'} style={{ margin: 20 }}>
          <Table
            dataSource={orderItems}
            columns={tableColumns}
            rowKey='id'
          />
        </Card>
      </div>
    </div>
  );
};

export default OrderItems;
