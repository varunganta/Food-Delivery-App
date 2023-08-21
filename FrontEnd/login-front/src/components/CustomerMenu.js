import React, { useEffect, useState } from 'react';
import { Card, Table, Button, message, Input } from 'antd';
import backgroundImage from '/home/varun/Desktop/FrontEnd/login-front/src/25386.jpg';
import axios from 'axios';
import { useParams } from 'react-router-dom';
import SideMenuCustomer from '../assets/components/SideMenuCustomer.js';

const CustomerMenu = () => {
  const { restaurantId } = useParams();
  const [menuItems, setMenuItems] = useState([]);
  const appUserId = localStorage.getItem('id') ? Number(localStorage.getItem('id')) : null;
  const [selectedRestaurantId, setSelectedRestaurantId] = useState(null);
  const [filterText, setFilterText] = useState('');

  useEffect(() => {
    if (menuItems.length > 0) {
      setSelectedRestaurantId(menuItems[0].restaurantId);
    }
  }, [menuItems]);

  const handleAddToCart = async (itemId) => {
    try {
      await axios.post('http://localhost:8080/cart/add', { itemId, appUserId: appUserId, quantity: 1 });
      console.log('Added to cart:', itemId);
      message.success('Item added to cart successfully');
    } catch (error) {
      console.error('Error adding to cart:', error);
      message.error('Failed to add item to cart');
    }
  };

//    const handleAddToCart = async (itemId, restaurantId) => {
//        console.log('Selected Restaurant ID:', selectedRestaurantId);
//          console.log('Item Restaurant ID:', restaurantId);
//        try {
//          if (selectedRestaurantId === null || selectedRestaurantId === restaurantId) {
//            await axios.post('http://localhost:8080/cart/add', { itemId, appUserId, quantity: 1 });
//            console.log('Added to cart:', itemId);
//            setSelectedRestaurantId(restaurantId); // Update selectedRestaurantId
//            message.success('Item added to cart successfully');
//          } else {
//            message.error('You can only add items from the same restaurant to the cart.');
//          }
//        } catch (error) {
//          console.error('Error adding to cart:', error);
//          message.error('Failed to add item to cart');
//        }
//      };

  useEffect(() => {
      fetchMenu();
    }, [restaurantId]);

    const fetchMenu = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/menus/by-restaurant/${restaurantId}`);
        console.log('API Response:', response.data);
        setMenuItems(response.data);
      } catch (error) {
        console.error('Error fetching menu:', error);
      }
    };

  const handleFilterChange = (e) => {
      setFilterText(e.target.value);
    };

    const handleSearch = () => {
      if (filterText.trim() === '') {
        fetchMenu();
      } else {
        const searchText = filterText.toLowerCase();
        const filteredItems = menuItems.filter(item =>
          item.itemName.toLowerCase().includes(searchText)
        );
        setMenuItems(filteredItems);
      }
    };

  const columns = [
    {
      title: 'Item Name',
      dataIndex: 'itemName',
      key: 'itemName',
    },
    {
      title: 'Price',
      dataIndex: 'itemPrice',
      key: 'itemPrice',
      render: (price) => `${price} $`,
    },
    {
      title: 'Actions',
      key: 'actions',
      render: (text, record) => (
        <Button style={{ borderColor: '#f5945f', color: '#f5945f' }} onClick={() => handleAddToCart(record.itemId)}>
          Add to Cart
        </Button>
      ),
    },
  ];

    return (
        <div style={{
          display: 'flex',
          backgroundImage: `url(${backgroundImage})`,
          backgroundSize: 'cover',
          height: '100vh'
        }}>
          <SideMenuCustomer />
          <div style={{ flex: 1, padding: '20px' }}>
            <Card title={`Menu`} style={{ margin: '20px' }}>
              <Input
                placeholder="Search menu items..."
                value={filterText}
                onChange={handleFilterChange}
                style={{ marginBottom: 16, width: 250, borderColor: '#f5945f' }}
              />
              <Button
                type="primary"
                style={{ backgroundColor: '#f5945f', borderColor: '#f5945f' }}
                onClick={handleSearch}
              >
                Search
              </Button>
              <Table dataSource={menuItems} columns={columns} rowKey="itemId" />
            </Card>
          </div>
        </div>
      );
};

export default CustomerMenu;
