import React, { useState, useEffect } from 'react';
import { Form, Input, Card, Button, message } from 'antd';
import SideMenuCustomer from '../assets/components/SideMenuCustomer';
import backgroundImage from '/home/varun/Desktop/FrontEnd/login-front/src/25386.jpg';
import axios from 'axios';

const CustomerSettings = () => {
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [newPassword, setNewPassword] = useState('');
  const [newAddress, setNewAddress] = useState('');
  const appUserId = localStorage.getItem('id') ? Number(localStorage.getItem('id')) : null;

  const fetchCustomerDetails = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/customers/customer/${appUserId}`);
      const customerData = response.data;
      setFirstName(customerData.firstName);
      setLastName(customerData.lastName);
    } catch (error) {
      console.error('Error fetching customer details:', error);
    }
  };

  useEffect(() => {
    fetchCustomerDetails();
  }, []);

  const handleSubmit = async () => {
    try {
      const updateUrl = `http://localhost:8080/customers/update/${appUserId}`;
      const response = await axios.put(updateUrl, {
        firstName: firstName,
        lastName: lastName,
        newPassword: newPassword,
      });
      message.success('Profile updated successfully!');
    } catch (error) {
      console.error('Error updating profile:', error);
      message.error('Error updating profile.');
    }
  };

  return (
    <div style={{
      display: 'flex',
      backgroundImage: `url(${backgroundImage})`,
      backgroundSize: 'cover',
      height: '100vh'
    }}>
      <SideMenuCustomer />
      <div style={{ flex: 1 }}>
        <Card title='Edit Profile' style={{ margin: 20 }}>
          <Form layout='vertical' wrapperCol={{ span: 8 }} onFinish={handleSubmit}>
            <Form.Item label='First Name' >
              <Input
                placeholder='Enter first name here'
                value={firstName}
                onChange={(e) => setFirstName(e.target.value)}
              />
            </Form.Item>
            <Form.Item label='Last Name' >
              <Input
                placeholder='Enter last name here'
                value={lastName}
                onChange={(e) => setLastName(e.target.value)}
              />
            </Form.Item>
            <Form.Item label='New Password'>
              <Input.Password
                placeholder='Enter new password'
                value={newPassword}
                onChange={(e) => setNewPassword(e.target.value)}
              />
            </Form.Item>
            <Form.Item label='New Address'>
              <Input.TextArea
                placeholder='Enter new address'
                value={newAddress}
                onChange={(e) => setNewAddress(e.target.value)}
              />
            </Form.Item>
            <Form.Item>
              <Button type='primary' style={{ backgroundColor: '#f5945f', borderColor: '#f5945f' }} htmlType='submit'>
                Save
              </Button>
            </Form.Item>
          </Form>
        </Card>
      </div>
    </div>
  );
};

export default CustomerSettings;
