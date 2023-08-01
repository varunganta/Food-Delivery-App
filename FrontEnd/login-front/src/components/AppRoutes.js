import Home from './Home.js';
import Registration from './Registration.js';
import Login from './Login.js';
import {Routes, Route} from 'react-router-dom';
import Main from './Main.js';
import Logout from './Logout.js';

const AppRoutes = () => {
    return(
        <Routes>
            <Route path="/" element={<Home/>} />
            <Route path="/registration" element={<Registration/>} />
            <Route path="/login" element={<Login/>} />
            <Route path="/main" element={<Main/>} />
            <Route path="/logout" element={<Logout/>} />
        </Routes>
    );
};

export default AppRoutes;