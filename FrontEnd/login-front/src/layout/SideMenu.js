import React from 'react';
import { Menu, Collapse } from 'antd';
import { useNavigate } from 'react-router-dom';
import { MenuOutlined } from '@ant-design/icons';

const { Panel } = Collapse;

const SideMenu = () => {
  const navigate = useNavigate();
  const menuItems = [
    {
      key: '/login',
      label: 'Login',
    },
    {
      key: '/registration',
      label: 'Register',
    },
  ];

  const handleMenuClick = ({ key }) => {
    navigate(key);
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

export default SideMenu;
