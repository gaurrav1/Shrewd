import React from 'react';
import { Link } from 'react-router-dom';

function Home() {
    return (
        <div>
            <h2>Home</h2>
            <div>
                <Link to="/login">
                    <button>Login</button>
                </Link>
                <Link to="/register">
                    <button>Sign Up</button>
                </Link>
                <Link to="/organizations">
                    <button>See All</button>
                </Link>
            </div>
        </div>
    );
}

export default Home;
