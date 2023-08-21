import React, { useEffect, useState } from 'react';
import { Card, Table, Button } from 'antd';
import { Link } from 'react-router-dom';
import backgroundImage from '/home/varun/Desktop/FrontEnd/login-front/src/25386.jpg';
import axios from 'axios';
import SideMenuCustomer from '../assets/components/SideMenuCustomer.js';

const columns = [
  {
    title: 'Restaurant Name',
    dataIndex: 'restaurantName',
    key: 'restaurantName',
  },
  {
    title: 'Location',
    dataIndex: 'restaurantLocation',
    key: 'restaurantLocation',
  },
  {
      title: 'Menu',
      key: 'menu',
      render: (text, record) => (
        <Link to={`/customer-menu/${record.restaurantId}`}>
          <Button style={{ borderColor: '#f5945f', color: '#f5945f' }}>
            View Menu
          </Button>
        </Link>
      ),
    },
];

const CustomerHome = () => {
  const [restaurants, setRestaurants] = useState([]);

  useEffect(() => {
    const fetchRestaurants = async () => {
      try {
        const response = await axios.get('http://localhost:8080/restaurants');
        setRestaurants(response.data);
      } catch (error) {
        console.error('Error fetching restaurants:', error);
      }
    };

    fetchRestaurants();
  }, []);

  return (
    <div style={{
      display: 'flex',
      backgroundImage: `url(${backgroundImage})`,
      backgroundSize: 'cover',
      height: '100vh'
    }}>
      <SideMenuCustomer />
      <div style={{ flex: 1, padding: '20px' }}>
        <Card title="Explore Restaurants" style={{ margin: '20px' }}>
          <Table dataSource={restaurants} columns={columns} rowKey="restaurantId" />
        </Card>
      </div>
    </div>
  );
};

export default CustomerHome;
