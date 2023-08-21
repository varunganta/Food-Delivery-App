import React, { useState, useEffect } from 'react';
import { Form, Input, Card, Button, message } from 'antd';
import SideMenuRestaurant from '../assets/components/SideMenuRestaurant';
import backgroundImage from '/home/varun/Desktop/FrontEnd/login-front/src/25386.jpg';
import axios from 'axios';

const Settings = () => {
  const [restaurantName, setRestaurantName] = useState('');
  const [restaurantLocation, setRestaurantLocation] = useState('');
  const [restaurantId, setRestaurantId] = useState('');
  const appUserId = localStorage.getItem('id') ? Number(localStorage.getItem('id')) : null;

  //console.log(appUserId);



    const fetchRestaurantIdByAppUserId = async () => {
        try {
          const response = await axios.get(`http://localhost:8080/restaurants/restaurant/by-app-user/${appUserId}`); // Replace with your API endpoint
          setRestaurantId(response.data);
          console.log(response.data);
        } catch (error) {
          console.error('Error fetching restaurant ID:', error);
        }
      };

    const fetchRestaurantDetails = async () => {
        await fetchRestaurantIdByAppUserId();
        if (restaurantId) {
          try {
            const response = await axios.get(`http://localhost:8080/restaurants/restaurant/${restaurantId}`); // Replace with your API endpoint
            setRestaurantName(response.data.restaurantName);
            setRestaurantLocation(response.data.restaurantLocation);
          } catch (error) {
            console.error('Error fetching restaurant details:', error);
          }
        }
      };

    useEffect(() => {
        fetchRestaurantDetails();
      }, []);

    const handleSubmit = async () => {
        try {
          if (restaurantId) {
            const updateUrl = `http://localhost:8080/restaurants/restaurant/update/${restaurantId}`;
            const response = await axios.put(updateUrl, {
              restaurantName: restaurantName,
              restaurantLocation: restaurantLocation,
            });
            message.success('Settings updated successfully!');
          } else {
            message.error('Restaurant ID not found.');
          }
        } catch (error) {
          console.error('Error updating restaurant:', error);
          message.error('Error updating restaurant settings.');
        }
      };

    return (
    <div style={{
        display: 'flex',
        backgroundImage: `url(${backgroundImage})`,
        backgroundSize: 'cover',
        height: '100vh'
      }}>
      <SideMenuRestaurant />
      <div style={{ flex: 1 }}>
        <Card title='Restaurant Details' style={{ margin: 20 }}>
          <Form layout='vertical' wrapperCol={{ span: 8 }} onFinish={handleSubmit}>
            <Form.Item label='Restaurant Name' required>
              <Input
                placeholder='Enter restaurant name here'
                value={restaurantName}
                onChange={(e) => setRestaurantName(e.target.value)}
              />
            </Form.Item>
            <Form.Item label='Restaurant Address' required>
              <Input
                placeholder='Enter restaurant address here'
                value={restaurantLocation}
                onChange={(e) => setRestaurantLocation(e.target.value)}
              />
            </Form.Item>
            <Form.Item>
              <Button type='primary' style={{ backgroundColor: '#f5945f', borderColor: '#f5945f' }} htmlType='submit'>
                Submit
              </Button>
            </Form.Item>
          </Form>
        </Card>
      </div>
    </div>
    );
    };

export default Settings;
