import React, { useState } from 'react';
import { Card, Form, Input, Button, message } from 'antd';
import axios from 'axios';
import { useNavigate, useParams } from 'react-router-dom';
import backgroundImage from '/home/varun/Desktop/FrontEnd/login-front/src/25386.jpg';
import SideMenuRestaurant from '../assets/components/SideMenuRestaurant.js';

const UpdateOrder = () => {
  const navigate = useNavigate();
  const { orderId } = useParams();

  const handleUpdate = async (values) => {
    try {
      const response = await axios.put(`http://localhost:8080/orders/${orderId}`, values, {
      headers: {
                Authorization: `Bearer ${localStorage.getItem('token')}` // Assuming your token key is 'token'
              },
      });
      if (response.status === 200) {
        message.success('Order Updated');
        navigate('/orders');
      } else {
        message.error('Update Failed');
      }
    } catch (error) {
      message.error('Update Failed');
    }
  };

  return (
    <div style={{
      display: 'flex',
      backgroundImage: `url(${backgroundImage})`,
      backgroundSize: 'cover',
      minHeight: '100vh',
    }}>
      <SideMenuRestaurant />
      <div style={{ flex: '1', padding: '20px' }}>
        <Card title={`Update Order ${orderId}`}>
          <Form layout="vertical" onFinish={handleUpdate} wrapperCol={{ span: 8 }}>
            <Form.Item name="status" label="Status">
              <Input style={{ height: '30px' }} placeholder="Enter status" />

            </Form.Item>
            <Form.Item name="acceptedAt" label="Accepted At">
              <Input style={{ height: '30px' }} placeholder="Enter accepted time" />
            </Form.Item>
            <Form.Item name="completedAt" label="Completed At">
              <Input style={{ height: '30px' }} placeholder="Enter completed time" />
            </Form.Item>
            <Button type="primary" htmlType="submit">
              Update
            </Button>
          </Form>
        </Card>
      </div>
    </div>
  );
};

export default UpdateOrder;
