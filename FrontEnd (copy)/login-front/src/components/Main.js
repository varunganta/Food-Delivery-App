import React, {useEffect} from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, message } from 'antd';
import image from '/home/varun/Desktop/FrontEnd/login-front/src/25386.jpg';
import {useRequireAuth} from './useRequireAuth.js';

const Main = ({ handleLogout }) => {
  useRequireAuth();
  const location = useLocation();
  const email = location?.state?.email;
  const name = email && email.split('@')[0];
  const navigate = useNavigate();

  const handleLogoutClick = () => {
    sessionStorage.clear();
    localStorage.clear();
    window.location.href = '/';
  };

  return (
    <div style={{ backgroundImage:`url(${image})`,backgroundRepeat:"no-repeat",backgroundPosition: "center", width: '100vw', height: '100vh'}}>
        <div style={{ textAlign: 'center' }}>
          <h1>Welcome to the Main Page</h1>
          <Button type="primary" onClick={handleLogoutClick}>
            Logout
          </Button>
        </div>
    </div>
  );
};

export default Main;
