    <Router>
      <div className="App">
        <Navbar />
        <Routes>
          <Route exact path="/" element={Home} />
          <Route path="/registration" element={Registration} />
          <Route path="/login" element={Login} />
        </Routes>
      </div>
    </Router>


    import React, { useState } from 'react';
    import axios from 'axios';
    import { Link } from 'react-router-dom';

    const Registration = () => {
      const [email, setEmail] = useState('');
      const [password, setPassword] = useState('');

      const handleSubmit = async (e) => {
        e.preventDefault();

        const requestBody = {
          email: email,
          password: password,
        };

        try {
          const response = await axios.post(
            'http://localhost:8080/api/v1/registration',
            requestBody
          );
          console.log(response.data);
        } catch (error) {
          console.error(error);
        }
      };

      return (
        <div className="container">
          <div className="py-4">
            <h1>Create Account</h1>
            <form onSubmit={handleSubmit}>
              <div className="form-group">
                <label htmlFor="email">Email</label>
                <input
                  type="email"
                  className="form-control"
                  id="email"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
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
                />
              </div>
              <button type="submit" className="btn btn-primary">
                Register
              </button>
            </form>
            <p>Already have an account? <Link to="/login">Login</Link></p>
          </div>
        </div>
      );
    };

    export default Registration;


<Link to="/login" className="option-link">
          I'm a Restaurant
        </Link>