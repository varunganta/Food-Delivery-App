import React from 'react';
import { Menu, Collapse } from 'antd';
import { useNavigate } from 'react-router-dom';
import { MenuOutlined } from '@ant-design/icons';

const { Panel } = Collapse;

const SideMenuRestaurant = () => {
  const navigate = useNavigate();

  const menuItems = [
    {
      key: 'restaurant-home',
      label: 'Home',
    },
    {
      key: 'orders',
      label: 'Orders',
    },
    {
      key: 'menus',
      label: 'Menu',
    },
    {
      key: 'settings',
      label: 'Settings',
    },
    {
        key: 'main',
        label: 'Logout'
    }
  ];

  const handleMenuClick = ({ key }) => {
    navigate(`/${key}`);
  };

  return (
    <Collapse
      defaultActiveKey={['1']}
      collapsible="header"
      expandIcon={({ isActive }) => <MenuOutlined rotate={isActive ? 90 : 0} style={{ color: '#ffffff' }} />}
    >
      <Panel
        header={<span style={{ color: '#ffffff' }}>Menu</span>}
        key="1"
        style={{
          background: '#000000',
          color: '#ffffff',
          height: '100vh',
          overflow: 'hidden'
        }}
      >
        <Menu
          mode="vertical"
          theme="dark"
          onClick={handleMenuClick}
          style={{
            width: '10vw',
            height: '100vh',
            display: 'flex',
            flexDirection: 'column',
            justifyContent: 'flex-start'
          }}
        >
          {menuItems.map((item) => (
            <Menu.Item key={item.key} style={{ color: '#ffffff'}}>
              {item.label}
            </Menu.Item>
          ))}
        </Menu>
      </Panel>
    </Collapse>
  );
};

export default SideMenuRestaurant;
