import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Home from './components/Home';
import Login from './components/Login.js';
import Registration from './components/Registration.js';
import AppRoutes from './components/AppRoutes.js';

const App = () => {
  return (
      <Router>
        <AppRoutes/>
      </Router>
  );
};

export default App;
