import React, { useState, useEffect} from 'react';
import { Form, Input, Button, Card, InputNumber, message } from 'antd';
import SideMenuRestaurant from '../assets/components/SideMenuRestaurant.js';
import backgroundImage from '/home/varun/Desktop/FrontEnd/login-front/src/25386.jpg';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const { TextArea } = Input;

const CreateMenuItem = () => {
    const [dishName, setDishName] = useState('');
    const [dishDescription, setDishDescription] = useState('');
    const [price, setPrice] = useState('');
    const [restaurantId, setRestaurantId] = useState('');
    const appUserId = localStorage.getItem('id') ? Number(localStorage.getItem('id')) : null;
    const navigate = useNavigate();

    const fetchRestaurantIdByAppUserId = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/restaurants/restaurant/by-app-user/${appUserId}`);
            setRestaurantId(response.data);
            console.log(response.data);
        } catch (error) {
            console.error('Error fetching restaurant ID:', error);
        }
    };

    useEffect(() => {
            fetchRestaurantIdByAppUserId();
        }, []);

    const handleSubmit = async () => {
        try {
                    const response = await axios.post('http://localhost:8080/menus/create', {
                        restaurantId: restaurantId,
                        itemName: dishName,
                        itemPrice: price,
                    });

                    message.success('Menu item created successfully');

                    setDishName('');
                    setPrice('');
                    navigate('/menus');
                } catch (error) {
                    console.error('Error creating menu item:', error);

                    if (error.response && error.response.status === 400) {
                        message.error('Failed to create menu item. Invalid input data.');
                    } else {
                        message.error('Failed to create menu item. Please try again.');
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
                overflow: 'hidden',
            }}
        >
            <SideMenuRestaurant />
            <div style={{ flex: 1 }}>
                <Card title="New Menu Item" style={{ margin: 20, width: '97.5%' }}>
                    <Form layout="vertical" wrapperCol={{ span: 8 }}>
                        <Form.Item label="Dish Name" required>
                            <Input placeholder="Enter dish name" value={dishName} onChange={e => setDishName(e.target.value)} />
                        </Form.Item>
                        <Form.Item label="Price($)" required>
                            <InputNumber value={price} onChange={value => setPrice(value)} />
                        </Form.Item>
                        <Form.Item>
                            <Button type="primary" onClick={handleSubmit} style={{backgroundColor: '#f5945f', borderColor: '#f5945f'}}>Submit</Button>
                        </Form.Item>
                    </Form>
                </Card>
            </div>
        </div>
    );
};

export default CreateMenuItem;
