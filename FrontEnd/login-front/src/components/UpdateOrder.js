import React, { useState } from 'react';
import { Card, Form, Input, Button, message, Select, DatePicker, TimePicker } from 'antd';
import axios from 'axios';
import { useNavigate, useParams } from 'react-router-dom';
import backgroundImage from '/home/varun/Desktop/FrontEnd/login-front/src/25386.jpg';
import SideMenuRestaurant from '../assets/components/SideMenuRestaurant.js';
import moment from 'moment';

const {Option} = Select;

const UpdateOrder = () => {
  const navigate = useNavigate();
  const { orderId } = useParams();
  const [status, setStatus] = useState('');
  const [acceptedAt, setAcceptedAt] = useState(null);
  const [completedAt, setCompletedAt] = useState(null);

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

  const handleStatusChange = (value) => {
      setStatus(value);
    };

  const handleAcceptedAtChange = (value) => {
      setAcceptedAt(value);
    };

    const handleCompletedAtChange = (value) => {
      setCompletedAt(value);
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
              <Select
                  style={{ width: '100%' }}
                  placeholder="Select status"
                  value={status}
                  onChange={handleStatusChange}
                >
                  <Option value="processing">Processing</Option>
                  <Option value="delivering">Delivering</Option>
                  <Option value="completed">Completed</Option>
                </Select>
            </Form.Item>
            <Form.Item name="acceptedAt" label="Accepted At">
              <DatePicker
                  showTime
                  format="YYYY-MM-DD HH:mm:ss"
                  style={{ width: '100%' }}
                  defaultValue={acceptedAt ? moment(acceptedAt).utcOffset('+05:30') : null}
                  onChange={handleAcceptedAtChange}
                />
            </Form.Item>
            <Form.Item name="completedAt" label="Completed At">
              <Input style={{ height: '30px' }} placeholder="Enter completed time" />
            </Form.Item>
            <Button type="primary" style={{ backgroundColor: '#f5945f', borderColor: '#f5945f' }} htmlType="submit">
              Update
            </Button>
          </Form>
        </Card>
      </div>
    </div>
  );
};

export default UpdateOrder;
