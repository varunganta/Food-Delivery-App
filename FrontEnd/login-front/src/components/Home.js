import React from 'react';
import { Link } from 'react-router-dom';
import SideMenu from '/home/varun/Desktop/FrontEnd/login-front/src/layout/SideMenu.js';
import backgroundImage from '/home/varun/Desktop/FrontEnd/login-front/src/25386.jpg';

const Home = () => {
  return (
    <div className="home-container" style={{ backgroundImage: `url(${backgroundImage})`, height: '100vh', overflow: 'hidden' }}>
      <div className="header" style={{ textAlign: 'center' }}>
        <h1>Nom Nom Delivery</h1>
      </div>
      <div className="content" style={{ display: 'flex', height: '100%' }}>
        <div className="side-menu" style={{ flex: '0 0 10vw' }}>
          <SideMenu />
        </div>

      </div>
    </div>
  );
};

export default Home;
