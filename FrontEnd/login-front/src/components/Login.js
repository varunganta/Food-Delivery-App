import React, { useState } from 'react';
import axios from 'axios';
import { Link, useNavigate } from 'react-router-dom';
import { Button, message } from 'antd';
import image from '/home/varun/Desktop/FrontEnd/login-front/src/25386.jpg';

const Login = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    const requestBody = {
      email: email,
      password: password,
    };

    try {
        const response = await axios.post(
            'http://localhost:8080/login',
            requestBody
        );

        const { loginStatus, errorMessage, jwt, id } = response.data;

        if(loginStatus){
            const roleResponse = await axios.get(
                `http://localhost:8080/appUser/role?email=${email}`,
                {
                    headers: {
                        Authorization: `$(jwt)`
                    }
                }
            );
            const role = roleResponse.data.role;
            message.success('Login successful!');

            if(role === 'RESTAURANT'){
                navigate('/restaurant-home', { state: { email: email } });
                localStorage.setItem('token', jwt);
                localStorage.setItem('email', email);
                localStorage.setItem('id', Number(id));
            } else {
                navigate('/customer-home', { state: { email: email } });
                localStorage.setItem('token', jwt);
                localStorage.setItem('email', email);
                localStorage.setItem('id', Number(id));
            }
            axios.defaults.headers.common['Authorization'] = `Bearer ${jwt}`;
        } else {
            message.error(errorMessage);
        }
    } catch (error) {
        console.error(error);
        if (error.response && error.response.status === 401) {
            message.error('The account is disabled');
        } else {
            message.error('Invalid username or password');
        }
    }
  };

  return (
    <div
      style={{
        backgroundImage: `url(${image})`,
        backgroundRepeat: 'no-repeat',
        backgroundPosition: 'center',
        width: '100vw',
        height: '100vh',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
      }}
    >
      <div
        className="container"
        style={{
          maxWidth: '350px',
          margin: '0 auto',
          textAlign: 'center',
          background: 'rgba(0, 0, 0, 0.7)',
          borderRadius: '10px',
          padding: '20px',
        }}
      >
        <div className="py-4" style={{ color: 'white' }}>
          <h1>Login</h1>
          <form onSubmit={handleSubmit}>
            <div className="form-group">
              <label htmlFor="email">Email</label>
              <input
                type="email"
                className="form-control"
                id="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                style={{ color: 'black' }}
              />
            </div>
            <div className="form-group">
              <label htmlFor="password">Password</label>
              <input
                type="password"
                className="form-control"
                id="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                style={{ color: 'black' }}
              />
            </div>
            <div style={{ marginBottom: '20px' }}></div>
            <Button type="primary" htmlType="submit" style={{ width: '100%' }}>
              Login
            </Button>
          </form>
          <p>
            No account? <Link to="/registration" style={{ color: 'white' }}>Register</Link>
          </p>
          <p>
            <Link to="/" style={{ color: 'white' }}>Back to Home</Link>
          </p>
        </div>
      </div>
    </div>
  );
};

export default Login;
