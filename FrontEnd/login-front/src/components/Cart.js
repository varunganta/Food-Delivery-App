import React, { useState, useEffect } from 'react';
import { Card, Table, Button, Popconfirm, message, Modal } from 'antd';
import SideMenuCustomer from '../assets/components/SideMenuCustomer';
import backgroundImage from '/home/varun/Desktop/FrontEnd/login-front/src/25386.jpg';
import axios from 'axios';

const Cart = () => {
  const [cartItems, setCartItems] = useState([]);
  const [selectedCartItem, setSelectedCartItem] = useState(null);
  const [totalPrice, setTotalPrice] = useState(0);
  const [isCheckoutModalVisible, setIsCheckoutModalVisible] = useState(false);

  const appUserId = localStorage.getItem('id') ? Number(localStorage.getItem('id')) : null;
  const token = localStorage.getItem('token');

  const showCheckoutModal = () => {
    setIsCheckoutModalVisible(true);
  };

  const hideCheckoutModal = () => {
    setIsCheckoutModalVisible(false);
  };

  const handleCheckout = async () => {
    if (cartItems.length === 0) {
      message.error('Cannot proceed with checkout. Cart is empty.');
      return;
    }

    const isSameRestaurant = cartItems.every(item => item.restaurantId === cartItems[0]?.restaurantId);

    if (!isSameRestaurant) {
      message.error('Cannot proceed with checkout. Cart items belong to different restaurants.');
      return;
    }

    const checkoutPayload = {
      appUserId: appUserId,
      customerId: appUserId,
      restaurantId: cartItems[0]?.restaurantId,
      items: cartItems.map(item => ({
        itemId: item.itemId,
        quantity: item.quantity
      })),
      totalAmount: totalPrice,
      createdAt: new Date().toISOString(),
      acceptedAt: null,
      completedAt: null,
      status: 'processing'
    };

    try {
      const response = await axios.post(
        'http://localhost:8080/orders/create',
        checkoutPayload,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      const orderItemsPayload = cartItems.map(item => ({
        orderId: response.data.orderId,
        menuId: item.itemId,
        price: item.itemPrice,
        quantity: item.quantity,
      }));

      try {
        const orderItemsResponse = await axios.post(
          'http://localhost:8080/orderItems/batch-create',
          orderItemsPayload,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );

        setCartItems([]);
        setTotalPrice(0);
        hideCheckoutModal();

        try {
          await axios.delete(`http://localhost:8080/cart/all/${appUserId}`, {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          });

          message.success('Order created successfully. Cart items removed.');
        } catch (error) {
          console.error('Error removing cart items:', error);
          message.error('Error removing cart items.');
        }
      } catch (error) {
        console.error('Error creating order items:', error);
        message.error('Error creating order items.');
      }
    } catch (error) {
      console.error('Error creating order:', error);
      message.error('Error creating order.');
    }
  };


  const tableColumns = [
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
      title: 'Quantity',
      dataIndex: 'quantity',
      key: 'quantity',
      render: (quantity, record) => (
        <input
          type="number"
          value={quantity}
          onChange={(e) => handleQuantityChange(record.cartItemId, e.target.value)}
        />
      ),
    },
    {
      title: '',
      dataIndex: 'itemId',
      key: 'actions',
      render: (itemId, record) => (
        <Popconfirm
          title="Are you sure to remove this item from the cart?"
          onConfirm={() => handleConfirmRemove(record)}
          okText="Yes"
          cancelText="No"
        >
          <Button danger>Remove</Button>
        </Popconfirm>
      ),
    },
  ];

  const handleQuantityChange = (cartItemId, newQuantity) => {
      console.log('Updating quantity:', cartItemId, newQuantity);
      const updatedCartItems = cartItems.map((item) =>
          item.cartItemId === cartItemId ? { ...item, quantity: newQuantity } : item
      );
      setCartItems(updatedCartItems);

      updateCartItemQuantity(cartItemId, newQuantity, updatedCartItems);
  };

  const updateCartItemQuantity = async (cartItemId, newQuantity, updatedCartItems) => {
      try {
          const requestPayload = {
              cartItemId: cartItemId,
              quantity: newQuantity,
          };

          await axios.put(
              `http://localhost:8080/cart/update/${cartItemId}`,
              requestPayload,
              {
                  headers: {
                      Authorization: `Bearer ${token}`,
                  },
              }
          );

          const updatedTotalPrice = calculateTotalPrice(updatedCartItems);
          setTotalPrice(updatedTotalPrice);

          message.success('Quantity updated successfully.');
      } catch (error) {
          console.error('Error updating quantity:', error);
          message.error('Error updating quantity.');
      }
  };

  const calculateTotalPrice = (items) => {
      return items.reduce((total, item) => total + item.itemPrice * item.quantity, 0);
    };

  const fetchCartItems = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/cart/${appUserId}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      const cartWithItemDetails = await Promise.all(response.data.map(async (cartItem) => {
        const itemResponse = await axios.get(`http://localhost:8080/menus/menu/${cartItem.itemId}`);
        const { itemName, itemPrice, restaurantId } = itemResponse.data;
        return {
          ...cartItem,
          itemName,
          itemPrice,
          restaurantId,
        };
      }));

      setCartItems(cartWithItemDetails);
    } catch (error) {
      console.error('Error fetching cart items:', error);
    }
  };

  const fetchTotalPrice = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/cart/totalPrice/${appUserId}`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        setTotalPrice(response.data);
      } catch (error) {
        console.error('Error fetching total price:', error);
      }
    };

    useEffect(() => {
      fetchCartItems();
      fetchTotalPrice();
    }, []);

   const handleRemoveItem = (itemId) => {
      setSelectedCartItem(cartItems.find((item) => item.itemId === itemId));
    };

    const handleConfirmRemove = async (item) => {
      try {
        await axios.delete(`http://localhost:8080/cart/${item.cartItemId}`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        const updatedCart = cartItems.filter((cartItem) => cartItem.itemId !== item.itemId);
        setCartItems(updatedCart);
        setTotalPrice(calculateTotalPrice(updatedCart));
        message.success('Item/s removed from cart.');
      } catch (error) {
        console.error('Error removing item from cart:', error);
        message.error('Error removing item from cart.');
      }
    };


  return (
    <div
      style={{
        display: 'flex',
        backgroundImage: `url(${backgroundImage})`,
        backgroundSize: 'cover',
        height: '100vh',
      }}
    >
      <SideMenuCustomer />
      <div style={{ flex: 1 }}>
        <Card title={'Item Cart'} style={{ margin: 20 }}>
          <Table dataSource={cartItems} columns={tableColumns} rowKey="itemId" />
          <div style={{ marginTop: '1rem', textAlign: 'right' }}>
              <strong>Total Price:</strong> {totalPrice.toFixed(2)} $
            </div>
          <div style={{ marginTop: '1rem', textAlign: 'right' }}>
            <Button style={{borderColor: '#f5945f', color: '#f5945f'}} onClick={showCheckoutModal}>
              Checkout
            </Button>
          </div>
        </Card>
      </div>
      <Modal
        title="Checkout"
        visible={isCheckoutModalVisible}
        //onOk={hideCheckoutModal}
        onCancel={hideCheckoutModal}
        footer = {null}
      >
        <div>
          <p>Total Price: {totalPrice.toFixed(2)} $</p>
          <form style={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
            <label htmlFor="address">Address:</label>
            <textarea id="address" name="address" rows="3" required style={{ width: '100%', marginBottom: '10px' }} />

            <label htmlFor="phoneNumber">Phone Number:</label>
            <input type="tel" id="phoneNumber" name="phoneNumber" required style={{ width: '100%', marginBottom: '10px' }} />

            <Button style={{ borderColor: '#f5945f', color: '#f5945f', width: '100%' }} onClick={handleCheckout}>
              Confirm Checkout
            </Button>
          </form>
        </div>
      </Modal>
    </div>
  );
};

export default Cart;
