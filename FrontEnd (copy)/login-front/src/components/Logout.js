import React from 'react';
import { Link } from 'react-router-dom';
import { Button } from 'antd';

const LogoutPage = () => {
  return (
    <div style={{ textAlign: 'center' }}>
      <h1>You have been logged out</h1>
      <Button type="primary" className="btn btn-primary">
        <Link to="/login" style={{ color: '#fff' }}>
          Back to Login
        </Link>
      </Button>
    </div>
  );
};

export default LogoutPage;
