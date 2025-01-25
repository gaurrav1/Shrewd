import React from 'react';
import { BrowserRouter as Router, Route, Routes, Link } from 'react-router-dom';
import Organizations from './components/Organizations';
import RegisterOrganization from './components/RegisterOrganization';
import LoginOrganization from './components/LoginOrganization';
import Home from './components/Home';

const App = () => {
    return (
        <Router>
            <div>
                <h1>Smart Attendance and Payroll System</h1>
                <Routes>
                    <Route path="/" element={<Home />} />
                    <Route path="/register" element={<RegisterOrganization />} />
                    <Route path="/login" element={<LoginOrganization />} />
                    <Route path="/organizations" element={<Organizations />} />
                </Routes>
            </div>
        </Router>
    );
};

export default App;
