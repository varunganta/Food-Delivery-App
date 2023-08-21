import React from 'react';
import { Card, Typography } from 'antd';
import SideMenuRestaurant from '../assets/components/SideMenuRestaurant.js';
import backgroundImage from '/home/varun/Desktop/FrontEnd/login-front/src/25386.jpg';

const { Title } = Typography;

const RestaurantHome = () => {
  const restaurants = [

  ];

  return (
    <div style={{
                            display: 'flex',
                            backgroundImage: `url(${backgroundImage})`,
                            backgroundSize: 'cover',
                            height: '100vh'
                          }}>
      <SideMenuRestaurant />
      <div style={{ flex: 1, padding: '20px' }}>
        <Title level={2}>Welcome to Restaurant Home</Title>
        <div style={{ display: 'flex', flexWrap: 'wrap' }}>
          {restaurants.map((restaurant) => (
            <Card
              key={restaurant.id}
              title={restaurant.name}
              style={{ width: '300px', margin: '10px' }}
            >
              {restaurant.description}
            </Card>
          ))}
        </div>
      </div>
    </div>
  );
};

export default RestaurantHome;
