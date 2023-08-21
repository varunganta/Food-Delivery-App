import React, {useEffect, useState} from 'react';
import {Card, Table, Button, message, Modal, Input} from 'antd';
//import dishes from "../assets/data/dishes.json";
import {Link, useNavigate} from 'react-router-dom';
import SideMenuRestaurant from '../assets/components/SideMenuRestaurant.js';
import backgroundImage from '/home/varun/Desktop/FrontEnd/login-front/src/25386.jpg';
import axios from 'axios';


const RestaurantMenu = () => {
    const [menuItems, setMenuItems] = useState([]);
    const [restaurantId, setRestaurantId] = useState('');
    const appUserId = localStorage.getItem('id') ? Number(localStorage.getItem('id')) : null;
    const navigate = useNavigate();
    const [isDeleteModalVisible, setIsDeleteModalVisible] = useState(false);
    const [filterText, setFilterText] = useState('');


    const showDeleteModal = () => {
        setIsDeleteModalVisible(true);
      };

    const fetchRestaurantIdByAppUserId = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/restaurants/restaurant/by-app-user/${appUserId}`); // Replace with your API endpoint

        setRestaurantId(response.data);
        console.log(response.data);
      } catch (error) {
        console.error('Error fetching restaurant ID:', error);
      }
    };

    const handleRemoveItem = (itemId) => {
        Modal.confirm({
          title: 'Confirm Deletion',
          content: 'Are you sure you want to delete this menu item?',
          okText: 'Yes',
          cancelText: 'No',
          onOk: async () => {
            try {
              const response = await axios.delete(`http://localhost:8080/menus/menu/${itemId}`, {
                headers: {
                  Authorization: `Bearer ${localStorage.getItem('token')}`,
                },
              });

              fetchMenuByRestaurantId();

              message.success('Menu item successfully deleted');
            } catch (error) {
              console.error('Error removing menu item:', error);
            }
          },
        });
      };

    const handleMenuItemClick = async (itemId) => {
        try {
          const response = await axios.get(`http://localhost:8080/menus/menu/${itemId}`);
          const menuItem = response.data;

          navigate(`../update/${menuItem.itemId}`);
        } catch (error) {
          console.error('Error fetching menu item details:', error);
        }
      };

    const handleFilterChange = (e) => {
        setFilterText(e.target.value);
        console.log(e);
    };

    const handleSearch = () => {
            if (filterText.trim() === '') {
                fetchMenuByRestaurantId();
            } else {
                const searchText = filterText.toLowerCase();
                const filteredItems = menuItems.filter(item =>
                    item.itemName.toLowerCase().includes(searchText)

                );
                console.log("Filtered menu items:", filteredItems);
                setMenuItems(filteredItems);
            }
        };


    const tableColumns = [
      {
        title: 'Menu Item',
        dataIndex: 'itemName',
        key: 'itemName',
        render: (text, record) => (
                <Link to={`update/${record.itemId}`}>{text}</Link>
              ),
      },
      {
        title: 'Price',
        dataIndex: 'itemPrice',
        key: 'itemPrice',
        render: (price) => `${price} $`,
      },
      {
          title: '',
          key: 'action',
          render: (text, record) => (
            <Button danger onClick={() => handleRemoveItem(record.itemId)}>
              Remove
            </Button>
          ),
        },
    ];

    const renderNewItemButton = () => (
        <Link to = {"create"}>
            <Button type = "primary" style = {{backgroundColor: '#f5945f'}}>New Item</Button>
        </Link>
    )

    const handleCreateNewItem = async () => {
        try {
          const response = await axios.post(`http://localhost:8080/menus/create`, {
          });
          fetchMenuByRestaurantId();
        } catch (error) {
          console.error('Error creating new menu item:', error);
        }
      };

    const fetchMenuByRestaurantId = async () => {
        try {
          const response = await axios.get(`http://localhost:8080/menus/by-restaurant/${restaurantId}`);
          setMenuItems(response.data);
        } catch (error) {
          console.error('Error fetching menu items:', error);
        }
      };



    useEffect(() => {
        fetchRestaurantIdByAppUserId();
        fetchMenuByRestaurantId();
      }, [appUserId, restaurantId]);



    return (
        <div style={{
            display: 'flex',
            backgroundImage: `url(${backgroundImage})`,
            backgroundSize: 'cover',
            height: '100vh',
            overflow: 'hidden',
          }}>
          <SideMenuRestaurant />
          <div style={{ flex: 1 }}>
            <Card title={'Menu'} style={{ margin: 20 }} extra={renderNewItemButton()}>
              <Input
                  placeholder="Search by item name"
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
              <Table dataSource={menuItems} columns={tableColumns} rowKey="id" />
            </Card>
          </div>
        </div>
      );
};

export default RestaurantMenu