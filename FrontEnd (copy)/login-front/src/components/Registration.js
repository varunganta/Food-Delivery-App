//import React, { useState } from 'react';
//import axios from 'axios';
//import { Link, useNavigate } from 'react-router-dom';
//import { Button, message } from 'antd';
//import image from '/home/varun/Desktop/FrontEnd/login-front/src/25386.jpg';
//
//const Registration = () => {
//  const [firstname, setFirstName] = useState('');
//  const [lastname, setLastName] = useState('');
//  const [email, setEmail] = useState('');
//  const [password, setPassword] = useState('');
//  const [confirmpassword, setConfirmPassword] = useState('');
//  const navigate = useNavigate();
//
//  const handleSubmit = async (e) => {
//    e.preventDefault();
//
//    if (!isValidEmail(email)) {
//      message.error('Invalid email format');
//      return;
//    }
//
//    if (password !== confirmpassword) {
//      message.error('Passwords do not match');
//      return;
//    }
//
//    const requestBody = {
//      firstname: firstname,
//      lastname: lastname,
//      email: email,
//      password: password,
//      confirmpassword: confirmpassword,
//    };
//    try {
//      const response = await axios.post('http://localhost:8080/registration', requestBody);
//
//      if (response.data && response.data.verificationStatus === true) {
//        message.info('Registration successful. Please check your email for verification.');
//        navigate.push('/login');
//      } else {
//        message.error('Account with entered email exists');
//      }
//    } catch (error) {
//      console.error(error);
//      message.error('Error occurred during registration');
//    }
//
//  const isValidEmail = (email) => {
//    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
//    return emailPattern.test(email);
//  };
//
//  return (
//    <div
//      style={{
//        backgroundImage: `url(${image})`,
//        backgroundRepeat: 'no-repeat',
//        backgroundPosition: 'center',
//        width: '100vw',
//        height: '100vh',
//        display: 'flex',
//        alignItems: 'center',
//        justifyContent: 'center',
//      }}
//    >
//      <div
//        className="container"
//        style={{
//          maxWidth: '350px',
//          margin: '0 auto',
//          textAlign: 'center',
//          background: 'rgba(0, 0, 0, 0.7)',
//          borderRadius: '10px',
//          padding: '20px',
//        }}
//      >
//        <div className="py-4" style={{ color: 'white' }}>
//          <h1>Create Account</h1>
//          <form onSubmit={handleSubmit}>
//            <div className="form-group">
//              <label htmlFor="firstname">First Name</label>
//              <input
//                type="firstname"
//                className="form-control"
//                id="firstname"
//                value={firstname}
//                onChange={(e) => setFirstName(e.target.value)}
//                required
//                style={{ color: 'black' }}
//              />
//            </div>
//            <div className="form-group">
//              <label htmlFor="lastname">Last Name</label>
//              <input
//                type="lastname"
//                className="form-control"
//                id="lastname"
//                value={lastname}
//                onChange={(e) => setLastName(e.target.value)}
//                required
//                style={{ color: 'black' }}
//              />
//            </div>
//            <div className="form-group">
//              <label htmlFor="email">Email</label>
//              <input
//                type="email"
//                className="form-control"
//                id="email"
//                value={email}
//                onChange={(e) => setEmail(e.target.value)}
//                required
//                style={{ color: 'black' }}
//              />
//            </div>
//            <div className="form-group">
//              <label htmlFor="password">Password</label>
//              <input
//                type="password"
//                className="form-control"
//                id="password"
//                value={password}
//                onChange={(e) => setPassword(e.target.value)}
//                required
//                style={{ color: 'black' }}
//              />
//            </div>
//            <div className="form-group">
//              <label htmlFor="password">Confirm Password</label>
//              <input
//                type="password"
//                className="form-control"
//                id="password"
//                value={confirmpassword}
//                onChange={(e) => setConfirmPassword(e.target.value)}
//                required
//                style={{ color: 'black' }}
//              />
//            </div>
//            <div style={{ marginBottom: '20px' }}></div>
//            <Button type="primary" htmlType="submit" style={{ width: '100%' }}>
//              Register
//            </Button>
//          </form>
//          <p>
//            Already have an account? <Link to="/login" style={{ color: 'white' }}>Login</Link>
//          </p>
//          <p>
//            <Link to="/" style={{ color: 'white' }}>Back to Home</Link>
//          </p>
//        </div>
//      </div>
//    </div>
//  );
//};
//
//export default Registration;
import React, { useState } from 'react';
import axios from 'axios';
import { Link, useNavigate } from 'react-router-dom';
import { Button, message } from 'antd';
import image from '/home/varun/Desktop/FrontEnd/login-front/src/25386.jpg';

const Registration = () => {
  const [firstname, setFirstName] = useState('');
  const [lastname, setLastName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confirmpassword, setConfirmPassword] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!isValidEmail(email)) {
      message.error('Invalid email format');
      return;
    }

    if (password !== confirmpassword) {
      message.error('Passwords do not match');
      return;
    }

    const requestBody = {
      firstName: firstname,
      lastName: lastname,
      email: email,
      password: password,
      confirmPassword: confirmpassword,
    };
    //console.error("rendered");
    try {
      const response = await axios.post('http://localhost:8080/registration', requestBody);

      if (response.data && response.data.verificationStatus === true) {
        message.info('Registration successful. Please check your email for verification.');
        //navigate.push('/login');
      } else {
        message.error('Account with entered email exists');
      }
    } catch (error) {
      console.error(error);
      message.error("Account with email already registered");
    }
  };

  const isValidEmail = (email) => {
    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailPattern.test(email);
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
          <h1>Create Account</h1>
          <form onSubmit={handleSubmit}>
            <div className="form-group">
              <label htmlFor="firstname">First Name</label>
              <input
                type="firstname"
                className="form-control"
                id="firstname"
                value={firstname}
                onChange={(e) => setFirstName(e.target.value)}
                required
                style={{ color: 'black' }}
              />
            </div>
            <div className="form-group">
              <label htmlFor="lastname">Last Name</label>
              <input
                type="lastname"
                className="form-control"
                id="lastname"
                value={lastname}
                onChange={(e) => setLastName(e.target.value)}
                required
                style={{ color: 'black' }}
              />
            </div>
            <div className="form-group">
              <label htmlFor="email">Email</label>
              <input
                type="email"
                className="form-control"
                id="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                required
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
                required
                style={{ color: 'black' }}
              />
            </div>
            <div className="form-group">
              <label htmlFor="password">Confirm Password</label>
              <input
                type="password"
                className="form-control"
                id="password"
                value={confirmpassword}
                onChange={(e) => setConfirmPassword(e.target.value)}
                required
                style={{ color: 'black' }}
              />
            </div>
            <div style={{ marginBottom: '20px' }}></div>
            <Button type="primary" htmlType="submit" style={{ width: '100%' }}>
              Register
            </Button>
          </form>
          <p>
            Already have an account? <Link to="/login" style={{ color: 'white' }}>Login</Link>
          </p>
          <p>
            <Link to="/" style={{ color: 'white' }}>Back to Home</Link>
          </p>
        </div>
      </div>
    </div>
  );
};

export default Registration;

