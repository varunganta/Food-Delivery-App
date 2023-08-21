import React from 'react';
import { Routes, Route } from 'react-router-dom';
import Home from './Home.js';
import Registration from './Registration.js';
import Login from './Login.js';
import Main from './Main.js';
import Logout from './Logout.js';
import Orders from './Orders.js';
import RestaurantMenu from './RestaurantMenu.js';
import CreateMenuItem from './CreateMenuItem.js';
import OrderHistory from './OrderHistory.js';
import Settings from './Settings.js';
import RestaurantHome from './RestaurantHome.js';
import UpdateMenuItem from './UpdateMenuItem.js';
import OrderItems from './OrderItems.js';
import UpdateOrder from './UpdateOrder.js';
import CustomerHome from './CustomerHome.js';
import CustomerSettings from './CustomerSettings.js';
import CustomerMenu from './CustomerMenu.js';
import Cart from './Cart.js';

const AppRoutes = () => {
  return (
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/registration" element={<Registration />} />
      <Route path="/login" element={<Login />} />
      <Route path="/main" element={<Main />} />
      <Route path="/logout" element={<Logout />} />
      <Route path="/menus" element={<RestaurantMenu />} />
      <Route path="/menus/create" element={<CreateMenuItem />} />
      <Route path="/settings" element={<Settings />} />
      <Route path = "/orders" element={<Orders />} />
      <Route path = "restaurant-home" element={<RestaurantHome />} />
      <Route path = "menus/update/:itemId" element={<UpdateMenuItem />} />
      <Route path = "orders/order/:orderId" element={<OrderItems />} />
      <Route path = "orders/update/:orderId" element={<UpdateOrder />} />
      <Route path = "customer-home" element={<CustomerHome />} />
      <Route path = "customer-settings" element={<CustomerSettings />} />
      <Route path = "order-history" element={<OrderHistory />} />
      <Route path="/customer-menu/:restaurantId" element={<CustomerMenu />} />
      <Route path = "/cart" element={<Cart />} />
    </Routes>
  );
};

export default AppRoutes;
