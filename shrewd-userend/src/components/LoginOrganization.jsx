import React, { useState } from 'react';
import axios from 'axios';

const LoginOrganization = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [message, setMessage] = useState('');

    const handleLogin = async (e) => {
        e.preventDefault();
    
        try {
            const response = await axios.post('http://localhost:8080/api/organizations/login', {
                email,
                password,
            });
            setMessage(`Login successful! Welcome, ${response.data.name}.`);
        } catch (error) {
            if (error.response) {
                const errorMessage = error.response.data ? error.response.data : 'Unknown error occurred.';
                setMessage(`Error: ${errorMessage}`);
            } else {
                setMessage('Error: Could not connect to the server.');
            }
        }
    };
    

    return (
        <div>
            <h2>Login Organization</h2>
            <form onSubmit={handleLogin}>
                <div>
                    <label>Email:</label>
                    <input
                        type="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label>Password:</label>
                    <input
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                </div>
                <button type="submit">Login</button>
            </form>
            {message && <p>{message}</p>}
        </div>
    );
};

export default LoginOrganization;
