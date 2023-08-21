import React, { useState, useEffect } from 'react';
import { Form, Input, Button, Card, InputNumber, message } from 'antd';
import SideMenuRestaurant from '../assets/components/SideMenuRestaurant.js';
import backgroundImage from '/home/varun/Desktop/FrontEnd/login-front/src/25386.jpg';
import axios from 'axios';
import { useParams, useNavigate } from 'react-router-dom';

const { TextArea } = Input;

const UpdateMenuItem = () => {
  const [dishName, setDishName] = useState('');
  //const [dishDescription, setDishDescription] = useState('');
  const [price, setPrice] = useState('');
  //const [restaurantId, setRestaurantId] = useState('');
  //const appUserId = localStorage.getItem('id') ? Number(localStorage.getItem('id')) : null;
  const { itemId } = useParams();
  const navigate = useNavigate();

  const fetchMenuItemDetails = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/menus/menu/${itemId}`, {
        headers: {
                  Authorization: `Bearer ${localStorage.getItem('token')}`, // Assuming your token key is 'token'
                },
        });
        const menuItem = response.data;
        setDishName(menuItem.itemName);
        setPrice(menuItem.itemPrice);
      } catch (error) {
        console.error('Error fetching menu item details:', error);
      }
    };

  useEffect(() => {
      fetchMenuItemDetails();
    }, [itemId]);

  const handleSubmit = async () => {
      try {
            const response = await axios.put(
              `http://localhost:8080/menus/menu/${itemId}`,
              {
                itemName: dishName,
                itemPrice: price,
              },
              {
                headers: {
                  Authorization: `Bearer ${localStorage.getItem('token')}`,
                },
              }
            );

            message.success('Menu item updated successfully');
            navigate('/menus');
          } catch (error) {
            console.error('Error updating menu item:', error);

            if (error.response && error.response.status === 400) {
              message.error('Failed to update menu item. Invalid input data.');
            } else {
              message.error('Failed to update menu item. Please try again.');
            }
          }
  };

  return (
      <div
        style={{
          display: 'flex',
          backgroundImage: `url(${backgroundImage})`,
          backgroundSize: 'cover',
          minHeight: '100vh',
        }}
      >
        <SideMenuRestaurant />
        <div style={{ flex: 1 }}>
          <Card title="Update Menu Item" style={{ margin: 20, width: '97.5%' }}>
            <Form layout="vertical" wrapperCol={{ span: 8 }}>
              <Form.Item label="Dish Name">
                <Input placeholder="Enter dish name" value={dishName} onChange={e => setDishName(e.target.value)} />
              </Form.Item>
              <Form.Item label="Price($)">
                <InputNumber value={price} onChange={value => setPrice(value)} />
              </Form.Item>
              <Form.Item>
                <Button type="primary" onClick={handleSubmit} style={{backgroundColor: '#f5945f', borderColor: '#f5945f'}}>Update</Button>
              </Form.Item>
            </Form>
          </Card>
        </div>
      </div>
    );
};

export default UpdateMenuItem;
