import React, {useState, useEffect} from 'react';
import {Form, Input, Card, Button, message} from 'antd';

const Settings = () => {
    const [restaurantName, setRestaurantName] = useState('');
    const [restaurantAddress, setRestaurantAddress] = useState('');

    useEffect(() => {
        fetchRestaurantProfile();
    }, []);



    return(
        <Card title = "Restaurant Details" style = {{margin: 20}}>
            <Form layout = 'vertical' wrapperCol = {{span: 8}}>
                <Form.Item label = "Restaurant Name" required>
                    <Input placeholder = 'Enter restaurant name here'/>
                </Form.Item>
                <Form.Item label = "Restaurant Address" required>
                    <Input placeholder = 'Enter restaurant address here'/>
                </Form.Item>
                <Form.Item>
                    <Button type = 'primary'>Submit</Button>
                </Form.Item>
            </Form>
        </Card>
    );
};

export default Settings;