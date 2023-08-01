import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

export const useRequireAuth = () => {
  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem('token');
    const email = localStorage.getItem('email');
    //const password = localStorage.getItem('password')

    if (!token || !email) {
      navigate('/login');
    }
  }, [navigate]);
};
