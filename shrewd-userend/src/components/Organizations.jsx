import React, { useState, useEffect } from 'react';

const Organizations = () => {
  const [organizations, setOrganizations] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetch('http://localhost:8080/api/organizations/')
      .then(response => response.json())
      .then(data => {
        setOrganizations(data);
        setLoading(false);
      })
      .catch(error => {
        console.error('Error fetching organizations:', error);
        setLoading(false);
      });
  }, []);

  if (loading) {
    return <p>Loading organizations...</p>;
  }

  return (
    <div>
      <h2>Organizations</h2>
      <ul>
        {organizations.map(org => (
          <li key={org.id}>{org.name}</li>
        ))}
      </ul>
    </div>
  );
};

export default Organizations;
