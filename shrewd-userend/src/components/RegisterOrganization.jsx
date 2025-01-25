import React, { useState } from 'react';
import axios from 'axios';

const RegisterOrganization = () => {
    const [name, setName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [message, setMessage] = useState('');

    const handleRegister = async (e) => {
        e.preventDefault();

        try {
            const response = await axios.post('http://localhost:8080/api/organizations/register', {
                name,
                email,
                password,
            });
            setMessage(`Organization registered successfully: ${response.data.name}`);
            // Optionally, reset form fields
            setName('');
            setEmail('');
            setPassword('');
        } catch (error) {
            if (error.response) {
                setMessage(`Error: ${error.response.data}`);
            } else {
                setMessage('Error: Could not connect to the server.');
            }
        }
    };

    return (
        <div>
            <h2>Register Organization</h2>
            <form onSubmit={handleRegister}>
                <div>
                    <label>Name:</label>
                    <input
                        type="text"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                        required
                    />
                </div>
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
                <button type="submit">Register</button>
            </form>
            {message && <p>{message}</p>}
        </div>
    );
};

export default RegisterOrganization;
